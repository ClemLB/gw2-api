package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.achievements.Achievements;
import fr.kuremento.gw2.web.rest.models.achievements.AchievementsReward;
import fr.kuremento.gw2.web.rest.models.achievements.RewardType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
			execute(context.getBean(Gw2Client.class), "63D4E7FB-F855-5F47-83B5-5B33DC019AB1DBDF0126-24D2-4895-9B6E-D4B950CDF0D4");
			System.exit(0);
		}
	}

	private static void execute(Gw2Client gw2Client, String apiKey) {
		var achievements = gw2Client.achievements().get();
		log.info("Taille total : {}", achievements.size());
		var collectedList = achievements.stream().collect(Collectors.groupingBy(index -> index / 200)).values().stream().map(ArrayList::new).toList();
		collectedList.parallelStream().forEach(list -> {
			try {
				List<Achievements> achievementsList = gw2Client.achievements().get(list);
				var masteryAchievements = achievementsList.stream().filter(success -> success.getRewards().stream().allMatch(reward -> RewardType.MASTERY.equals(reward.getType()))).toList();
				var masteryRegions = masteryAchievements.stream().flatMap(success -> success.getRewards().stream()).map(AchievementsReward::getRegion).toList();
				masteryRegions.forEach(region -> log.info("Région de maîtrise : {}", region));
			} catch (TooManyArgumentsException e) {
				log.error("Trop de paramètres");
			}
		});
	}

}
