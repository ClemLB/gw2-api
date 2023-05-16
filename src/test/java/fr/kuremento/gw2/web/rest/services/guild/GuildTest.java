package fr.kuremento.gw2.web.rest.services.guild;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class GuildTest {

	@Autowired
	private GuildService service;

	@Value("${test.api-key.full}")
	private String apiKey;

	@Test
	@DisplayName("Check no authentification")
	void test1() {
		var guild = service.getWithAuthentification("116E0C0E-0035-44A9-BB22-4AE3E23127E5", "");
		assertNotNull(guild, "Une guilde devrait être retournée");
		assertNotNull(guild.getId(), "Un ID de guilde devrait être retourné");
		assertNull(guild.getInfluence(), "L'influence ne devrait pas être remontée sans authentification");
	}

	@Test
	@DisplayName("Check authentification ok")
	void test2() {
		var guild = service.getWithAuthentification("64682C99-4D9A-EB11-81A8-E944283D67C1", apiKey);
		assertNotNull(guild, "Une guilde devrait être retournée");
		assertNotNull(guild.getId(), "Un ID de guilde devrait être retourné");
		assertNotNull(guild.getInfluence(), "L'influence devrait être remontée avec authentification");
	}

	@Test
	@DisplayName("Check get without authentification")
	void test3() {
		var guild = service.get("116E0C0E-0035-44A9-BB22-4AE3E23127E5");
		assertNotNull(guild, "Une guilde devrait être retournée");
		assertNotNull(guild.getId(), "Un ID de guilde devrait être retourné");
		assertNull(guild.getInfluence(), "L'influence ne devrait pas être remontée sans authentification");
	}

	@Test
	@DisplayName("Check upgrades service")
	void test4() {
		assertNotNull(service.upgrades(), "Service should not null");
	}
}
