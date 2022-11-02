package fr.kuremento.gw2.web.rest.models.achievements;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.achievements.enums.AchievementFlag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Achievements {
	@JsonProperty("id")
	Integer id;
	@JsonProperty("icon")
	String icon;
	@JsonProperty("name")
	String name;
	@JsonProperty("description")
	String description;
	@JsonProperty("requirement")
	String requirement;
	@JsonProperty("locked_text")
	String lockedText;
	@JsonProperty("type")
	String type;
	@JsonProperty("flags")
	List<AchievementFlag> flags = new ArrayList<>();
	@JsonProperty("tiers")
	List<AchievementTier> tiers = new ArrayList<>();
	@JsonProperty("prerequisites")
	List<Integer> prerequisites = new ArrayList<>();
	@JsonProperty("rewards")
	List<AchievementsReward> rewards = new ArrayList<>();
	@JsonProperty("bits")
	List<AchievementProgress> bits = new ArrayList<>();
	@JsonProperty("point_cap")
	Integer pointCap;
}
