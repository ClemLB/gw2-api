package fr.kuremento.gw2.web.rest.services.achievements.categories;

import fr.kuremento.gw2.web.rest.services.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementsCategoriesService extends AbstractService {

	@Value("${application.rest.endpoints.achievements-category.categories}")
	private final String endpoint;
}
