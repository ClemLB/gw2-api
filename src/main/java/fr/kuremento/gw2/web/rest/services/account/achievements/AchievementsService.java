package fr.kuremento.gw2.web.rest.services.account.achievements;

import fr.kuremento.gw2.web.rest.models.account.achievements.Achievement;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("achievements")
@RequiredArgsConstructor
public class AchievementsService extends AbstractService {

	@Value("${application.rest.endpoints.achievement}")
	private final String endpoint;

	public List<Achievement> getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		}, apiKey);
	}
}
