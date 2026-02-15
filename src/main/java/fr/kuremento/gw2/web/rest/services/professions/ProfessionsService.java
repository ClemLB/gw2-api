package fr.kuremento.gw2.web.rest.services.professions;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.professions.Profession;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/professions">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class ProfessionsService extends AbstractService {

	@Value("${application.rest.endpoints.professions}")
	private final String endpoint;

	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Profession> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<Profession> get(List<String> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public Profession get(String name) {
		return super.get(super.buildURI(endpoint, name), new ParameterizedTypeReference<>() {});
	}
}
