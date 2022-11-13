package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TypeOfItem {

	ARMOR("Armor"),
	BACK("Back"),
	BAG("Bag"),
	CONSUMABLE("Consumable"),
	CONTAINER("Container"),
	CRAFTING_MATERIAL("CraftingMaterial"),
	GATHERING("Gathering"),
	GIZMO("Gizmo"),
	JADE_TECH_MODULE("JadeTechModule"),
	KEY("Key"),
	MINI_PET("MiniPet"),
	POWER_CORE("PowerCore"),
	TOOL("Tool"),
	TRAIT("Trait"),
	TRINKET("Trinket"),
	TROPHY("Trophy"),
	UPGRADE_COMPONENT("UpgradeComponent"),
	WEAPON("Weapon");

	private final String value;

	@JsonValue
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}
