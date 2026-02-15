package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.models.builds.ResolvedBuild;
import fr.kuremento.gw2.models.builds.ResolvedSpecLine;
import fr.kuremento.gw2.web.rest.models.skills.Skill;
import fr.kuremento.gw2.web.rest.models.traits.Trait;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildImageGeneratorService {

	private static final int IMAGE_WIDTH = 500;

	private static final int MAJOR_TRAIT_SIZE = 26;
	private static final int MINOR_TRAIT_SIZE = 26;
	private static final int SPEC_ICON_SIZE = 88;
	private static final int SKILL_ICON_SIZE = 46;

	private static final int VERTICAL_PADDING = 7;
	private static final int TRAIT_VERTICAL_GAP = 4;
	private static final int SPEC_ROW_HEIGHT = 3 * MAJOR_TRAIT_SIZE + 2 * TRAIT_VERTICAL_GAP + 2 * VERTICAL_PADDING;
	private static final int SPEC_ROW_GAP = 7;
	private static final int SKILL_ROW_HEIGHT = SKILL_ICON_SIZE + 20;

	private static final int TRAIT_GAP = 30;
	private static final int SPEC_ICON_OFFSET_X = 59;

	private static final Color DARK_BACKGROUND = new Color(30, 30, 30);

	private final IconFetcherService iconFetcherService;

	public BufferedImage generate(ResolvedBuild build) {
		int specCount = build.specLines().size();
		int totalHeight = specCount * SPEC_ROW_HEIGHT + (specCount - 1) * SPEC_ROW_GAP + SKILL_ROW_HEIGHT;

		BufferedImage image = new BufferedImage(IMAGE_WIDTH, totalHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g2d.setColor(DARK_BACKGROUND);
		g2d.fillRect(0, 0, IMAGE_WIDTH, totalHeight);

		drawSkillBar(g2d, build.skills(), 0);

		int specOffsetY = SKILL_ROW_HEIGHT;
		for (int i = 0; i < specCount; i++) {
			drawSpecLine(g2d, build.specLines().get(i), specOffsetY + i * (SPEC_ROW_HEIGHT + SPEC_ROW_GAP));
		}

		g2d.dispose();
		return image;
	}

	private void drawSpecLine(Graphics2D g2d, ResolvedSpecLine specLine, int y) {
		// Background en scale-to-cover : ratio préservé, remplit toute la zone, crop centré
		BufferedImage bg = loadClasspathImage("data/specializations/backgrounds/" + specLine.specialization().getId() + ".png");
		if (bg != null) {
			drawBackgroundCover(g2d, trimTransparent(bg), y);
		}

		List<Trait> majorTraits = specLine.majorTraits();
		List<Trait> minorTraits = specLine.minorTraits();
		int[] selectedIndices = specLine.selectedMajorIndices();

		// Icône de spécialisation : centrée sur le motif du background, décalée via SPEC_ICON_OFFSET_X
		int specIconX = SPEC_ICON_OFFSET_X;
		int specIconY = y + (SPEC_ROW_HEIGHT - SPEC_ICON_SIZE) / 2;
		BufferedImage specIcon = loadClasspathImage("data/specializations/icons/" + specLine.specialization().getId() + ".png");
		if (specIcon != null) {
			g2d.drawImage(specIcon, specIconX, specIconY, SPEC_ICON_SIZE, SPEC_ICON_SIZE, null);
		}

		// Zone des traits centrée dans l'espace restant à droite de l'icône
		int traitsAreaWidth = 6 * MINOR_TRAIT_SIZE + 5 * TRAIT_GAP;
		int traitsZoneLeft = specIconX + SPEC_ICON_SIZE;
		int traitsZoneWidth = IMAGE_WIDTH - traitsZoneLeft;
		int traitsStartX = traitsZoneLeft + (traitsZoneWidth - traitsAreaWidth) / 2;
		int columnTop = y + VERTICAL_PADDING;
		int centerY = y + SPEC_ROW_HEIGHT / 2;

		// Dessiner les lignes de connexion (sous les icônes)
		// Chemin continu : spec icon → minor1 → major1 → minor2 → major2 → minor3 → major3
		// Les lignes se connectent aux sommets de l'hexagone des minors selon la direction
		g2d.setColor(new Color(255, 255, 255, 220));
		g2d.setStroke(new BasicStroke(2f));
		int lineMargin = 4;

		// Paramètres de l'hexagone pointy-top pour les points de connexion
		double hexR = MINOR_TRAIT_SIZE / 2.0;
		double hexSideX = hexR * Math.sqrt(3) / 2.0;
		int hexHalfH = (int) (hexR / 2);

		// Premier tier : pas de ligne depuis l'icône de spé, on commence au minor1
		// Pour les tiers suivants : ligne major précédent → minor courant
		int prevX = 0;
		int prevY = centerY;
		boolean hasPrev = false;

		for (int tier = 0; tier < 3; tier++) {
			int elementIndex = tier * 2;
			int minorX = traitsStartX + elementIndex * (MINOR_TRAIT_SIZE + TRAIT_GAP);
			int majorColX = traitsStartX + (elementIndex + 1) * (MINOR_TRAIT_SIZE + TRAIT_GAP);
			int selectedChoice = tier < selectedIndices.length ? selectedIndices[tier] : 0;
			int selectedRow = selectedChoice - 1;
			int selectedMajorCenterY = columnTop + selectedRow * (MAJOR_TRAIT_SIZE + TRAIT_VERTICAL_GAP) + MAJOR_TRAIT_SIZE / 2;

			int minorCenterX = minorX + MINOR_TRAIT_SIZE / 2;

			// Ligne précédent → entrée hex (sauf pour le premier tier)
			if (hasPrev) {
				int hexInX = (int) (minorCenterX - hexSideX) - lineMargin;
				int hexInY = centerY;
				if (prevY < centerY) hexInY = centerY - hexHalfH;
				else if (prevY > centerY) hexInY = centerY + hexHalfH;

				g2d.drawLine(prevX, prevY, hexInX, hexInY);
			}

			// Point de sortie sur l'hexagone (côté droit) selon direction de départ
			int hexOutX = (int) (minorCenterX + hexSideX) + lineMargin;
			int hexOutY = centerY;
			if (selectedMajorCenterY < centerY) hexOutY = centerY - hexHalfH;
			else if (selectedMajorCenterY > centerY) hexOutY = centerY + hexHalfH;

			// Ligne sortie hex → avant le major sélectionné
			g2d.drawLine(hexOutX, hexOutY, majorColX - lineMargin, selectedMajorCenterY);

			// Prochain point de départ : après le major sélectionné
			prevX = majorColX + MAJOR_TRAIT_SIZE + lineMargin;
			prevY = selectedMajorCenterY;
			hasPrev = true;
		}

		// Dessiner les icônes par-dessus les lignes
		for (int tier = 0; tier < 3; tier++) {
			int elementIndex = tier * 2;
			int minorX = traitsStartX + elementIndex * (MINOR_TRAIT_SIZE + TRAIT_GAP);
			int majorColX = traitsStartX + (elementIndex + 1) * (MINOR_TRAIT_SIZE + TRAIT_GAP);

			// Trait mineur centré verticalement
			int minorY = y + (SPEC_ROW_HEIGHT - MINOR_TRAIT_SIZE) / 2;
			if (tier < minorTraits.size()) {
				BufferedImage minorIcon = iconFetcherService.fetchImage(minorTraits.get(tier).getIcon());
				if (minorIcon != null) {
					g2d.drawImage(applyHexagonMask(minorIcon), minorX, minorY, MINOR_TRAIT_SIZE, MINOR_TRAIT_SIZE, null);
				}
			}

			// 3 traits majeurs en colonne
			int selectedChoice = tier < selectedIndices.length ? selectedIndices[tier] : 0;
			int majorStartIndex = tier * 3;

			for (int row = 0; row < 3; row++) {
				int majorIndex = majorStartIndex + row;
				if (majorIndex < majorTraits.size()) {
					int traitY = columnTop + row * (MAJOR_TRAIT_SIZE + TRAIT_VERTICAL_GAP);
					boolean selected = selectedChoice == (row + 1);
					drawMajorTrait(g2d, majorTraits.get(majorIndex), majorColX, traitY, selected);
				}
			}
		}
	}

	private void drawBackgroundCover(Graphics2D g2d, BufferedImage bg, int y) {
		double scaleX = (double) IMAGE_WIDTH / bg.getWidth();
		double scaleY = (double) SPEC_ROW_HEIGHT / bg.getHeight();
		double scale = Math.max(scaleX, scaleY);

		int drawWidth = (int) Math.ceil(bg.getWidth() * scale);
		int drawHeight = (int) Math.ceil(bg.getHeight() * scale);

		int bgX = (IMAGE_WIDTH - drawWidth) / 2;
		int bgY = y + (SPEC_ROW_HEIGHT - drawHeight) / 2;

		Shape oldClip = g2d.getClip();
		g2d.setClip(0, y, IMAGE_WIDTH, SPEC_ROW_HEIGHT);
		g2d.drawImage(bg, bgX, bgY, drawWidth, drawHeight, null);
		g2d.setColor(new Color(0, 0, 0, 80));
		g2d.fillRect(0, y, IMAGE_WIDTH, SPEC_ROW_HEIGHT);
		g2d.setClip(oldClip);
	}

	private void drawMajorTrait(Graphics2D g2d, Trait trait, int x, int y, boolean selected) {
		BufferedImage icon = iconFetcherService.fetchImage(trait.getIcon());
		if (icon == null) {
			return;
		}

		if (!selected) {
			Composite originalComposite = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
			g2d.drawImage(icon, x, y, MAJOR_TRAIT_SIZE, MAJOR_TRAIT_SIZE, null);
			g2d.setComposite(originalComposite);
		} else {
			g2d.drawImage(icon, x, y, MAJOR_TRAIT_SIZE, MAJOR_TRAIT_SIZE, null);
		}
	}

	private void drawSkillBar(Graphics2D g2d, List<Skill> skills, int y) {
		// Fond dégradé sombre semi-transparent (comme sur build.png)
		GradientPaint gradient = new GradientPaint(
				0, y, new Color(25, 25, 30, 230),
				0, y + SKILL_ROW_HEIGHT, new Color(15, 15, 20, 200));
		g2d.setPaint(gradient);
		g2d.fillRect(0, y, IMAGE_WIDTH, SKILL_ROW_HEIGHT);
		g2d.setPaint(null);

		int gap = 10;
		int totalSkillsWidth = skills.size() * SKILL_ICON_SIZE + (skills.size() - 1) * gap;
		int x = (IMAGE_WIDTH - totalSkillsWidth) / 2;
		int centerY = y + (SKILL_ROW_HEIGHT - SKILL_ICON_SIZE) / 2;

		for (Skill skill : skills) {
			BufferedImage icon = iconFetcherService.fetchImage(skill.getIcon());
			if (icon != null) {
				g2d.drawImage(icon, x, centerY, SKILL_ICON_SIZE, SKILL_ICON_SIZE, null);
			}
			x += SKILL_ICON_SIZE + gap;
		}
	}

	private boolean isVisiblePixel(int rgb) {
		int alpha = (rgb >>> 24) & 0xFF;
		if (alpha <= 10) {
			return false;
		}
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = rgb & 0xFF;
		return (r + g + b) > 30;
	}

	private BufferedImage trimTransparent(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int top = 0;
		int bottom = height - 1;
		int left = 0;
		int right = width - 1;

		topLoop:
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (isVisiblePixel(image.getRGB(x, y))) {
					top = y;
					break topLoop;
				}
			}
		}

		bottomLoop:
		for (int y = height - 1; y >= top; y--) {
			for (int x = 0; x < width; x++) {
				if (isVisiblePixel(image.getRGB(x, y))) {
					bottom = y;
					break bottomLoop;
				}
			}
		}

		leftLoop:
		for (int x = 0; x < width; x++) {
			for (int y = top; y <= bottom; y++) {
				if (isVisiblePixel(image.getRGB(x, y))) {
					left = x;
					break leftLoop;
				}
			}
		}

		rightLoop:
		for (int x = width - 1; x >= left; x--) {
			for (int y = top; y <= bottom; y++) {
				if (isVisiblePixel(image.getRGB(x, y))) {
					right = x;
					break rightLoop;
				}
			}
		}

		int trimmedWidth = right - left + 1;
		int trimmedHeight = bottom - top + 1;

		if (trimmedWidth <= 0 || trimmedHeight <= 0) {
			return image;
		}

		return image.getSubimage(left, top, trimmedWidth, trimmedHeight);
	}

	private BufferedImage applyHexagonMask(BufferedImage image) {
		int size = Math.min(image.getWidth(), image.getHeight());
		BufferedImage result = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = result.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Hexagone pointy-top (pointes en haut et en bas, comme dans l'UI GW2)
		double cx = size / 2.0;
		double cy = size / 2.0;
		double r = size / 2.0;
		int[] xPoints = new int[6];
		int[] yPoints = new int[6];
		for (int i = 0; i < 6; i++) {
			double angle = Math.toRadians(60 * i - 90);
			xPoints[i] = (int) Math.round(cx + r * Math.cos(angle));
			yPoints[i] = (int) Math.round(cy + r * Math.sin(angle));
		}
		Polygon hexagon = new Polygon(xPoints, yPoints, 6);

		g.setClip(hexagon);
		g.drawImage(image, 0, 0, size, size, null);
		g.dispose();
		return result;
	}

	private BufferedImage loadClasspathImage(String path) {
		try {
			ClassPathResource resource = new ClassPathResource(path);
			return ImageIO.read(resource.getInputStream());
		} catch (IOException e) {
			log.warn("Impossible de charger l'image depuis le classpath : {}", path, e);
			return null;
		}
	}
}
