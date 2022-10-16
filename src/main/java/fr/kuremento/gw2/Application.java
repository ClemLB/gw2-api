package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.web.rest.models.colors.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
			execute(context.getBean(Gw2Client.class));
			System.exit(0);
		}
	}

	private static void execute(Gw2Client gw2Client) {
		List<Integer> idsList = gw2Client.colors().get();
		log.info("IDs : {}", idsList);
		Color oneColor = gw2Client.colors().get(idsList.get(0));
		log.info("Première couleur : {}", oneColor);
		List<Color> allColors;
		try {
			allColors = gw2Client.colors().get(idsList);
		} catch (TooManyArgumentsException e) {
			log.error("Trop d'arguments", e);
			allColors = gw2Client.colors().getAll();
		}
		allColors.stream().limit(5).forEach(color -> log.info(color.toString()));

	}

}
