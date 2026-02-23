package fr.kuremento.gw2.models.builds;

import java.util.List;

public record EquipmentPiece(
		EquipmentSlot slot,
		int itemId,
		Integer statId,
		Integer upgradeId,
		Integer upgrade2Id,
		List<Integer> infusionIds
) {

	public EquipmentPiece(EquipmentSlot slot, int itemId) {
		this(slot, itemId, null, null, null, List.of());
	}

	public EquipmentPiece(EquipmentSlot slot, int itemId, int statId) {
		this(slot, itemId, statId, null, null, List.of());
	}
}
