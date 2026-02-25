package fr.kuremento.gw2;

import fr.kuremento.gw2.models.builds.BuildImages;
import fr.kuremento.gw2.services.builds.BuildTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.io.File;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Application implements ApplicationRunner {

    private static final String CODE = "eNrT4WWVsVWWtpLlYWRgMAZiBXEGhggGBgZbMQYMwMTPwHxxN8NCBoZHKoyMzJdhTCbm8zAmM_MFGJOF-RKMycp8DsZkY39WDmKqJzCoJDJwsLdPZYTzuBinlDB-ZGLgZkxKAYnyMPItAtG8jF_sQTQf44pkEM3POHMmiBZgcO5jBAAjqSbm";

    BuildTemplateService buildTemplateService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BuildImages images = buildTemplateService.generateBuildImages(CODE);
        new File("output").mkdirs();
        ImageIO.write(images.archetype(), "PNG", new File("output/archetype.png"));
        ImageIO.write(images.equipment(), "PNG", new File("output/equipment.png"));
    }

}
