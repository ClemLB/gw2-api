package fr.kuremento.gw2.web.rest.services.achievements.categories;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoriesAchievementsTest {

	@Autowired
	private CategoriesAchievementsService service;

	@Value("${application.rest.config.page-maximum-size}")
	private Integer maxPageSize;

	@Test
	@DisplayName("Check number of categories")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of categories id");
	}

	@Test
	@DisplayName("Check max number of categories per request exception is thrown")
	void test2() {
		var fakeIdsList = Arrays.stream(IntStream.generate(() -> new Random().nextInt(10000)).limit(maxPageSize + 1).toArray()).boxed().toList();
		Exception exception = assertThrows(TooManyArgumentsException.class, () -> service.get(fakeIdsList));

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains("Maximum number of arguments"));
	}

	@Test
	@DisplayName("Check max number of categories per request")
	void test3() {
		var fakeIdsList = List.of(1);
		var list = assertDoesNotThrow(() -> service.get(fakeIdsList));
		assertTrue(list.size() <= maxPageSize, String.format("Service should return at most %d categories", maxPageSize));
	}

	@Test
	@DisplayName("Check request one category")
	void test4() {
		assertNotNull(service.get(1), "Requested category should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test5() {
		assertFalse(service.getAll().isEmpty(), "Service should return a list of categories");
	}
}
