package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.raids.Raid;
import fr.kuremento.gw2.web.rest.models.raids.Wing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws TooManyArgumentsException {
		try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
			execute(context.getBean(Gw2Client.class), "63D4E7FB-F855-5F47-83B5-5B33DC019AB1DBDF0126-24D2-4895-9B6E-D4B950CDF0D4");
			System.exit(0);
		}
	}

	@SuppressWarnings("all")
	private static void execute(Gw2Client gw2Client, String apiKey) throws TooManyArgumentsException {
		var raids = gw2Client.account().raids().getWithAuthentification(apiKey);
		List<Raid> bosses = gw2Client.raids().getAll();
		raids.forEach(boss -> log.info("{}",
									   bosses.stream()
											 .map(Raid::getWings)
											 .flatMap(Collection::stream)
											 .map(Wing::getEvents)
											 .flatMap(Collection::stream)
											 .filter(raid -> raid.getId().equals(boss))
											 .findAny()
											 .orElse(null)));
	}
}
