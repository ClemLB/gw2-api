package fr.kuremento.gw2.services;

import fr.kuremento.gw2.models.raids.DailyRaidBounties;
import fr.kuremento.gw2.models.raids.RaidBountySlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service permettant de déterminer les primes de raid (raid bounties) quotidiennes dans Guild Wars 2.
 * <p>
 * Chaque jour, quatre boss de raid sont désignés comme primes selon un système de rotation cyclique
 * défini par les {@link RaidBountySlot}. Le calcul se base sur le jour de l'année et la longueur
 * du cycle de chaque slot pour déterminer les boss du jour.
 * <p>
 * Les années non bissextiles font l'objet d'un ajustement afin de rester synchronisé avec le cycle
 * de référence basé sur les années bissextiles (366 jours).
 */
@Slf4j
@Service
public class DailyRaidBountiesService {

	/**
	 * Retourne les primes de raid du jour courant.
	 *
	 * @return les quatre boss de raid du jour
	 */
	public DailyRaidBounties getDailyBounties() {
		return getDailyBounties(LocalDate.now());
	}

	/**
	 * Retourne les primes de raid pour une date donnée.
	 * <p>
	 * Le calcul utilise le jour de l'année (indexé à 0) pour déterminer la position dans le cycle
	 * de rotation de chaque slot. Pour les années non bissextiles, un décalage est appliqué à partir
	 * du 1er mars (jour 59) afin de maintenir l'alignement avec le cycle de référence.
	 *
	 * @param date la date pour laquelle calculer les primes
	 * @return les quatre boss de raid correspondant à la date
	 */
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
