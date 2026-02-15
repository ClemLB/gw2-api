package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.models.builds.DecodedBuild;
import fr.kuremento.gw2.models.builds.ResolvedBuild;
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
	private final BuildImageGeneratorService imageGenerator;

	public BufferedImage generateBuildImage(String chatCode) {
		log.info("Génération de l'image pour le chat code : {}", chatCode);
		DecodedBuild decoded = decoder.decode(chatCode);
		log.debug("Build décodé : profession={}, specs={}, skills={}", decoded.profession(), decoded.specLines().size(), decoded.terrestrialSkillPaletteIds().size());
		ResolvedBuild resolved = resolver.resolve(decoded);
		log.debug("Build résolu : {} spécialisations, {} skills", resolved.specLines().size(), resolved.skills().size());
		return imageGenerator.generate(resolved);
	}
}
