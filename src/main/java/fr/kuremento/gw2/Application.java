package fr.kuremento.gw2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
        }
    }

}
