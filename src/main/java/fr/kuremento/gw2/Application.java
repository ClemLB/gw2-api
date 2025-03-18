package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.services.FractalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws TooManyArgumentsException, IOException {
        try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
            execute(context.getBean(Gw2Client.class), context.getBean(FractalsService.class), "");
            System.exit(0);
        }
    }

    @SuppressWarnings("all")
    private static void execute(Gw2Client gw2Client, FractalsService service, String apiKey) throws TooManyArgumentsException, IOException {
        log.info("Dailies : {}", service.dailies());
        log.info("CMs : {}", service.cms());
        log.info("Recs : {}", service.recs());
    }
}
