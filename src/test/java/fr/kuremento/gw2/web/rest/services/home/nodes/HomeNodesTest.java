package fr.kuremento.gw2.web.rest.services.home.nodes;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HomeNodesTest {

	@Autowired
	private HomeNodesService service;

	@Test
	@DisplayName("Check number of nodes")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of nodes id");
	}

	@Test
	@DisplayName("Check request multiple node")
	void test2() throws TooManyArgumentsException {
		assertFalse(service.get(List.of("wintersday_tree", "aurilium_node")).isEmpty(), "Requested node should not be null");
	}

	@Test
	@DisplayName("Check request one node")
	void test3() {
		assertNotNull(service.get("wintersday_tree"), "Requested node should not be null");
	}

	@Test
	@DisplayName("Check getAll request")
	void test4() {
		assertFalse(service.get().isEmpty(), "Service should return a list of nodes");
	}

}
