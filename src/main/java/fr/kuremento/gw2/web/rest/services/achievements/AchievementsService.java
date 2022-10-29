package fr.kuremento.gw2.web.rest.services.achievements;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.achievements.Achievements;
import fr.kuremento.gw2.web.rest.services.AbstractService;
import fr.kuremento.gw2.web.rest.services.achievements.daily.DailyAchievementsService;
import fr.kuremento.gw2.web.rest.services.achievements.groups.GroupsAchievementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementsService extends AbstractService {

	private final DailyAchievementsService dailyAchievementsService;
	private final GroupsAchievementsService groupsAchievementsService;

	@Value("${application.rest.endpoints.achievements-category.achievements}")
	private final String endpoint;

	public DailyAchievementsService daily() {
		return this.dailyAchievementsService;
	}

	public GroupsAchievementsService groups() {
		return this.groupsAchievementsService;
	}

	public List<Integer> get() {
		return super.get(super.buildURI(endpoint), new ParameterizedTypeReference<>() {
		});
	}

	public List<Achievements> get(List<Integer> ids) throws TooManyArgumentsException {
		return super.get(super.buildURI(endpoint, ids), new ParameterizedTypeReference<>() {
		});
	}

	public Achievements get(Integer id) {
		return super.get(super.buildURI(endpoint, id), new ParameterizedTypeReference<>() {
		});
	}
}
