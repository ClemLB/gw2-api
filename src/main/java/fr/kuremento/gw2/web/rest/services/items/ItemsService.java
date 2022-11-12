package fr.kuremento.gw2.web.rest.services.items;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/items">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class ItemsService extends AbstractService {

	@Value("${application.rest.endpoints.minis}")
	private final String endpoint;
}
