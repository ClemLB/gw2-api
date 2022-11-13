package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDetails {

	@JsonProperty("type")
	String type;

	@JsonProperty("weight_class")
	WeightClass weightClass;

	@JsonProperty("defense")
	Integer defense;

	@JsonProperty("size")
	Integer size;

	@JsonProperty("no_sell_or_sort")
	Boolean noSellOrSort;

	@JsonProperty("description")
	String description;

	@JsonProperty("duration_ms")
	Integer durationMs;

	@JsonProperty("unlock_type")
	ConsumableUnlockType unlockType;

	@JsonProperty("color_id")
	Integer colorId;

	@JsonProperty("recipe_id")
	Integer recipeId;

	@JsonProperty("extra_recipe_ids")
	List<Integer> extraRecipeIds = new ArrayList<>();

	@JsonProperty("guild_upgrade_id")
	Integer guildUpgradeId;

	@JsonProperty("apply_count")
	Integer applyCount;

	@JsonProperty("name")
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("skins")
	List<Integer> skins = new ArrayList<>();

	@JsonProperty("minipet_id")
	Integer minipetId;

	@JsonProperty("charges")
	Integer charges;

	@JsonProperty("flags")
	List<UpgradeComponentFlags> flags = new ArrayList<>();

	@JsonProperty("infusion_upgrade_flags")
	List<InfusionSlotFlag> infusionUpgradeFlags = new ArrayList<>();

	@JsonProperty("suffix")
	String suffix;

	@JsonProperty("bonuses")
	List<String> bonuses = new ArrayList<>();

	@JsonProperty("damage_type")
	WeaponDamageType damageType;

	@JsonProperty("min_power")
	Integer minPower;

	@JsonProperty("max_power")
	Integer maxPower;

	@JsonProperty("vendor_ids")
	List<Integer> vendorIds = new ArrayList<>();


	@JsonProperty("infusion_slots")
	List<InfusionSlot> infusionSlots = new ArrayList<>();

	@JsonProperty("attribute_adjustment")
	Integer attributeAdjustment;

	@JsonProperty("infix_upgrade")
	InfixUpgrade infixUpgrade;

	@JsonProperty("suffix_item_id")
	Integer suffixItemId;

	@JsonProperty("secondary_suffix_item_id")
	String secondarySuffixItemId;

	@JsonProperty("stat_choices")
	List<Integer> statChoices = new ArrayList<>();

}
