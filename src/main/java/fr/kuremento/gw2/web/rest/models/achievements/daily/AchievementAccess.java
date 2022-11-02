package fr.kuremento.gw2.web.rest.models.achievements.daily;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.achievements.daily.enums.DailyAchievementCondition;
import fr.kuremento.gw2.web.rest.models.achievements.daily.enums.DailyAchievementProduct;
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
