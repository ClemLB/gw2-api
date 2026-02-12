package fr.kuremento.gw2.services;

import fr.kuremento.gw2.models.raids.DailyRaidBounties;
import fr.kuremento.gw2.models.raids.RaidBountySlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class DailyRaidBountiesService {

	public DailyRaidBounties getDailyBounties() {
		return getDailyBounties(LocalDate.now());
	}

	public DailyRaidBounties getDailyBounties(LocalDate date) {
		int dayOfYear = date.getDayOfYear() - 1;

		if (!date.isLeapYear() && dayOfYear >= 59) {
			dayOfYear++;
		}

		String boss1 = RaidBountySlot.BOSS_1.getEncounters().get(dayOfYear % RaidBountySlot.BOSS_1.getCycleLength());
		String boss2 = RaidBountySlot.BOSS_2.getEncounters().get(dayOfYear % RaidBountySlot.BOSS_2.getCycleLength());
		String boss3 = RaidBountySlot.BOSS_3.getEncounters().get(dayOfYear % RaidBountySlot.BOSS_3.getCycleLength());
		String boss4 = RaidBountySlot.BOSS_4.getEncounters().get(dayOfYear % RaidBountySlot.BOSS_4.getCycleLength());

		return new DailyRaidBounties(boss1, boss2, boss3, boss4);
	}

}
