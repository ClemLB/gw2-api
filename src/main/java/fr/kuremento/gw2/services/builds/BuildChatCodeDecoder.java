package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.builds.DecodedBuild;
import fr.kuremento.gw2.models.builds.ProfessionId;
import fr.kuremento.gw2.models.builds.SpecLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BuildChatCodeDecoder {

	private static final int BUILD_CHAT_LINK_HEADER = 0x0D;

	public DecodedBuild decode(String chatCode) {
		String base64 = chatCode.replace("[&", "").replace("]", "");
		byte[] data = Base64.getDecoder().decode(base64);

		if (data.length < 28) {
			throw new TechnicalException("Chat code trop court : " + data.length + " bytes");
		}

		int header = Byte.toUnsignedInt(data[0]);
		if (header != BUILD_CHAT_LINK_HEADER) {
			throw new TechnicalException("Header de chat code invalide : 0x" + Integer.toHexString(header) + ", attendu 0x0D");
		}

		ProfessionId profession = ProfessionId.fromId(Byte.toUnsignedInt(data[1]));

		List<SpecLine> specLines = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			int offset = 2 + i * 2;
			int specId = Byte.toUnsignedInt(data[offset]);
			int traitByte = Byte.toUnsignedInt(data[offset + 1]);

			int trait1 = traitByte & 0x03;
			int trait2 = (traitByte >> 2) & 0x03;
			int trait3 = (traitByte >> 4) & 0x03;

			specLines.add(new SpecLine(specId, trait1, trait2, trait3));
		}

		// 10 skill palette IDs en little-endian uint16, indices pairs = terrestre
		List<Integer> terrestrialSkillPaletteIds = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			int offset = 8 + i * 4; // chaque paire terrestre/aquatique = 4 bytes
			int paletteId = Byte.toUnsignedInt(data[offset]) | (Byte.toUnsignedInt(data[offset + 1]) << 8);
			terrestrialSkillPaletteIds.add(paletteId);
		}

		return new DecodedBuild(profession, specLines, terrestrialSkillPaletteIds);
	}
}
