package fr.kuremento.gw2;

import fr.kuremento.gw2.models.builds.BuildImages;
import fr.kuremento.gw2.services.builds.BuildTemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.io.File;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Application implements ApplicationRunner {

    private static final String CODE = "eNoz4WUT1JSSNrA_LnRc6LDQUqEJDIeFyoRKhGYCIQMaYA5jSGcwY2Bg4mdgXunDGMzKwJXIwMicCGMyMWfBmMzMPDAmC_M-byiTldnVF8pkY2aIAjHDlBjZmdvDQMwVMoxcjFNKQExuxqQUEM3DyLcIRPMyfrFnLGFi4GNckQyi-RlnzgTRAgwm7YwA-IMe7Q";

    BuildTemplateService buildTemplateService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BuildImages images = buildTemplateService.generateBuildImages(CODE);
        File output = new File("output");
        if (!output.exists()) {
            log.debug("Création du répertoire '{}' : {}", output.getName(), output.mkdirs());
        }
        ImageIO.write(images.archetype(), "PNG", new File("output/archetype.png"));
        ImageIO.write(images.equipment(), "PNG", new File("output/equipment.png"));
    }

}
