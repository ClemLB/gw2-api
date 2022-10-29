package fr.kuremento.gw2.web.rest.services.account.achievements;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.web.rest.models.account.achievements.AccountAchievement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountAchievementsTest {

	@Autowired
	private AccountAchievementsService service;

	@Value("${test.api-key.account}")
	private String simpleApiKey;

	@Value("${test.api-key.full}")
	private String fullApiKey;

	@Test
	@DisplayName("Check no authentification exception")
	void test1() {
		Exception exception = assertThrows(TechnicalException.class, () -> {
			service.getWithAuthentification("");
		});

		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, "The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions");
	}

	@Test
	@DisplayName("Check authentification exception")
	void test2() {
		Exception exception = assertThrows(TechnicalException.class, () -> {
			service.getWithAuthentification(simpleApiKey);
		});

		String actualMessage = exception.getMessage();
		assertEquals(actualMessage, "The requested endpoint is authenticated and you did not provide a valid API key, or a valid API key without the necessary permissions");
	}

	@Test
	@DisplayName("Check authentification ok")
	void test3() {
		AtomicReference<List<AccountAchievement>> account = new AtomicReference<>();
		assertDoesNotThrow(() -> account.set(service.getWithAuthentification(fullApiKey)));
		assertNotNull(account.get(), "Service should return informations on account");
		assertFalse(account.get().isEmpty(), "Service should return informations on account");
	}

}
