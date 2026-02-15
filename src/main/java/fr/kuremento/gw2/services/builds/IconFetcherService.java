package fr.kuremento.gw2.services.builds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Service
public class IconFetcherService {

	private final HttpClient httpClient = HttpClient.newHttpClient();

	public BufferedImage fetchImage(String url) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.build();

			HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

			if (response.statusCode() != 200) {
				log.warn("Impossible de télécharger l'image {} : HTTP {}", url, response.statusCode());
				return null;
			}

			return ImageIO.read(new ByteArrayInputStream(response.body()));
		} catch (IOException | InterruptedException e) {
			log.warn("Erreur lors du téléchargement de l'image {} : {}", url, e.getMessage());
			if (e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			return null;
		}
	}
}
