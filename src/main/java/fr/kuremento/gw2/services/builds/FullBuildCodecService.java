package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.builds.Equipment;
import fr.kuremento.gw2.models.builds.EquipmentPiece;
import fr.kuremento.gw2.models.builds.EquipmentSlot;
import fr.kuremento.gw2.models.builds.FullBuildCode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Encode et décode un code composite regroupant l'archétype (chat code GW2) et l'équipement.
 *
 * Format v2 (actuel) : base64url(deflate([buildLen:1] + [buildBytes] + [equipBytes]))
 *   Section équipement (binaire little-endian, version 0x02) :
 *     [version : 1 octet = 0x02]
 *     [nombre de pièces : 1 octet]
 *     Pour chaque pièce :
 *       [slot   : 1 octet — ordinal de EquipmentSlot]
 *       [flags  : 1 octet — bit0=statId, bit1=upgradeId, bit2=upgrade2Id, bit3=infusions]
 *       [itemId : 3 octets LE]
 *       [statId    : 3 octets LE] (si flag bit0)
 *       [upgradeId : 3 octets LE] (si flag bit1)
 *       [upgrade2Id: 3 octets LE] (si flag bit2)
 *       [infusionCount : 1 octet] + [infusionIds : count × 3 octets] (si flag bit3)
 *
 * Format v1 (rétrocompatibilité) : base64url(buildBytes).base64url(equipBytes_v1)
 */
@Service
public class FullBuildCodecService {

	private static final byte EQUIP_VERSION_V1 = 0x01;
	private static final byte EQUIP_VERSION_V2 = 0x02;

	private static final int FLAG_STAT_ID     = 0x01;
	private static final int FLAG_UPGRADE_ID  = 0x02;
	private static final int FLAG_UPGRADE2_ID = 0x04;
	private static final int FLAG_INFUSIONS   = 0x08;

	private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
	private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

	// --- API publique ---

	public String encode(String chatCode, Equipment equipment) {
		byte[] buildBytes = decodeChatCode(chatCode);
		byte[] equipBytes = encodeEquipmentV2(equipment);

		ByteArrayOutputStream payload = new ByteArrayOutputStream();
		payload.write(buildBytes.length);
		payload.writeBytes(buildBytes);
		payload.writeBytes(equipBytes);

		return ENCODER.encodeToString(deflate(payload.toByteArray()));
	}

	public FullBuildCode decode(String code) {
		if (code.contains(".")) {
			return decodeV1(code);
		}
		return decodeV2(code);
	}

	// --- Décodage v1 (rétrocompatibilité) ---

	private FullBuildCode decodeV1(String code) {
		String[] parts = code.split("\\.");
		if (parts.length != 2) {
			throw new TechnicalException("Format de code v1 invalide");
		}
		byte[] buildBytes = DECODER.decode(parts[0]);
		byte[] equipBytes = DECODER.decode(parts[1]);
		return new FullBuildCode(encodeChatCode(buildBytes), decodeEquipmentV1(equipBytes));
	}

	// --- Décodage v2 ---

	private FullBuildCode decodeV2(String code) {
		byte[] payload = inflate(DECODER.decode(code));
		int buildLength = Byte.toUnsignedInt(payload[0]);
		byte[] buildBytes = Arrays.copyOfRange(payload, 1, 1 + buildLength);
		byte[] equipBytes = Arrays.copyOfRange(payload, 1 + buildLength, payload.length);
		return new FullBuildCode(encodeChatCode(buildBytes), decodeEquipmentV2(equipBytes));
	}

	// --- Encodage équipement v2 (avec flags de présence) ---

