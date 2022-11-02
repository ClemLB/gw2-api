package fr.kuremento.gw2.web.rest.services.achievements.groups;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.achievements.groups.AchievementGroup;
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
public class GroupsAchievementsTest {

	@Autowired
	private GroupsAchievementsService service;

	@Value("${application.rest.config.page-maximum-size}")
	private Integer maxPageSize;

	@Test
	@DisplayName("Check number of groups")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of groups id");
	}

	@Test
	@DisplayName("Check max number of groups per request exception is thrown")
	void test2() {
		var fakeIdsList = Arrays.stream(IntStream.generate(() -> new Random().nextInt(10000)).limit(maxPageSize + 1).toArray()).boxed().map(String::valueOf).toList();
		Exception exception = assertThrows(TooManyArgumentsException.class, () -> service.get(fakeIdsList));

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains("Maximum number of arguments"));
	}

	@Test
	@DisplayName("Check max number of groups per request")
	void test3() {
		var fakeIdsList = List.of("BE8B9954-5B55-4FCB-9022-B871AD00EAAB");
		AtomicReference<List<AchievementGroup>> list = new AtomicReference<>();
		assertDoesNotThrow(() -> list.set(service.get(fakeIdsList)));
		assertTrue(list.get().size() <= maxPageSize, String.format("Service should return at most %d groups", maxPageSize));
	}

	@Test
	@DisplayName("Check request one group")
	void test4() {
		assertNotNull(service.get("BE8B9954-5B55-4FCB-9022-B871AD00EAAB"), "Requested group should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test5() {
		assertFalse(service.getAll().isEmpty(), "Service should return a list of groups");
	}
}
