package fr.kuremento.gw2.services;

import fr.kuremento.gw2.models.raids.DailyRaidBounties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DailyRaidBountiesServiceTest {

	@Autowired
	private DailyRaidBountiesService service;

	@Test
	@DisplayName("Check daily bounties are not null and contain 4 bosses")
	void test1() {
		DailyRaidBounties bounties = service.getDailyBounties();

		assertNotNull(bounties);
		assertNotNull(bounties.boss1());
		assertNotNull(bounties.boss2());
		assertNotNull(bounties.boss3());
		assertNotNull(bounties.boss4());
	}

	@Test
	@DisplayName("Check known date January 1st returns index 0 encounters")
	void test2() {
		// January 1st: dayOfYear=0, non-leap year adjustment doesn't apply (0 < 59)
		DailyRaidBounties bounties = service.getDailyBounties(LocalDate.of(2025, 1, 1));

		assertEquals("Col des Cimefroides", bounties.boss1());
		assertEquals("Cachette des Étherlames", bounties.boss2());
		assertEquals("Paressor", bounties.boss3());
		assertEquals("Qadim", bounties.boss4());
	}

	@Test
	@DisplayName("Check cycle repeats every 12 calendar days across February/March boundary in non-leap year")
	void test3() {
		// Feb 17 2026 (non-leap): dayIndex 47, 47%12=11 → Kela
		DailyRaidBounties feb17 = service.getDailyBounties(LocalDate.of(2026, 2, 17));
		assertEquals("Kela, sénéchal des vagues", feb17.boss2());

		// March 1 2026 (non-leap): dayIndex 59, 59%12=11 → Kela (12 calendar days later)
		DailyRaidBounties mar1 = service.getDailyBounties(LocalDate.of(2026, 3, 1));
		assertEquals("Kela, sénéchal des vagues", mar1.boss2());
	}

	@Test
	@DisplayName("Check cycle repeats correctly for 12-day cycle bosses")
	void test4() {
		LocalDate baseDate = LocalDate.of(2024, 1, 1);
		DailyRaidBounties day0 = service.getDailyBounties(baseDate);
		DailyRaidBounties day12 = service.getDailyBounties(baseDate.plusDays(12));

		assertEquals(day0.boss2(), day12.boss2());
		assertEquals(day0.boss3(), day12.boss3());
	}

	@Test
	@DisplayName("Check cycle repeats correctly for 6-day cycle bosses")
	void test5() {
		LocalDate baseDate = LocalDate.of(2024, 1, 1);
		DailyRaidBounties day0 = service.getDailyBounties(baseDate);
		DailyRaidBounties day6 = service.getDailyBounties(baseDate.plusDays(6));

		assertEquals(day0.boss1(), day6.boss1());
		assertEquals(day0.boss4(), day6.boss4());
	}

	@Test
	@DisplayName("Check February 28 to March 1 transition in non-leap year advances by one step")
	void test6() {
		// Feb 28 non-leap (2025): dayIndex 58
		DailyRaidBounties feb28 = service.getDailyBounties(LocalDate.of(2025, 2, 28));
		// Mar 1 non-leap (2025): dayIndex 59 (pas de saut, avance d'un cran)
		DailyRaidBounties mar1 = service.getDailyBounties(LocalDate.of(2025, 3, 1));

		// Les boss doivent être différents (indices consécutifs)
		assertNotEquals(feb28.boss2(), mar1.boss2());
		assertNotEquals(feb28.boss3(), mar1.boss3());
	}

	@Test
	@DisplayName("Check February 28 to March 1 transition in leap year advances by one step per day")
	void test7() {
		// En année bissextile, Feb 28 → Feb 29 → Mar 1 avancent chacun d'un cran
		DailyRaidBounties feb28 = service.getDailyBounties(LocalDate.of(2024, 2, 28));
		DailyRaidBounties feb29 = service.getDailyBounties(LocalDate.of(2024, 2, 29));
		DailyRaidBounties mar1 = service.getDailyBounties(LocalDate.of(2024, 3, 1));

		// Trois jours consécutifs → trois boss différents pour les slots à cycle 12
		assertNotEquals(feb28.boss2(), feb29.boss2());
		assertNotEquals(feb29.boss2(), mar1.boss2());
		assertNotEquals(feb28.boss2(), mar1.boss2());

		// Le cycle de 12 jours fonctionne aussi à travers cette transition
		DailyRaidBounties mar11 = service.getDailyBounties(LocalDate.of(2024, 3, 11));
		assertEquals(feb28.boss2(), mar11.boss2());
		assertEquals(feb28.boss3(), mar11.boss3());
	}

}
