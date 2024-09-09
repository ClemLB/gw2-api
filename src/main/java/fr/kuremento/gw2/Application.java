package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.raids.Raid;
import fr.kuremento.gw2.web.rest.models.raids.Wing;
import fr.kuremento.gw2.web.rest.models.tokeninfo.TokenInfo;
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
			execute(context.getBean(Gw2Client.class), "");
			System.exit(0);
		}
	}

	@SuppressWarnings("all")
	private static void execute(Gw2Client gw2Client, String apiKey) throws TooManyArgumentsException {
		var account = gw2Client.account().getWithAuthentification(apiKey);
		log.info(account.toString());
	}
}
