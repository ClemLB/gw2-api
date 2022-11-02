package fr.kuremento.gw2.web.rest.models.achievements;

import com.fasterxml.jackson.annotation.JsonProperty;
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
