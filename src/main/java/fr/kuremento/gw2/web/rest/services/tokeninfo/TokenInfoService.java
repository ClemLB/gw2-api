package fr.kuremento.gw2.web.rest.services.tokeninfo;

import fr.kuremento.gw2.web.rest.models.token.TokenInfo;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/tokeninfo">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class TokenInfoService extends AbstractService {

	@Value("${application.rest.endpoints.token-info}")
	private final String endpoint;

	public TokenInfo getWithAuthentification(String apiKey) {
		return super.getWithAuthentification(super.buildURI(endpoint), new ParameterizedTypeReference<>() {}, apiKey);
	}
}
