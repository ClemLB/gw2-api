package fr.kuremento.gw2.web.rest.services.achievements.daily;

import fr.kuremento.gw2.web.rest.models.achievements.daily.DailyAchievements;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/achievements/daily/tomorrow">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class TomorrowDailyAchievementsService extends AbstractService {

	@Value("${application.rest.endpoints.achievements-category.tomorrow}")
	private final String endpoint;

	public DailyAchievements get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

}
