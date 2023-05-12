package fr.kuremento.gw2.web.rest.services.achievements.groups;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.achievements.groups.AchievementGroup;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @see <a href="https://wiki.guildwars2.com/wiki/API:2/achievements/groups">Wiki</a>
 */
@Service
@RequiredArgsConstructor
public class GroupsAchievementsService extends AbstractService {

	@Value("${application.rest.endpoints.achievements-category.groups}")
	private final String endpoint;

	public List<String> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {});
	}

	public List<AchievementGroup> get(List<String> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {});
	}

	public AchievementGroup get(String id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {});
	}

	public List<AchievementGroup> getAll() {
		return super.get(super.buildURIAllIds(endpoint), new ParameterizedTypeReference<>() {});
	}
}
