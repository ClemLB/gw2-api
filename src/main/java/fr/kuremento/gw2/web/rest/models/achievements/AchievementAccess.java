package fr.kuremento.gw2.web.rest.models.achievements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementAccess {

	@JsonProperty("product")
	DailyAchievementProduct product;
	@JsonProperty("condition")
	DailyAchievementCondition condition;
}
