package fr.kuremento.gw2.web.rest.services.itemstats;

import fr.kuremento.gw2.web.rest.models.itemstats.ItemStat;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/itemstats">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class ItemStatsService extends AbstractService {

	@Value("${application.rest.endpoints.itemstats}")
	private final String endpoint;

	public ItemStat get(Integer id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}
}
