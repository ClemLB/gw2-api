package fr.kuremento.gw2.web.rest.services.account.achievements;

import fr.kuremento.gw2.web.rest.models.account.achievements.AccountAchievement;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/account/achievements">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class AccountAchievementsService extends AbstractService {

	@Value("${application.rest.endpoints.account-category.achievement}")
	private final String endpoint;

	public List<AccountAchievement> getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		}, apiKey);
	}
}
