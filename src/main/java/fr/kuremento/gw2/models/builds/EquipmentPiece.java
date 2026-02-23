package fr.kuremento.gw2.models.builds;

import java.util.List;

public record EquipmentPiece(EquipmentSlot slot, int itemId, Integer statId, Integer upgradeId, Integer upgrade2Id, List<Integer> infusionIds) {

    // Pour les reliques
    public EquipmentPiece(EquipmentSlot slot, int itemId) {
        this(slot, itemId, null, null, null, List.of());
    }

    // Pour les armes
    public EquipmentPiece(EquipmentSlot slot, int itemId, int statId, int upgradeId) {
        this(slot, itemId, statId, upgradeId, null, List.of());
    }

    public EquipmentPiece(EquipmentSlot slot, AscendedLightArmorStat armor, int upgradeId) {
        this(slot, armor.toEquipmentPiece(slot).itemId(), armor.toEquipmentPiece(slot).statId(), upgradeId, null, List.of());
    }

    // Pour les accessoires
    public EquipmentPiece(EquipmentSlot slot, int itemId, int statId) {
        this(slot, itemId, statId, null, null, List.of());
    }
}
