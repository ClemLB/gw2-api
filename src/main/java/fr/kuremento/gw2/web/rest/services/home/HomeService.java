package fr.kuremento.gw2.web.rest.services.home;

import fr.kuremento.gw2.web.rest.services.home.cats.HomeCatsService;
import fr.kuremento.gw2.web.rest.services.home.nodes.HomeNodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final HomeNodesService nodesService;
	private final HomeCatsService catsService;

	public HomeNodesService nodes() {
		return this.nodesService;
	}

	public HomeCatsService cats() {
		return this.catsService;
	}

}
