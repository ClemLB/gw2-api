package fr.kuremento.gw2.web.rest.models.achievements;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.achievements.enums.MasteryRegion;
import fr.kuremento.gw2.web.rest.models.achievements.enums.RewardType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementsReward {

	@JsonProperty("type")
	RewardType type;

	@JsonProperty("count")
	Integer count;

	@JsonProperty("id")
	Integer id;

	@JsonProperty("region")
	MasteryRegion region;

}
