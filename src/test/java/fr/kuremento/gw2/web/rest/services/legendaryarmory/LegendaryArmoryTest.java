package fr.kuremento.gw2.web.rest.services.legendaryarmory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class LegendaryArmoryTest {

	@Autowired
	private LegendaryArmoryService service;

	@Test
	@DisplayName("Check number of items")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of items id");
	}

}
