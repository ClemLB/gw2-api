package fr.kuremento.gw2;

import fr.kuremento.gw2.client.Gw2Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.image.BufferedImage;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        try (ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)) {
            execute(context.getBean(Gw2Client.class));
        }
    }

    private static void execute(Gw2Client gw2Client) {
        var image = gw2Client.builds().generateBuildImage("[&DQcBHRgdQjsjDwAAZQEAAIMBAAC2AQAA5RoAAAAAAAAAAAAAAAAAAAAAAAA=]");
        showImage(image);
    }

    private static void showImage(BufferedImage image) {
        var frame = new JFrame("Build Preview");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
