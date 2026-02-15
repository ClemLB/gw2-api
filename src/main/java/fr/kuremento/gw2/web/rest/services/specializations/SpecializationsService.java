package fr.kuremento.gw2.web.rest.services.specializations;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.specializations.Specialization;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/specializations">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class SpecializationsService extends AbstractService {

	@Value("${application.rest.endpoints.specializations}")
	private final String endpoint;

	public List<Integer> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Specialization> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Specialization> get(List<Integer> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public Specialization get(Integer id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}
}
