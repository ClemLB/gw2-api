package fr.kuremento.gw2.web.rest.services.achievements.daily;

import fr.kuremento.gw2.web.rest.models.achievements.DailyAchievements;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyAchievementsService extends AbstractService {

	private final TomorrowDailyAchievementsService tomorrowDailyAchievementsService;

	@Value("${application.rest.endpoints.achievements-category.daily}")
	private final String endpoint;

	public TomorrowDailyAchievementsService tomorrow() {
		return this.tomorrowDailyAchievementsService;
	}

	public DailyAchievements get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		});
	}

}
