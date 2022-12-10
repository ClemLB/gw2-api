package fr.kuremento.gw2.web.rest.services.account.bank;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/account/bank">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class AccountBankService extends AbstractService {

	@Value("${application.rest.endpoints.account-category.bank}")
	private final String endpoint;

	public List<Object> getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		}, apiKey);
	}
}
