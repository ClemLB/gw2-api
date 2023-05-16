package fr.kuremento.gw2.web.rest.models.guild.upgrades;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.guild.upgrades.enums.GuildUpgradeCostType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpgradeCost {

	@JsonProperty("type")
	GuildUpgradeCostType type;

	@JsonProperty("name")
	String name;

	@JsonProperty("count")
	Integer count;

	@JsonProperty("item_id")
	Integer itemId;
}
