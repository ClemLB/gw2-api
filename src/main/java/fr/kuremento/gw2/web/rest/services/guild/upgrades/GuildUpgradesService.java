package fr.kuremento.gw2.web.rest.services.guild.upgrades;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.guild.upgrades.GuildUpgrade;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/guild/upgrades">Wiki</a>
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/guild/:id/upgrades">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class GuildUpgradesService extends AbstractService {

	@Value("${application.rest.endpoints.guild-category.upgrades}")
	private final String endpoint;

	@Value("${application.rest.endpoints.guild-category.upgrades-id}")
	private final String endpointWithId;

	public List<Integer> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<GuildUpgrade> get(List<Integer> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public GuildUpgrade get(Integer id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}

	public List<GuildUpgrade> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Integer> getWithAuthentification(String id, String apiKey) {
		return super.getWithAuthentification(super.buildURIWithParams(endpointWithId, id), new ParameterizedTypeReference<>() {}, apiKey);
	}
}
