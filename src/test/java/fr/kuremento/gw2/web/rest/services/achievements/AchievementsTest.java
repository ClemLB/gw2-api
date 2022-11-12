package fr.kuremento.gw2.web.rest.services.achievements;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.achievements.Achievements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AchievementsTest {

	@Autowired
	private AchievementsService service;

	@Value("${application.rest.config.page-maximum-size}")
	private Integer maxPageSize;

	@Test
	@DisplayName("Daily service context")
	void test1() {
		assertNotNull(service.daily(), "Service should not null");
	}

	@Test
	@DisplayName("Check number of achievements")
	void test2() {
		assertFalse(service.get().isEmpty(), "Service should return a list of achievements id");
	}

	@Test
	@DisplayName("Check max number of achievements per request exception is thrown")
	void test3() {
		var fakeIdsList = Arrays.stream(IntStream.generate(() -> new Random().nextInt(10000)).limit(maxPageSize + 1).toArray()).boxed().toList();
		Exception exception = assertThrows(TooManyArgumentsException.class, () -> {
			service.get(fakeIdsList);
		});

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains("Maximum number of arguments"));
	}

	@Test
	@DisplayName("Check max number of achievements per request")
	void test4() {
		var fakeIdsList = List.of(1);
		AtomicReference<List<Achievements>> list = new AtomicReference<>();
		assertDoesNotThrow(() -> list.set(service.get(fakeIdsList)));
		assertTrue(list.get().size() <= maxPageSize, String.format("Service should return at most %d achievements", maxPageSize));
	}

	@Test
	@DisplayName("Check request one achievement")
	void test5() {
		assertNotNull(service.get(1), "Requested achievement should not be null");
	}

	@Test
	@DisplayName("Categories service context")
	void test6() {
		assertNotNull(service.categories(), "Service should not null");
	}

	@Test
	@DisplayName("Groups service context")
	void test7() {
		assertNotNull(service.groups(), "Service should not null");
	}
}
