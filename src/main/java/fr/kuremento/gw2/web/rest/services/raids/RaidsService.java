package fr.kuremento.gw2.web.rest.services.raids;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/raids">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class RaidsService {

	@Value("${application.rest.endpoints.raids}")
	private final String endpoint;
}
