package fr.kuremento.gw2.web.rest.services.account.legendaryarmory;

import fr.kuremento.gw2.web.rest.models.account.legendaryarmory.LegendaryItem;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/account/legendaryarmory">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class AccountLegendaryArmoryService extends AbstractService {

	@Value("${application.rest.endpoints.account-category.legendary-armory}")
	private final String endpoint;

	public List<LegendaryItem> getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {}, apiKey);
	}
}
