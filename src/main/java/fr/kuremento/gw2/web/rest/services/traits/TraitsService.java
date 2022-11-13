package fr.kuremento.gw2.web.rest.services.traits;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/traits">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class TraitsService {

	@Value("${application.rest.endpoints.traits}")
	private final String endpoint;
}
