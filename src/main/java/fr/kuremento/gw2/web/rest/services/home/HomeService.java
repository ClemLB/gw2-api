package fr.kuremento.gw2.web.rest.services.home;

import fr.kuremento.gw2.web.rest.services.home.nodes.HomeNodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final HomeNodesService nodesService;

	public HomeNodesService nodes() {
		return this.nodesService;
	}

}
