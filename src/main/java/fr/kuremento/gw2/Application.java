package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.builds.AscendedLightArmorStat;
import fr.kuremento.gw2.models.builds.Equipment;
import fr.kuremento.gw2.models.builds.EquipmentPiece;
import fr.kuremento.gw2.models.builds.EquipmentSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws TooManyArgumentsException {
        try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
            execute(context.getBean(Gw2Client.class));
        }
    }

    private static void execute(Gw2Client gw2Client) throws TooManyArgumentsException {
        var equipments = new Equipment(List.of(new EquipmentPiece(EquipmentSlot.HELM, AscendedLightArmorStat.RAMPAGER, 24762),
                                               new EquipmentPiece(EquipmentSlot.SHOULDERS, AscendedLightArmorStat.RAMPAGER, 24762),
                                               new EquipmentPiece(EquipmentSlot.CHEST, AscendedLightArmorStat.SINISTER, 24762),
                                               new EquipmentPiece(EquipmentSlot.GLOVES, AscendedLightArmorStat.RAMPAGER, 24762),
                                               new EquipmentPiece(EquipmentSlot.LEGGINGS, AscendedLightArmorStat.SINISTER, 24762),
                                               new EquipmentPiece(EquipmentSlot.BOOTS, AscendedLightArmorStat.SINISTER, 24762),
                                               new EquipmentPiece(EquipmentSlot.RELIC, 100849),
                                               new EquipmentPiece(EquipmentSlot.BACK, 104857, AscendedLightArmorStat.RAMPAGER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.AMULET, 95380, AscendedLightArmorStat.SINISTER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.ACCESSORY_1, 81908, AscendedLightArmorStat.SINISTER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.ACCESSORY_2, 91048, AscendedLightArmorStat.SINISTER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.RING_1, 91234, AscendedLightArmorStat.RAMPAGER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.RING_2, 107022, AscendedLightArmorStat.RAMPAGER.getStatId()),
                                               new EquipmentPiece(EquipmentSlot.MAIN_HAND_1, 87109, AscendedLightArmorStat.SINISTER.getStatId(), 24612),
                                               new EquipmentPiece(EquipmentSlot.OFF_HAND_1, 81957, AscendedLightArmorStat.SINISTER.getStatId(), 24560),
                                               new EquipmentPiece(EquipmentSlot.OFF_HAND_2, 86098, AscendedLightArmorStat.SINISTER.getStatId(), 24560)));
        save(gw2Client.builds().generateBuildImage("[&DQcBHRgdQjsjDwAAZQEAAIMBAAC2AQAA5RoAAAAAAAAAAAAAAAAAAAAAAAA=]"), "Archétype");
        save(gw2Client.builds().generateEquipmentImage(equipments), "Équipement");
    }

    private static void save(BufferedImage image, String title) {
        var outputDir = new File("output");
        if(!outputDir.exists()) {
            try {
                Files.createDirectories(outputDir.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            ImageIO.write(image, "png", new File(outputDir, title.toLowerCase().replace(" ", "-") + ".png"));
            log.info("Image sauvegardée : {}.png", title.toLowerCase().replace(" ", "-"));
        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde de l'image", e);
        }
    }

}
