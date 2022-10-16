package fr.kuremento.gw2.web.rest.services.account;

import fr.kuremento.gw2.web.rest.models.account.Account;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import fr.kuremento.gw2.web.rest.services.account.achievements.AchievementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service("account")
@RequiredArgsConstructor
public class AccountService extends AbstractService {

	@Value("${application.rest.endpoints.account}")
	private final String endpoint;

	@Qualifier("achievements")
	private final AchievementsService achievementsService;

	public AchievementsService achievements() {
		return achievementsService;
	}

	public Account getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		}, apiKey);
	}
}
