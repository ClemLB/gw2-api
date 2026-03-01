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
	@DisplayName("Check leap year and non-leap year consistency on March 1st")
	void test3() {
		// March 1st leap year (2024): dayIndex=60, no adjustment
		DailyRaidBounties leapYear = service.getDailyBounties(LocalDate.of(2024, 3, 1));

		// March 1st non-leap year (2025): dayIndex=59, adjusted to 60
		DailyRaidBounties nonLeapYear = service.getDailyBounties(LocalDate.of(2025, 3, 1));

		assertEquals(leapYear.boss1(), nonLeapYear.boss1());
		assertEquals(leapYear.boss2(), nonLeapYear.boss2());
		assertEquals(leapYear.boss3(), nonLeapYear.boss3());
		assertEquals(leapYear.boss4(), nonLeapYear.boss4());
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
	@DisplayName("Check February 28 to March 1 transition in non-leap year skips February 29 equivalent")
	void test6() {
		// Feb 28 non-leap (2025): dayIndex=58, pas d'ajustement (58 < 59)
		DailyRaidBounties feb28 = service.getDailyBounties(LocalDate.of(2025, 2, 28));
		// Mar 1 non-leap (2025): dayIndex=59, ajusté à 60 (le 29 fév. est sauté)
		DailyRaidBounties mar1 = service.getDailyBounties(LocalDate.of(2025, 3, 1));

		// L'index passe de 58 à 60 (saut de 2, le 59 = 29 fév. est absent) → boss différents
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
