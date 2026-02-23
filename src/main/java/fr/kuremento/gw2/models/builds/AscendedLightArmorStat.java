package fr.kuremento.gw2.models.builds;

import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

/**
 * Statistiques disponibles pour l'armure légère élevée.
 * Les IDs d'items correspondent aux pièces d'armure légère de la statistique.
 * L'ordre des slots : HELM, SHOULDERS, CHEST, GLOVES, LEGGINGS, BOOTS
 */
@Getter
public enum AscendedLightArmorStat {

	APOTHECARY("Apothicaire", 605,
			48045, // HELM
			48047, // SHOULDERS
			48043, // CHEST
			48044, // GLOVES
			48046, // LEGGINGS
			48042  // BOOTS
	),
	ASSASSIN("Assassin", 753,
			48135, 48137, 48133, 48134, 48136, 48132),
	BERSERKER("Berserker", 161,
			48081, 48083, 48079, 48080, 48082, 48078),
	BRINGER("Du pourvoyeur", 1032,
			86671, 86628, 86670, 86691, 86633, 86520),
	CARRION("Nécrophage", 160,
			47955, 47957, 47953, 47954, 47956, 47952),
	CAVALIER("Cavalier", 616,
			48171, 48173, 48169, 48170, 48172, 48168),
	CELESTIAL("Céleste", 559,
			47937, 47939, 47935, 47936, 47938, 47934),
	CLERIC("Clerc", 155,
			48009, 48011, 48007, 48008, 48010, 48006),
	COMMANDER("Commandant", 1131,
			75727, 70555, 72182, 77208, 76139, 72663),
	CRUSADER("Croisé", 1109,
			77108, 76186, 76854, 74836, 73234, 73766),
	DIRE("Sanguinaire", 754,
			47991, 47993, 47989, 47990, 47992, 47988),
	DIVINER("Devin", 1556,
			89912, 89628, 89768, 89925, 89940, 89452),
	DRAGON("Draconique", 1681,
			97161, 96731, 95626, 97390, 96818, 96062),
	GIVER("Bienfaiteur", 628,
			86642, 86650, 86486, 86518, 86471, 86422),
	GRIEVING("Deuil", 1344,
			85296, 85241, 85319, 85048, 85352, 85007),
	HARRIER("Du circaète", 1363,
			85161, 85089, 85098, 85004, 84926, 85317),
	KNIGHT("Chevalier", 158,
			47918, 47916, 47920, 47919, 47917, 47921),
	MAGI("Mage", 156,
			48027, 48029, 48025, 48026, 48028, 48024),
	MARAUDER("Maraudeur", 1111,
			75940, 76671, 71968, 74518, 75183, 72251),
	MARSHAL("Maréchal", 1364,
			85285, 85066, 85288, 84866, 85180, 85342),
	MINSTREL("Ménestrel", 1123,
			73970, 73670, 74448, 75866, 70414, 75349),
	NOMAD("Nomade", 1026,
			66376, 66366, 66370, 66359, 66367, 66371),
	PLAGUEDOCTOR("De médecin du Fléau", 1559,
			88022, 88085, 87977, 87960, 87806, 87696),
	RABID("Enragé", 154,
			47973, 47975, 47971, 47972, 47974, 47970),
	RAMPAGER("Saccageur", 159,
			48117, 48119, 48115, 48116, 48118, 48114),
	RITUALIST("Ritualiste", 1686,
			96005, 97313, 96962, 96140, 97476, 96739),
	SENTINEL("Sentinelle", 686,
			48207, 48209, 48205, 48206, 48208, 48204),
	SERAPH("Séraphin", 1222,
			80661, 80448, 80244, 80268, 80664, 80491),
	SETTLER("Colon", 700,
			48153, 48155, 48151, 48152, 48154, 48150),
	SHAMAN("Chamane", 153,
			48189, 48191, 48187, 48188, 48190, 48186),
	SINISTER("Sinistre", 1067,
			67442, 67449, 67453, 67452, 67450, 67454),
	SOLDIER("Militaire", 162,
			48099, 48101, 48097, 48098, 48100, 48096),
	TRAILBLAZER("Pionnier", 1085,
			76834, 76406, 74524, 70993, 73392, 72816),
	VALKYRIE("Valkyrien", 157,
			48063, 48065, 48061, 48062, 48064, 48060),
	VIGILANT("Vigilant", 1118,
			74522, 75023, 73882, 74512, 71570, 72369),
	VIPER("Vipérin", 1153,
			75770, 73225, 71436, 73852, 75378, 74264),
	WANDERER("Voyageur", 1140,
			73521, 71308, 72494, 70699, 72719, 71242),
	ZEALOT("Zélote", 799,
			49620, 49618, 49622, 49621, 49619, 49623);

	private final String label;
	private final int statId;
	private final int helm;
	private final int shoulders;
	private final int chest;
	private final int gloves;
	private final int leggings;
	private final int boots;

	AscendedLightArmorStat(String label, int statId, int helm, int shoulders, int chest, int gloves, int leggings, int boots) {
		this.label = label;
		this.statId = statId;
		this.helm = helm;
		this.shoulders = shoulders;
		this.chest = chest;
		this.gloves = gloves;
		this.leggings = leggings;
		this.boots = boots;
	}

	public EquipmentPiece toEquipmentPiece(EquipmentSlot slot) {
		int itemId = switch (slot) {
			case HELM -> helm;
			case SHOULDERS -> shoulders;
			case CHEST -> chest;
			case GLOVES -> gloves;
			case LEGGINGS -> leggings;
			case BOOTS -> boots;
			default -> throw new IllegalArgumentException("Slot non valide pour l'armure légère : " + slot);
		};
		return new EquipmentPiece(slot, itemId, statId, null, null, List.of());
	}

	public List<EquipmentPiece> toEquipmentPieces() {
		return Stream.of(
				new EquipmentPiece(EquipmentSlot.HELM, helm, statId, null, null, List.of()),
				new EquipmentPiece(EquipmentSlot.SHOULDERS, shoulders, statId, null, null, List.of()),
				new EquipmentPiece(EquipmentSlot.CHEST, chest, statId, null, null, List.of()),
				new EquipmentPiece(EquipmentSlot.GLOVES, gloves, statId, null, null, List.of()),
				new EquipmentPiece(EquipmentSlot.LEGGINGS, leggings, statId, null, null, List.of()),
				new EquipmentPiece(EquipmentSlot.BOOTS, boots, statId, null, null, List.of())
		).filter(p -> p.itemId() != 0).toList();
	}
}
