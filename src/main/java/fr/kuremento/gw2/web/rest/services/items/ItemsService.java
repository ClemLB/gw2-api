package fr.kuremento.gw2.web.rest.services.items;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.items.Item;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/items">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class ItemsService extends AbstractService {

	@Value("${application.rest.endpoints.items}")
	private final String endpoint;

	public List<Integer> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Item> get(List<Integer> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public Item get(Integer id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}
}
