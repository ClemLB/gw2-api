package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import fr.kuremento.gw2.web.rest.models.items.details.enums.ConsumableType;
import fr.kuremento.gw2.web.rest.models.items.details.enums.ConsumableUnlockType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Consumable extends ItemDetails {

	@JsonProperty(value = "type", required = true)
	ConsumableType type;

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

}
