package fr.kuremento.gw2.ui;

import fr.kuremento.gw2.models.builds.EquipmentPiece;
import fr.kuremento.gw2.models.builds.EquipmentSlot;

import java.awt.image.BufferedImage;
import java.util.List;

public record SlotData(
		Integer itemId,
		Integer statId,
		Integer upgradeId,
		Integer upgrade2Id,
		String statName,
		String upgradeName,
		String upgrade2Name,
		String itemName,
		BufferedImage itemIcon,
		BufferedImage upgradeIcon,
		BufferedImage upgrade2Icon
) {

	public EquipmentPiece toEquipmentPiece(EquipmentSlot slot) {
		if (itemId == null) {
			throw new IllegalStateException("itemId est requis pour créer un EquipmentPiece");
		}
		return new EquipmentPiece(slot, itemId, statId, upgradeId, upgrade2Id, List.of());
	}
}
