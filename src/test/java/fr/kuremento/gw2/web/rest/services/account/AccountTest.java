package fr.kuremento.gw2.web.rest.services.account;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.Constants;
import fr.kuremento.gw2.web.rest.models.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTest {

	@Autowired
	private AccountService service;

	@Value("${test.api-key.account}")
	private String apiKey;

	@Test
	@DisplayName("Achievements service context")
	void test1() {
		assertNotNull(service.achievements(), "Service should not null");
	}

	@Test
	@DisplayName("Check no authentification exception")
	void test2() {
		Exception exception = assertThrows(TechnicalException.class, () -> {
			service.getWithAuthentification("");
		});

		String actualMessage = exception.getMessage();
		assertEquals("401 UNAUTHORIZED" + " : " + Constants.ERROR_401_403_MESSAGE.getValue(), actualMessage);
	}

	@Test
	@DisplayName("Check authentification ok")
	void test3() {
		AtomicReference<Account> account = new AtomicReference<>();
		assertDoesNotThrow(() -> account.set(service.getWithAuthentification(apiKey)));
		assertNotNull(account.get(), "Service should return informations on account");
	}

	@Test
	@DisplayName("Bank service context")
	void test4() {
		assertNotNull(service.bank(), "Service should not null");
	}

	@Test
	@DisplayName("Legendary armory service context")
	void test5() {
		assertNotNull(service.legendaryArmory(), "Service should not null");
	}

	@Test
	@DisplayName("Home service context")
	void test6() {
		assertNotNull(service.home(), "Service should not null");
	}

}
