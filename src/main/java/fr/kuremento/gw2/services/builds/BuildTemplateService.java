package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.builds.Equipment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildTemplateService {

	private final BuildChatCodeDecoder decoder;
	private final BuildResolverService resolver;
	private final EquipmentResolverService equipmentResolver;
	private final BuildImageGeneratorService imageGenerator;

	public BufferedImage generateBuildImage(String chatCode) {
		log.info("Génération de l'image pour le chat code : {}", chatCode);
		var decodedBuild = decoder.decode(chatCode);
		var resolvedBuild = resolver.resolve(decodedBuild);
		return imageGenerator.generate(resolvedBuild);
	}

	public BufferedImage generateEquipmentImage(Equipment equipment) throws TooManyArgumentsException {
		log.info("Génération de l'image d'équipement ({} pièces)", equipment.pieces().size());
		var resolvedEquipment = equipmentResolver.resolve(equipment);
		return imageGenerator.generateEquipmentImage(resolvedEquipment);
	}
}
