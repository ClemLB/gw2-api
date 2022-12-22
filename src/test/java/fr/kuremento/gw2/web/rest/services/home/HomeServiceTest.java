package fr.kuremento.gw2.web.rest.services.home;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HomeServiceTest {

	@Autowired
	private HomeService service;

	@Test
	@DisplayName("Nodes service context")
	void test1() {
		assertNotNull(service.nodes(), "Service should not null");
	}
}
