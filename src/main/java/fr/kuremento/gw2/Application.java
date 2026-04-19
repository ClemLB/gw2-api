package fr.kuremento.gw2;

import fr.kuremento.gw2.models.fractals.DailyFractal;
import fr.kuremento.gw2.services.FractalsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

    private final FractalsService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        String dailies = service.dailies().stream().map(DailyFractal::name).collect(Collectors.joining(", "));
        log.info("T4 quotidiennes : {}", dailies);
    }

}
