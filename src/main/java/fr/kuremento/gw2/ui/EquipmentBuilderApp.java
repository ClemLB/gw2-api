package fr.kuremento.gw2.ui;

import fr.kuremento.gw2.config.AutoConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

public class EquipmentBuilderApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(AutoConfig.class)
				.headless(false)
				.run(args);

		EquipmentBuilderFrame frame = context.getBean(EquipmentBuilderFrame.class);

		SwingUtilities.invokeLater(() -> {
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosed(java.awt.event.WindowEvent e) {
					context.close();
				}
			});
			frame.setVisible(true);
		});
	}
}
