package fr.kuremento.gw2.web.rest.services.account;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.web.rest.models.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountTest {

	@Autowired
	private AccountService service;

	@Value("${test.api-key.account}")
	private String apiKey;

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
	@DisplayName("Check authentification ok")
	void test2() {
		AtomicReference<Account> account = new AtomicReference<>();
		assertDoesNotThrow(() -> account.set(service.getWithAuthentification(apiKey)));
		assertNotNull(account.get(), "Service should return informations on account");
	}

}
