package fr.kuremento.gw2.web.rest.services.itemstats;

import fr.kuremento.gw2.web.rest.models.itemstats.ItemStat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemStatsTest {

	@Autowired
	private ItemStatsService service;

	@Test
	@DisplayName("Check request one itemstat")
	void test1() {
		ItemStat stat = service.get(161);
		assertNotNull(stat, "Requested itemstat should not be null");
		assertNotNull(stat.getName(), "Itemstat name should not be null");
		assertFalse(stat.getAttributes().isEmpty(), "Itemstat should have attributes");
	}

}
