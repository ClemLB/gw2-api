package fr.kuremento.gw2.web.rest.services.home.cats;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HomeCatsTest {

	@Autowired
	private HomeCatsService service;

	@Test
	@DisplayName("Check number of cats")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of cats id");
	}

	@Test
	@DisplayName("Check request multiple cats")
	void test2() throws TooManyArgumentsException {
		assertFalse(service.get(List.of(1, 2, 3)).isEmpty(), "Requested cat should not be null");
	}

	@Test
	@DisplayName("Check request one node")
	void test3() {
		assertNotNull(service.get(1), "Requested cat should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test4() {
		assertFalse(service.get().isEmpty(), "Service should return a list of cats");
	}

}
