package fr.kuremento.gw2.web.rest.services.guild;

import fr.kuremento.gw2.web.rest.models.guild.Guild;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/guild/:id">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class GuildService extends AbstractService {

    @Value("${application.rest.endpoints.guild-category.guild-id}")
    private final String endpoint;

    public Guild get(String id) {
        return super.get(super.buildURIWithParams(endpoint, id), new ParameterizedTypeReference<>() {
        });
    }

    public Guild getWithAuthentification(String id, String apiKey) {
        return super.getWithAuthentification(super.buildURIWithParams(endpoint, id), new ParameterizedTypeReference<>() {
        }, apiKey);
    }
}
