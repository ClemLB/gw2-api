package fr.kuremento.gw2.web.rest.services.home.nodes;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.home.Node;
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
public class HomeNodesService extends AbstractService {

	@Value("${application.rest.endpoints.home-category.nodes}")
	private final String endpoint;

	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Node> get(List<String> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public Node get(String id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}
}
