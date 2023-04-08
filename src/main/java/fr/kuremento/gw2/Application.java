package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
		var account = gw2Client.account().getWithAuthentification(apiKey);
		log.info("Guildes : {} | leader : {}", account.getGuilds(), account.getGuildLeader());
	}

}
