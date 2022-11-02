package fr.kuremento.gw2.web.rest.models.achievements.daily;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailyAchievements {

	@JsonProperty("pve")
	List<DailyAchievement> pve;

	@JsonProperty("pvp")
	List<DailyAchievement> pvp;

	@JsonProperty("wvw")
	List<DailyAchievement> wvw;

	@JsonProperty("fractals")
	List<DailyAchievement> fractals;

	@JsonProperty("special")
	List<DailyAchievement> special;

}
