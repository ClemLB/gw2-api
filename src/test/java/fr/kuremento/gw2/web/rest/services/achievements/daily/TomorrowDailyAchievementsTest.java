package fr.kuremento.gw2.web.rest.services.achievements.daily;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TomorrowDailyAchievementsTest {

	@Autowired
	private TomorrowDailyAchievementsService service;

	@Test
	@DisplayName("Check achievements categories")
	void test1() {
		assertNotNull(service.get(), "Service should return categories of achievements");
	}
}
