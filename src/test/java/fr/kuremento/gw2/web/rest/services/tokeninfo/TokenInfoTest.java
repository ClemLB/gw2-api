package fr.kuremento.gw2.web.rest.services.tokeninfo;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.Constants;
import fr.kuremento.gw2.web.rest.models.tokeninfo.TokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenInfoTest {

	@Autowired
	private TokenInfoService service;

	@Value("${test.api-key.account}")
	private String apiKey;

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
	@DisplayName("Check authentification ok")
	void test2() {
		AtomicReference<TokenInfo> tokenInfo = new AtomicReference<>();
		assertDoesNotThrow(() -> tokenInfo.set(service.getWithAuthentification(apiKey)));
		assertNotNull(tokenInfo.get(), "Service should return informations on token");
	}
}
