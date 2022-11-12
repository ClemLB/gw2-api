package fr.kuremento.gw2.web.rest.services;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.services.colors.ColorsService;
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
class ColorsTest {

	@Autowired
	private ColorsService service;

	@Value("${application.rest.config.page-maximum-size}")
	private Integer maxPageSize;

	@Test
	@DisplayName("Check number of colors")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of colors id");
	}

	@Test
	@DisplayName("Check max number of colors per request exception is thrown")
	void test2() {
		var fakeIdsList = Arrays.stream(IntStream.generate(() -> new Random().nextInt(10000)).limit(maxPageSize + 1).toArray()).boxed().toList();
		Exception exception = assertThrows(TooManyArgumentsException.class, () -> {
			service.get(fakeIdsList);
		});

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains("Maximum number of arguments"));
	}

	@Test
	@DisplayName("Check max number of colors per request")
	void test3() throws TooManyArgumentsException {
		var fakeIdsList = List.of(1);
		assertDoesNotThrow(() -> service.get(fakeIdsList));
		var achievementsList = service.get(fakeIdsList);
		assertTrue(achievementsList.size() <= maxPageSize, String.format("Service should return at most %d colors", maxPageSize));
	}

	@Test
	@DisplayName("Check request one color")
	void test4() {
		assertNotNull(service.get(1), "Requested color should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test5() {
		assertFalse(service.getAll().isEmpty(), "Service should return a list of colors");
	}
}
