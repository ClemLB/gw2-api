package fr.kuremento.gw2.web.rest.models.achievements.categories;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.achievements.daily.AchievementAccess;
import fr.kuremento.gw2.web.rest.models.achievements.categories.enums.AchievementInCategoryFlag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementInCategory {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("required_access")
	AchievementAccess requiredAccess;

	@JsonProperty("flags")
	List<AchievementInCategoryFlag> flags = new ArrayList<>();

	@JsonProperty("level")
	List<Integer> level = new ArrayList<>(2);

}
