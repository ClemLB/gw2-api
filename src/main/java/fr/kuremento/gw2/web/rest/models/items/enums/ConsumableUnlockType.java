package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConsumableUnlockType {

	BAG_SLOT("BagSlot"),
	BANK_TAB("BankTab"),
	CHAMPION("Champion"),
	COLLECTIBLE_CAPACITY("CollectibleCapacity"),
	CONTENT("Content"),
	CRAFTING_RECIPE("CraftingRecipe"),
	DYE("Dye"),
	GLIDER_SKIN("GliderSkin"),
	MINIPET("Minipet"),
	MS("Ms"),
	OUTFIT("Outfit"),
	RANDOM_ULOCK("RandomUlock"),
	SHARED_SLOT("SharedSlot"),
	JADE_BOT_SKIN("JadeBotSkin"),
	BUILD_LIBRARY_SLOT("BuildLibrarySlot"),
	GEAR_LOADOUT_TAB("GearLoadoutTab"),
	BUILD_LOADOUT_TAB("BuildLoadoutTab");

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
