package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.builds.BuildImages;
import fr.kuremento.gw2.models.builds.Equipment;
import fr.kuremento.gw2.models.builds.FullBuildCode;
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
	private final FullBuildCodecService codec;

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

	public BufferedImage generateEquipmentImage(Equipment equipment, String chatCode) throws TooManyArgumentsException {
		log.info("Génération de l'image d'équipement avec archétype ({} pièces)", equipment.pieces().size());
		var decodedBuild = decoder.decode(chatCode);
		int eliteSpecId = decodedBuild.specLines().get(2).specializationId();
		var resolvedEquipment = equipmentResolver.resolve(equipment);
		return imageGenerator.generateEquipmentImage(resolvedEquipment, eliteSpecId);
	}

	public String encodeBuildCode(String chatCode, Equipment equipment) {
		return codec.encode(chatCode, equipment);
	}

	public FullBuildCode decodeBuildCode(String code) {
		return codec.decode(code);
	}

	public BuildImages generateBuildImages(String code) throws TooManyArgumentsException {
		return generateBuildImages(codec.decode(code));
	}

	public BuildImages generateBuildImages(FullBuildCode fullBuildCode) throws TooManyArgumentsException {
		return generateBuildImages(fullBuildCode.chatCode(), fullBuildCode.equipment());
	}

	public BuildImages generateBuildImages(String chatCode, Equipment equipment) throws TooManyArgumentsException {
		log.info("Génération des images de build et d'équipement ({} pièces)", equipment.pieces().size());
		var decodedBuild = decoder.decode(chatCode);
		var resolvedBuild = resolver.resolve(decodedBuild);
		int eliteSpecId = decodedBuild.specLines().get(2).specializationId();
		var resolvedEquipment = equipmentResolver.resolve(equipment);
		return new BuildImages(
				imageGenerator.generate(resolvedBuild),
				imageGenerator.generateEquipmentImage(resolvedEquipment, eliteSpecId),
				codec.encode(chatCode, equipment)
		);
	}
}
