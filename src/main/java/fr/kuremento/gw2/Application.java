package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.raids.DailyRaidBounties;
import fr.kuremento.gw2.services.DailyRaidBountiesService;
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
            execute(context.getBean(Gw2Client.class), context.getBean(DailyRaidBountiesService.class), "");
            System.exit(0);
        }
    }

    @SuppressWarnings("all")
    private static void execute(Gw2Client gw2Client, DailyRaidBountiesService service, String apiKey) throws TooManyArgumentsException, IOException {
        DailyRaidBounties dailyBounties = service.getDailyBounties();
        log.debug("Daily bounties: {}", dailyBounties);
    }
}
