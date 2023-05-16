package fr.kuremento.gw2.web.rest.models.guild.upgrades;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.guild.upgrades.enums.GuildUpgradeType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuildUpgrade {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("description")
	String description;

	@JsonProperty("type")
	GuildUpgradeType type;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("build_time")
	Integer buildTime;

	@JsonProperty("required_level")
	Integer requiredLevel;

	@JsonProperty("experience")
	Integer experience;

	@JsonProperty("prerequisites")
	List<Integer> prerequisites = new ArrayList<>();

	@JsonProperty("costs")
	List<UpgradeCost> costs = new ArrayList<>();
}
