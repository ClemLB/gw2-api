package fr.kuremento.gw2.web.rest.services.legendaryarmory;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/legendaryarmory">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class LegendaryArmoryService extends AbstractService {

	@Value("${application.rest.endpoints.legendary-armory}")
	private final String endpoint;

	public List<Integer> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

}
