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
	@DisplayName("Check leap year and non-leap year consistency after February 28")
	void test3() {
		// March 1st leap year (2024): dayOfYear = 60 (0-based), no adjustment
		DailyRaidBounties leapYear = service.getDailyBounties(LocalDate.of(2024, 3, 1));

		// March 1st non-leap year (2025): dayOfYear = 59 (0-based), adjusted to 60
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
	@DisplayName("Check February 28 to March 1 transition in non-leap year")
	void test6() {
		// Feb 28 non-leap (2025): dayOfYear = 58 (0-based), < 59, no adjustment → index 58
		// Mar 1 non-leap (2025): dayOfYear = 59 (0-based), >= 59, adjusted to 60
		// This means index 59 (Feb 29 equivalent) is skipped in non-leap years
		DailyRaidBounties feb28 = service.getDailyBounties(LocalDate.of(2025, 2, 28));
		DailyRaidBounties mar1 = service.getDailyBounties(LocalDate.of(2025, 3, 1));

		// Feb 28 leap (2024): dayOfYear = 58 (0-based)
		DailyRaidBounties feb28Leap = service.getDailyBounties(LocalDate.of(2024, 2, 28));

		assertEquals(feb28Leap.boss1(), feb28.boss1());
		assertEquals(feb28Leap.boss2(), feb28.boss2());
		assertEquals(feb28Leap.boss3(), feb28.boss3());
		assertEquals(feb28Leap.boss4(), feb28.boss4());

		// Mar 1 leap (2024): dayOfYear = 60 (0-based)
		DailyRaidBounties mar1Leap = service.getDailyBounties(LocalDate.of(2024, 3, 1));

		assertEquals(mar1Leap.boss1(), mar1.boss1());
		assertEquals(mar1Leap.boss2(), mar1.boss2());
		assertEquals(mar1Leap.boss3(), mar1.boss3());
		assertEquals(mar1Leap.boss4(), mar1.boss4());
	}

}
