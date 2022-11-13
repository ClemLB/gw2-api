package fr.kuremento.gw2.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Gw2ClientTest {

	public static final String SERVICE_SHOULD_NOT_BE_NULL = "Service should not be null";
	@Autowired
	private Gw2Client gw2Client;

	@Test
	@DisplayName("Account service context")
	void test1() {
		assertNotNull(gw2Client.account(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Quaggans service context")
	void test2() {
		assertNotNull(gw2Client.quaggans(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Colors service context")
	void test3() {
		assertNotNull(gw2Client.colors(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Achievements service context")
	void test4() {
		assertNotNull(gw2Client.achievements(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Minis service context")
	void test5() {
		assertNotNull(gw2Client.minis(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Raids service context")
	void test6() {
		assertNotNull(gw2Client.raids(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Items service context")
	void test7() {
		assertNotNull(gw2Client.items(), SERVICE_SHOULD_NOT_BE_NULL);
	}

	@Test
	@DisplayName("Traits service context")
	void test8() {
		assertNotNull(gw2Client.traits(), SERVICE_SHOULD_NOT_BE_NULL);
	}
}
