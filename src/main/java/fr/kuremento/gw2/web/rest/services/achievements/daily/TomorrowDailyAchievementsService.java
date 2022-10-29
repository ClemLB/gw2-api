package fr.kuremento.gw2.web.rest.services.achievements.daily;

import fr.kuremento.gw2.web.rest.models.achievements.DailyAchievements;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TomorrowDailyAchievementsService extends AbstractService {

	@Value("${application.rest.endpoints.achievements-category.tomorrow}")
	private String endpoint;

	public DailyAchievements get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		});
	}

}
