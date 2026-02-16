package fr.kuremento.gw2.services.builds;

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
        var decodedBuild = decoder.decode(chatCode);
        var resolvedBuild = resolver.resolve(decodedBuild);
        return imageGenerator.generate(resolvedBuild);
    }
}
