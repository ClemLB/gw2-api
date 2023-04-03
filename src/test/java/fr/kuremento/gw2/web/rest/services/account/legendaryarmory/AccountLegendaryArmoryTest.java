package fr.kuremento.gw2.web.rest.services.account.legendaryarmory;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.Constants;
import fr.kuremento.gw2.web.rest.models.account.legendaryarmory.LegendaryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountLegendaryArmoryTest {

	@Autowired
	private AccountLegendaryArmoryService service;

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
		assertEquals("401 UNAUTHORIZED" + " : " + Constants.ERROR_401_403_MESSAGE.getValue(), actualMessage);
	}

	@Test
	@DisplayName("Check authentification exception")
	void test2() {
		Exception exception = assertThrows(TechnicalException.class, () -> {
			service.getWithAuthentification(simpleApiKey);
		});

		String actualMessage = exception.getMessage();
		assertEquals("403 FORBIDDEN" + " : " + Constants.ERROR_401_403_MESSAGE.getValue(), actualMessage);
	}

	@Test
	@DisplayName("Check authentification ok")
	void test3() {
		AtomicReference<List<LegendaryItem>> account = new AtomicReference<>();
		assertDoesNotThrow(() -> account.set(service.getWithAuthentification(fullApiKey)));
		assertNotNull(account.get(), "Service should return informations on legendary armory");
		assertFalse(account.get().isEmpty(), "Service should return informations on legendary armory");
	}
}
