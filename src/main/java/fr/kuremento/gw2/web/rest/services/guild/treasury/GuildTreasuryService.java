package fr.kuremento.gw2.web.rest.services.guild.treasury;

import fr.kuremento.gw2.web.rest.models.guild.treasury.TreasuryItem;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/guild/:id/treasury">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class GuildTreasuryService extends AbstractService {

	@Value("${application.rest.endpoints.guild-category.treasury}")
	private final String endpoint;

	public List<TreasuryItem> getWithAuthentification(String id, String apiKey) {
		return super.getWithAuthentification(super.buildURIWithParams(endpoint, id), new ParameterizedTypeReference<>() {}, apiKey);
	}
}
