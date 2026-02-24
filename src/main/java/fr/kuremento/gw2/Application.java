package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
            execute(context.getBean(Gw2Client.class));
        }
    }

    private static void execute(Gw2Client gw2Client) {

    }

}
