package fr.kuremento.gw2.web.rest.models.achievements.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementCategory {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("description")
	String description;

	@JsonProperty("order")
	Integer order;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("achievements")
	List<AchievementInCategory> achievements = new ArrayList<>();

	@JsonProperty("tomorrow")
	List<AchievementInCategory> tomorrow = new ArrayList<>();

}
