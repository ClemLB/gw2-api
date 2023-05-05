package fr.kuremento.gw2.web.rest.services.account.home;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import fr.kuremento.gw2.web.rest.services.account.home.nodes.AccountNodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountHomeService extends AbstractService {

	@Value("${application.rest.endpoints.account-category.home-category.home}")
	private final String endpoint;

	private final AccountNodesService accountNodesService;

	public AccountNodesService nodes() {
		return this.accountNodesService;
	}

	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}
}
