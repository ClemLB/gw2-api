package fr.kuremento.gw2.web.rest.services.account.home.nodes;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/home/nodes">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class AccountNodesService extends AbstractService {

	@Value("${application.rest.endpoints.account-category.home-category.nodes}")
	private final String endpoint;

	public List<String> getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {}, apiKey);
	}

}
