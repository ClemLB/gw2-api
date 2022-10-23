package fr.kuremento.gw2.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class Gw2ClientTest {

	@Autowired
	private Gw2Client gw2Client;

	@Test
	@DisplayName("Account service context")
	void test1() {
		assertNotNull(gw2Client.account(), "Service should not null");
	}

	@Test
	@DisplayName("Quaggans service context")
	void test2() {
		assertNotNull(gw2Client.quaggans(), "Service should not null");
	}

	@Test
	@DisplayName("Colors service context")
	void test3() {
		assertNotNull(gw2Client.colors(), "Service should not null");
	}

	@Test
	@DisplayName("Achievements service context")
	void test4() {
		assertNotNull(gw2Client.achievements(), "Service should not null");
	}
}