	private byte[] encodeEquipmentV2(Equipment equipment) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(EQUIP_VERSION_V2);
		out.write(equipment.pieces().size());
		for (EquipmentPiece piece : equipment.pieces()) {
			List<Integer> infusions = piece.infusionIds() != null ? piece.infusionIds() : List.of();
			int flags = 0;
			if (piece.statId() != null)    flags |= FLAG_STAT_ID;
			if (piece.upgradeId() != null) flags |= FLAG_UPGRADE_ID;
			if (piece.upgrade2Id() != null) flags |= FLAG_UPGRADE2_ID;
			if (!infusions.isEmpty())       flags |= FLAG_INFUSIONS;

			out.write(piece.slot().ordinal());
			out.write(flags);
			writeUint24(out, piece.itemId());
			if (piece.statId() != null)     writeUint24(out, piece.statId());
			if (piece.upgradeId() != null)  writeUint24(out, piece.upgradeId());
			if (piece.upgrade2Id() != null) writeUint24(out, piece.upgrade2Id());
			if (!infusions.isEmpty()) {
				out.write(infusions.size());
				for (int id : infusions) writeUint24(out, id);
			}
		}
		return out.toByteArray();
	}

	// --- Décodage équipement v2 ---

	private Equipment decodeEquipmentV2(byte[] data) {
		int i = 0;
		if (data[i++] != EQUIP_VERSION_V2) {
			throw new TechnicalException("Version équipement non supportée : " + data[0]);
		}
		int count = Byte.toUnsignedInt(data[i++]);
		List<EquipmentPiece> pieces = new ArrayList<>(count);
		for (int p = 0; p < count; p++) {
			EquipmentSlot slot = EquipmentSlot.values()[Byte.toUnsignedInt(data[i++])];
			int flags = Byte.toUnsignedInt(data[i++]);
			int itemId = readUint24(data, i); i += 3;
			Integer statId = null;
			if ((flags & FLAG_STAT_ID) != 0)      { statId     = readUint24(data, i); i += 3; }
			Integer upgradeId = null;
			if ((flags & FLAG_UPGRADE_ID) != 0)   { upgradeId  = readUint24(data, i); i += 3; }
			Integer upgrade2Id = null;
			if ((flags & FLAG_UPGRADE2_ID) != 0)  { upgrade2Id = readUint24(data, i); i += 3; }
			List<Integer> infusions = List.of();
			if ((flags & FLAG_INFUSIONS) != 0) {
				int infCount = Byte.toUnsignedInt(data[i++]);
				List<Integer> inf = new ArrayList<>(infCount);
				for (int n = 0; n < infCount; n++) { inf.add(readUint24(data, i)); i += 3; }
				infusions = inf;
			}
			pieces.add(new EquipmentPiece(slot, itemId, statId, upgradeId, upgrade2Id, infusions));
		}
		return new Equipment(pieces);
	}

	// --- Décodage équipement v1 ---

	private Equipment decodeEquipmentV1(byte[] data) {
		int i = 0;
		if (data[i++] != EQUIP_VERSION_V1) {
			throw new TechnicalException("Version équipement v1 invalide");
		}
		int count = Byte.toUnsignedInt(data[i++]);
		List<EquipmentPiece> pieces = new ArrayList<>(count);
		for (int p = 0; p < count; p++) {
			EquipmentSlot slot = EquipmentSlot.values()[Byte.toUnsignedInt(data[i++])];
			int itemId    = readUint24(data, i); i += 3;
			int statId    = readUint24(data, i); i += 3;
			int upgradeId = readUint24(data, i); i += 3;
			int upgrade2Id = readUint24(data, i); i += 3;
			int infCount  = Byte.toUnsignedInt(data[i++]);
			List<Integer> infusions = new ArrayList<>(infCount);
			for (int n = 0; n < infCount; n++) { infusions.add(readUint24(data, i)); i += 3; }
			pieces.add(new EquipmentPiece(slot, itemId,
					statId != 0 ? statId : null,
					upgradeId != 0 ? upgradeId : null,
					upgrade2Id != 0 ? upgrade2Id : null,
					infusions));
		}
		return new Equipment(pieces);
	}

	// --- Helpers chat code ---

	private byte[] decodeChatCode(String chatCode) {
		return Base64.getDecoder().decode(chatCode.replace("[&", "").replace("]", ""));
	}

	private String encodeChatCode(byte[] bytes) {
		return "[&" + Base64.getEncoder().encodeToString(bytes) + "]";
	}

	// --- Helpers DEFLATE ---

	private byte[] deflate(byte[] input) {
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
		deflater.setInput(input);
		deflater.finish();
		ByteArrayOutputStream out = new ByteArrayOutputStream(input.length);
		byte[] buffer = new byte[256];
		while (!deflater.finished()) {
			out.write(buffer, 0, deflater.deflate(buffer));
		}
		deflater.end();
		return out.toByteArray();
	}

	private byte[] inflate(byte[] input) {
		Inflater inflater = new Inflater();
		inflater.setInput(input);
		ByteArrayOutputStream out = new ByteArrayOutputStream(input.length * 3);
		byte[] buffer = new byte[256];
		try {
			while (!inflater.finished()) {
				out.write(buffer, 0, inflater.inflate(buffer));
			}
		} catch (DataFormatException e) {
			throw new TechnicalException("Décompression du code échouée");
		} finally {
			inflater.end();
		}
		return out.toByteArray();
	}

	// --- Helpers binaires little-endian ---

	private void writeUint24(ByteArrayOutputStream out, int value) {
		out.write(value & 0xFF);
		out.write((value >> 8) & 0xFF);
		out.write((value >> 16) & 0xFF);
	}

	private int readUint24(byte[] data, int offset) {
		return Byte.toUnsignedInt(data[offset])
				| (Byte.toUnsignedInt(data[offset + 1]) << 8)
				| (Byte.toUnsignedInt(data[offset + 2]) << 16);
	}
}
