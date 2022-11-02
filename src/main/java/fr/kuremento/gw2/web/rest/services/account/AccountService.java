package fr.kuremento.gw2.web.rest.services.account;

import fr.kuremento.gw2.web.rest.models.account.Account;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import fr.kuremento.gw2.web.rest.services.account.achievements.AccountAchievementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/account">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class AccountService extends AbstractService {

	private final AccountAchievementsService accountAchievementsService;

	@Value("${application.rest.endpoints.account-category.account}")
	private final String endpoint;

	public AccountAchievementsService achievements() {
		return accountAchievementsService;
	}

	public Account getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		}, apiKey);
	}
}
