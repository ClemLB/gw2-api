package fr.kuremento.gw2.web.rest.services.quaggans;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.quaggans.Quaggan;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuaggansService extends AbstractService {

	@Value("${application.rest.endpoints.quaggans}")
	private String endpoint;

	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		});
	}

	public List<Quaggan> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {
		});
	}

	public List<Quaggan> get(List<String> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {
		});
	}

	public Quaggan get(String id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {
		});
	}

}
