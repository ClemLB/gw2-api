package fr.kuremento.gw2.web.rest.services.guild.treasury;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.Constants;
import fr.kuremento.gw2.web.rest.models.guild.treasury.TreasuryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuildTreasuryTest {

	@Autowired
	private GuildTreasuryService service;

	@Value("${test.api-key.full}")
	private String apiKey;

	@Test
	@DisplayName("Check no authentification exception")
	void test1() {
		Exception exception = assertThrows(TechnicalException.class,
										   () -> service.getWithAuthentification("64682C99-4D9A-EB11-81A8-E944283D67C1", ""));

		String actualMessage = exception.getMessage();
		assertEquals("401 UNAUTHORIZED" + " : " + Constants.ERROR_401_403_MESSAGE.getValue(), actualMessage);
	}

	@Test
	@DisplayName("Check authentification ok")
	void test2() {
		AtomicReference<List<TreasuryItem>> list = new AtomicReference<>();
		assertDoesNotThrow(() -> list.set(service.getWithAuthentification("64682C99-4D9A-EB11-81A8-E944283D67C1", apiKey)));
		assertNotNull(list.get(), "Service should return informations on treasury");
	}

}
