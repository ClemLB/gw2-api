package fr.kuremento.gw2.web.rest.services.quaggans;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.GetService;
import fr.kuremento.gw2.web.rest.models.quaggans.Quaggan;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("quaggans")
@RequiredArgsConstructor
public class QuaggansService extends AbstractService implements GetService<String, Quaggan> {

	@Value("${application.rest.endpoints.quaggans}")
	private final String endpoint;

	@Override
	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		});
	}

	@Override
	public List<Quaggan> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {
		});
	}

	@Override
	public List<Quaggan> get(List<String> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {
		});
	}

	@Override
	public Quaggan get(String id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {
		});
	}

}
