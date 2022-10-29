package fr.kuremento.gw2.web.rest.services.achievements.daily;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DailyAchievementsTest {

	@Autowired
	private DailyAchievementsService service;

	@Test
	@DisplayName("Tomorrow service context")
	void test1() {
		assertNotNull(service.tomorrow(), "Service should not null");
	}

	@Test
	@DisplayName("Check achievements categories")
	void test2() {
		assertNotNull(service.get(), "Service should return categories of achievements");
	}
}
