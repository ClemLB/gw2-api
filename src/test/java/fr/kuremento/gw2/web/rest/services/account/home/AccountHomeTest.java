package fr.kuremento.gw2.web.rest.services.account.home;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AccountHomeTest {

	@Autowired
	private AccountHomeService service;

	@Test
	@DisplayName("Nodes service context")
	void test1() {
		assertNotNull(service.nodes(), "Service should not null");
	}

	@Test
	@DisplayName("Cats service context")
	void test2() {
		assertNotNull(service.cats(), "Service should not null");
	}

}
