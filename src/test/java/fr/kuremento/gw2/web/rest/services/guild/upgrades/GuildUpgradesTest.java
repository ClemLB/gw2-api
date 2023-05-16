package fr.kuremento.gw2.web.rest.services.guild.upgrades;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.Constants;
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
class GuildUpgradesTest {

	@Autowired
	private GuildUpgradesService service;

	@Value("${application.rest.config.page-maximum-size}")
	private Integer maxPageSize;

	@Value("${test.api-key.full}")
	private String apiKey;

	@Test
	@DisplayName("Check number of upgrades")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of colors id");
	}

	@Test
	@DisplayName("Check max number of upgrades per request exception is thrown")
	void test2() {
		var fakeIdsList = Arrays.stream(IntStream.generate(() -> new Random().nextInt(10000)).limit(maxPageSize + 1).toArray())
								.boxed()
								.toList();
		Exception exception = assertThrows(TooManyArgumentsException.class, () -> service.get(fakeIdsList));

		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains("Maximum number of arguments"));
	}

	@Test
	@DisplayName("Check max number of upgrades per request")
	void test3() {
		var fakeIdsList = List.of(55);
		var list = assertDoesNotThrow(() -> service.get(fakeIdsList));
		assertTrue(list.size() <= maxPageSize, String.format("Service should return at most %d colors", maxPageSize));
	}

	@Test
	@DisplayName("Check request one upgrade")
	void test4() {
		assertNotNull(service.get(55), "Requested color should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test5() {
		assertFalse(service.getAll().isEmpty(), "Service should return a list of colors");
	}

	@Test
	@DisplayName("Check no authentification")
	void test6() {
		Exception exception = assertThrows(TechnicalException.class, () -> {
			service.getWithAuthentification("116E0C0E-0035-44A9-BB22-4AE3E23127E5", "");
		});

		String actualMessage = exception.getMessage();
		assertEquals("401 UNAUTHORIZED" + " : " + Constants.ERROR_401_403_MESSAGE.getValue(), actualMessage);
	}

	@Test
	@DisplayName("Check authentification ok")
	void test7() {
		AtomicReference<List<Integer>> idsList = new AtomicReference<>();
		assertDoesNotThrow(() -> idsList.set(service.getWithAuthentification("64682C99-4D9A-EB11-81A8-E944283D67C1", apiKey)));
		assertNotNull(idsList.get(), "Service should return informations on upgrades for a guild");
	}

}
