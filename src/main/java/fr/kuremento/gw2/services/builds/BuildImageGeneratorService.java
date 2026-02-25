package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.models.builds.*;
import fr.kuremento.gw2.web.rest.models.itemstats.ItemStat;
import fr.kuremento.gw2.web.rest.models.itemstats.ItemStatAttribute;
import fr.kuremento.gw2.web.rest.models.items.Item;
import fr.kuremento.gw2.web.rest.models.items.enums.TypeOfItem;
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
import java.util.*;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildImageGeneratorService {

	private static final int SCALE = 2;

	private static final int BUILD_WIDTH = 500;

	private static final int MAJOR_TRAIT_SIZE = 26;
	private static final int MINOR_TRAIT_SIZE = 26;
	private static final int SPEC_ICON_SIZE = 88;
	private static final int SKILL_ICON_SIZE = 46;

	private static final int EQUIP_ICON_SIZE = 38;
	private static final int UPGRADE_OVERLAY_SIZE = 14;
	private static final int EQUIP_ROW_GAP = 8;
	private static final int EQUIP_PADDING = 8;
	private static final int SECTION_HEADER_HEIGHT = 14;
	private static final int SECTION_CONTENT_GAP = 8;
	private static final int SIGIL_SLOT_SIZE = 18;
	private static final int EQUIP_ATTR_LINE_HEIGHT = 10;

	// Partie fixe (sans texte) de chaque section
	private static final int ITEM_FIXED = EQUIP_PADDING + EQUIP_ICON_SIZE + 6 + EQUIP_PADDING; // 60
	private static final int MAX_TEXT_WIDTH = 100;
	private static final int CELL_GAP = 4;

	private static final Map<String, String> WEAPON_TYPE_FR = Map.ofEntries(
			Map.entry("Axe", "Hache"),
			Map.entry("Dagger", "Dague"),
			Map.entry("Focus", "Focus"),
			Map.entry("Greatsword", "Espadon"),
			Map.entry("Hammer", "Marteau"),
			Map.entry("LongBow", "Arc long"),
			Map.entry("Mace", "Masse"),
			Map.entry("Pistol", "Pistolet"),
			Map.entry("Rifle", "Fusil"),
			Map.entry("Scepter", "Sceptre"),
			Map.entry("Shield", "Bouclier"),
			Map.entry("ShortBow", "Arc court"),
			Map.entry("Staff", "Bâton"),
			Map.entry("Sword", "Épée"),
			Map.entry("Torch", "Torche"),
			Map.entry("Trident", "Trident"),
			Map.entry("Warhorn", "Cor de guerre"),
			Map.entry("Harpoon", "Lance"),
			Map.entry("Spear", "Lance"),
			Map.entry("HarpoonGun", "Harpon")
	);

	private static final Map<EquipmentSlot, String> SLOT_FR = Map.ofEntries(
			Map.entry(EquipmentSlot.HELM, "Casque"),
			Map.entry(EquipmentSlot.SHOULDERS, "Épaules"),
			Map.entry(EquipmentSlot.CHEST, "Plastron"),
			Map.entry(EquipmentSlot.GLOVES, "Gantelets"),
			Map.entry(EquipmentSlot.LEGGINGS, "Jambières"),
			Map.entry(EquipmentSlot.BOOTS, "Bottes"),
			Map.entry(EquipmentSlot.AMULET, "Amulette"),
			Map.entry(EquipmentSlot.RING_1, "Anneau"),
			Map.entry(EquipmentSlot.RING_2, "Anneau"),
			Map.entry(EquipmentSlot.ACCESSORY_1, "Accessoire"),
			Map.entry(EquipmentSlot.ACCESSORY_2, "Accessoire"),
			Map.entry(EquipmentSlot.BACK, "Dos"),
			Map.entry(EquipmentSlot.RELIC, "Relique")
	);

	private static final Map<String, String> ATTR_FR = Map.ofEntries(
			Map.entry("Power", "Puissance"),
			Map.entry("Precision", "Précision"),
			Map.entry("CritDamage", "Férocité"),
			Map.entry("ConditionDamage", "Dégâts par altération"),
			Map.entry("Toughness", "Robustesse"),
			Map.entry("Vitality", "Vitalité"),
			Map.entry("HealingPower", "Guérison"),
			Map.entry("BoonDuration", "Concentration"),
			Map.entry("ConditionDuration", "Expertise"),
			Map.entry("AgonyResistance", "Résistance à l'agonie")
	);

	private static final int VERTICAL_PADDING = 7;
	private static final int TRAIT_VERTICAL_GAP = 4;
	private static final int SPEC_ROW_HEIGHT = 3 * MAJOR_TRAIT_SIZE + 2 * TRAIT_VERTICAL_GAP + 2 * VERTICAL_PADDING;
	private static final int SPEC_ROW_GAP = 7;
	private static final int SKILL_ROW_HEIGHT = SKILL_ICON_SIZE + 20;

	private static final int TRAIT_GAP = 30;
	private static final int SPEC_ICON_OFFSET_X = 59;

	private static final Color DARK_BACKGROUND = new Color(30, 30, 30);
	private static final int BACKGROUND_OPACITE = 100;

	private final IconFetcherService iconFetcherService;

	private static void applyRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}

	public BufferedImage generate(ResolvedBuild build) {
		int specCount = build.specLines().size();
		int specHeight = specCount * SPEC_ROW_HEIGHT + (specCount - 1) * SPEC_ROW_GAP + SKILL_ROW_HEIGHT;

		BufferedImage image = new BufferedImage(BUILD_WIDTH * SCALE, specHeight * SCALE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();

		applyRenderingHints(g2d);
		g2d.scale(SCALE, SCALE);

		g2d.setColor(DARK_BACKGROUND);
		g2d.fillRect(0, 0, BUILD_WIDTH, specHeight);

		drawSkillBar(g2d, build.skills(), 0);

		int specOffsetY = SKILL_ROW_HEIGHT;
		for (int i = 0; i < specCount; i++) {
			drawSpecLine(g2d, build.specLines().get(i), specOffsetY + i * (SPEC_ROW_HEIGHT + SPEC_ROW_GAP));
		}

		g2d.dispose();
		return image;
	}

	public BufferedImage generateEquipmentImage(ResolvedEquipment equipment) {
		return doGenerateEquipmentImage(equipment, null);
	}

	public BufferedImage generateEquipmentImage(ResolvedEquipment equipment, int eliteSpecId) {
		return doGenerateEquipmentImage(equipment, eliteSpecId);
	}

	private BufferedImage doGenerateEquipmentImage(ResolvedEquipment equipment, Integer eliteSpecId) {
		int leftW = ITEM_FIXED + measureMaxTextWidth(equipment,
				EquipmentSlot.HELM, EquipmentSlot.SHOULDERS, EquipmentSlot.CHEST,
				EquipmentSlot.GLOVES, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS);
		// Accessoires : grille 3×2, chaque cellule = icône + texte
		int accessTextW = measureMaxTextWidth(equipment, EquipmentSlot.BACK, EquipmentSlot.ACCESSORY_1,
				EquipmentSlot.ACCESSORY_2, EquipmentSlot.AMULET, EquipmentSlot.RING_1, EquipmentSlot.RING_2);
		int accessCellW = EQUIP_ICON_SIZE + 6 + accessTextW;
		int accessGridW = 2 * EQUIP_PADDING + 3 * accessCellW + 2 * CELL_GAP;
		// Armes : 2 sets côte à côte, chaque set = ITEM_FIXED + max text width du set
		int armeSetTextW = Math.max(
				measureMaxTextWidth(equipment, EquipmentSlot.MAIN_HAND_1, EquipmentSlot.OFF_HAND_1),
				measureMaxTextWidth(equipment, EquipmentSlot.MAIN_HAND_2, EquipmentSlot.OFF_HAND_2));
		int armeGridW = 2 * (ITEM_FIXED + armeSetTextW) + CELL_GAP;
		int rightW = Math.max(accessGridW, armeGridW);

		// Largeur minimale pour que les en-têtes de section tiennent
		BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gTmp = tmp.createGraphics();
		FontMetrics fmH = gTmp.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 9));
		gTmp.dispose();
		leftW = Math.max(leftW, fmH.stringWidth("ARMURE") + 2 * EQUIP_PADDING);
		rightW = Math.max(rightW, fmH.stringWidth("ACCESSOIRES") + 2 * EQUIP_PADDING);
		rightW = Math.max(rightW, fmH.stringWidth("ARMES") + 2 * EQUIP_PADDING);

		int totalWidth = leftW + 1 + rightW;
		int bodyHeight = computeEquipmentHeight(equipment, rightW);
		int totalHeight = bodyHeight;

		BufferedImage image = new BufferedImage(totalWidth * SCALE, totalHeight * SCALE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();

		applyRenderingHints(g2d);
		g2d.scale(SCALE, SCALE);

		// Fond : image de spécialisation d'élite si fournie, sinon fond sombre uni
		if (eliteSpecId != null) {
			BufferedImage bg = loadClasspathImage("data/specializations/backgrounds/" + eliteSpecId + ".png");
			if (bg != null) {
				bg = trimTransparent(bg);
				double scaleX = (double) totalWidth / bg.getWidth();
				double scaleY = (double) totalHeight / bg.getHeight();
				double s = Math.max(scaleX, scaleY);
				int drawW = (int) Math.ceil(bg.getWidth() * s);
				int drawH = (int) Math.ceil(bg.getHeight() * s);
				g2d.drawImage(bg, 0, (totalHeight - drawH) / 2, drawW, drawH, null);
				g2d.setColor(new Color(0, 0, 0, BACKGROUND_OPACITE));
				g2d.fillRect(0, 0, totalWidth, totalHeight);
			}
		} else {
			g2d.setColor(DARK_BACKGROUND);
			g2d.fillRect(0, 0, totalWidth, totalHeight);
		}

		drawEquipmentColumn(g2d, equipment, 0, 0, bodyHeight, leftW, rightW);

		g2d.dispose();
		return image;
	}

	private int measureMaxTextWidth(ResolvedEquipment equipment, EquipmentSlot... slots) {
		BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		FontMetrics fmPlain = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		FontMetrics fmBold = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 8));
		g.dispose();

		int max = 0;
		Map<EquipmentSlot, ResolvedEquipmentPiece> bySlot = equipment.bySlot();
		for (EquipmentSlot slot : slots) {
			ResolvedEquipmentPiece piece = bySlot.get(slot);
			if (piece == null) continue;
			String label = buildItemLabel(piece);
			String statName = piece.stat() != null ? capitalize(piece.stat().getName()) : null;
			if (label != null && statName != null) {
				int labelSepW = fmPlain.stringWidth(label + " · ");
				int statW = fmBold.stringWidth(statName);
				if (labelSepW + statW <= MAX_TEXT_WIDTH) {
					max = Math.max(max, labelSepW + statW);
				} else {
					max = Math.max(max, fmPlain.stringWidth(label));
					max = Math.max(max, statW);
				}
			} else if (label != null) {
				max = Math.max(max, fmPlain.stringWidth(label));
			} else if (statName != null) {
				max = Math.max(max, fmBold.stringWidth(statName));
			}
			// Upgrade (sigil/rune) : icône + nom
			if (piece.upgrade() != null && piece.upgrade().getName() != null)
				max = Math.max(max, SIGIL_SLOT_SIZE + 4 + fmPlain.stringWidth(piece.upgrade().getName()));
			if (piece.upgrade2() != null && piece.upgrade2().getName() != null)
				max = Math.max(max, SIGIL_SLOT_SIZE + 4 + fmPlain.stringWidth(piece.upgrade2().getName()));
		}
		return max;
	}

	private void drawEquipmentColumn(Graphics2D g2d, ResolvedEquipment equipment, int x, int y, int height,
			int leftW, int rightW) {
		Map<EquipmentSlot, ResolvedEquipmentPiece> bySlot = equipment.bySlot();

		g2d.setColor(new Color(20, 20, 25, 220));
		g2d.fillRect(x, y, leftW + 1 + rightW, height);

		int rightX = x + leftW + 1;

		// Séparateur vertical entre les deux colonnes
		g2d.setColor(new Color(60, 60, 60));
		g2d.drawLine(rightX - 1, y, rightX - 1, y + height);

		// --- Colonne gauche : ARMURE ---
		int armureY = drawSectionHeader(g2d, "ARMURE", x + EQUIP_PADDING, y + EQUIP_PADDING, leftW - 2 * EQUIP_PADDING);
		for (EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HELM, EquipmentSlot.SHOULDERS, EquipmentSlot.CHEST,
				EquipmentSlot.GLOVES, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS}) {
			armureY = drawArmureRow(g2d, bySlot.get(slot), x, armureY);
		}

		// --- Colonne droite : ACCESSOIRES / ARMES / (RELIQUE | ATTRIBUTS) ---
		int rightY = y + EQUIP_PADDING;

		// ACCESSOIRES — grille 3×2 avec icône + texte par cellule
		rightY = drawSectionHeader(g2d, "ACCESSOIRES", rightX + EQUIP_PADDING, rightY, rightW - 2 * EQUIP_PADDING);
		int cellW = (rightW - 2 * EQUIP_PADDING - 2 * CELL_GAP) / 3;
		EquipmentSlot[][] accessGrid = {
				{EquipmentSlot.BACK, EquipmentSlot.ACCESSORY_1, EquipmentSlot.ACCESSORY_2},
				{EquipmentSlot.AMULET, EquipmentSlot.RING_1, EquipmentSlot.RING_2}
		};
		for (int row = 0; row < accessGrid.length; row++) {
			for (int col = 0; col < accessGrid[row].length; col++) {
				int cellX = rightX + EQUIP_PADDING + col * (cellW + CELL_GAP);
				int cellY = rightY + row * (EQUIP_ICON_SIZE + CELL_GAP);
				ResolvedEquipmentPiece piece = bySlot.get(accessGrid[row][col]);
				drawItemIconPlain(g2d, piece, cellX, cellY, EQUIP_ICON_SIZE);
				drawItemHeader(g2d, piece, cellX + EQUIP_ICON_SIZE + 6, cellY);
			}
		}
		rightY += 2 * EQUIP_ICON_SIZE + CELL_GAP + EQUIP_ROW_GAP;

		// ARMES — set 1 (gauche) et set 2 (droite) côte à côte
		rightY = drawSectionHeader(g2d, "ARMES", rightX + EQUIP_PADDING, rightY, rightW - 2 * EQUIP_PADDING);
		int setW = (rightW - CELL_GAP) / 2;
		int set2X = rightX + setW + CELL_GAP;
		// Séparateur vertical entre les deux sets
		g2d.setColor(new Color(60, 60, 60));
		g2d.drawLine(rightX + setW + CELL_GAP / 2, rightY, rightX + setW + CELL_GAP / 2, rightY + rowHeight(bySlot.get(EquipmentSlot.MAIN_HAND_1)) + EQUIP_ROW_GAP + rowHeight(bySlot.get(EquipmentSlot.OFF_HAND_1)));
		int set1Y = rightY;
		int set2Y = rightY;
		set1Y = drawWeaponRow(g2d, bySlot.get(EquipmentSlot.MAIN_HAND_1), rightX, set1Y);
		set2Y = drawWeaponRow(g2d, bySlot.get(EquipmentSlot.MAIN_HAND_2), set2X, set2Y);
		set1Y = drawWeaponRow(g2d, bySlot.get(EquipmentSlot.OFF_HAND_1), rightX, set1Y);
		set2Y = drawWeaponRow(g2d, bySlot.get(EquipmentSlot.OFF_HAND_2), set2X, set2Y);
		rightY = Math.max(set1Y, set2Y);

		// Section basse : RELIQUE (moitié gauche) | ATTRIBUTS (moitié droite)
		// La séparation est alignée avec celle des sets d'armes
		int separatorX = rightX + setW + CELL_GAP / 2;
		int attrSectionX = separatorX + 1;
		int attrContentW = rightX + rightW - attrSectionX - 2 * EQUIP_PADDING;
		int relicSectionW = separatorX - rightX;
		int headerH = SECTION_HEADER_HEIGHT + SECTION_CONTENT_GAP;

		// Pré-calcul des hauteurs de contenu pour le centrage vertical
		Map<String, ItemStat> statsMap = collectUniqueStats(equipment);
		BufferedImage tmpImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gTmp2 = tmpImg.createGraphics();
		FontMetrics fmTmp = gTmp2.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		gTmp2.dispose();
		int attrLineCount = 0;
		for (ItemStat stat : statsMap.values()) {
			attrLineCount += wrapText(buildStatSummary(stat), fmTmp, attrContentW).size();
		}
		int relicContentH = rowHeight(bySlot.get(EquipmentSlot.RELIC));
		int attrContentH = attrLineCount * EQUIP_ATTR_LINE_HEIGHT;
		int bottomH = Math.max(headerH + relicContentH, statsMap.isEmpty() ? 0 : attrContentH);

		// RELIQUE (moitié gauche) — contenu centré verticalement
		int relicBodyY = drawSectionHeader(g2d, "RELIQUE", rightX + EQUIP_PADDING, rightY, relicSectionW - 2 * EQUIP_PADDING);
		int relicOffsetY = Math.max(0, (bottomH - headerH - relicContentH) / 2);
		drawRelicRow(g2d, bySlot.get(EquipmentSlot.RELIC), rightX, relicBodyY + relicOffsetY);

		// ATTRIBUTS (moitié droite) — encadré autour du texte, contenu italique aligné en bas
		if (!statsMap.isEmpty()) {
			int attrBodyY = y + height - EQUIP_PADDING - attrContentH;
			Font italicFont = new Font(Font.SANS_SERIF, Font.ITALIC, 7);
			Font boldItalicFont = new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, 7);
			FontMetrics fmAttr = g2d.getFontMetrics(italicFont);
			FontMetrics fmBoldAttr = g2d.getFontMetrics(boldItalicFont);
			// Encadré autour du bloc texte
			int boxPad = 3;
			g2d.setColor(new Color(80, 80, 80));
			g2d.drawRoundRect(attrSectionX + EQUIP_PADDING - boxPad, attrBodyY - boxPad,
					attrContentW + 2 * boxPad, attrContentH + 2 * boxPad, 4, 4);
			g2d.setColor(new Color(180, 180, 180));
			for (ItemStat stat : statsMap.values()) {
				for (String line : wrapText(buildStatSummary(stat), fmAttr, attrContentW)) {
					String statPart = capitalize(stat.getName());
					if (line.startsWith(statPart)) {
						String rest = line.substring(statPart.length());
						g2d.setFont(boldItalicFont);
						g2d.drawString(statPart, attrSectionX + EQUIP_PADDING, attrBodyY + fmAttr.getAscent());
						g2d.setFont(italicFont);
						g2d.drawString(rest, attrSectionX + EQUIP_PADDING + fmBoldAttr.stringWidth(statPart), attrBodyY + fmAttr.getAscent());
					} else {
						g2d.setFont(italicFont);
						g2d.drawString(line, attrSectionX + EQUIP_PADDING, attrBodyY + fmAttr.getAscent());
					}
					attrBodyY += EQUIP_ATTR_LINE_HEIGHT;
				}
			}
		}
	}

	private int computeEquipmentHeight(ResolvedEquipment equipment, int rightW) {
		int headerH = SECTION_HEADER_HEIGHT + SECTION_CONTENT_GAP;
		Map<EquipmentSlot, ResolvedEquipmentPiece> bySlot = equipment.bySlot();

		// Colonne gauche : ARMURE (6 pièces)
		int armureContentH = 0;
		for (EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HELM, EquipmentSlot.SHOULDERS, EquipmentSlot.CHEST,
				EquipmentSlot.GLOVES, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS}) {
			armureContentH += rowHeight(bySlot.get(slot)) + EQUIP_ROW_GAP;
		}
		int leftH = EQUIP_PADDING + headerH + armureContentH;

		// Colonne droite : ACCESSOIRES + ARMES + (RELIQUE | ATTRIBUTS) côte à côte
		int accessContentH = 2 * EQUIP_ICON_SIZE + CELL_GAP + EQUIP_ROW_GAP;
		// 2 sets côte à côte : hauteur = un seul set (les deux sont symétriques)
		int armeContentH = rowHeight(bySlot.get(EquipmentSlot.MAIN_HAND_1)) + EQUIP_ROW_GAP
				+ rowHeight(bySlot.get(EquipmentSlot.OFF_HAND_1)) + EQUIP_ROW_GAP;
		// Section basse : max(hauteur RELIQUE, hauteur ATTRIBUTS)
		int attrContentW = rightW - rightW / 2 - 2 * EQUIP_PADDING;
		BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		FontMetrics fm = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		g.dispose();
		int attrLines = 0;
		for (ItemStat stat : collectUniqueStats(equipment).values()) {
			attrLines += wrapText(buildStatSummary(stat), fm, attrContentW).size();
		}
		int attrH = attrLines > 0 ? attrLines * EQUIP_ATTR_LINE_HEIGHT : 0;
		int relicH = headerH + rowHeight(bySlot.get(EquipmentSlot.RELIC)) + EQUIP_ROW_GAP;
		int bottomH = Math.max(relicH, attrH);

		int rightH = EQUIP_PADDING + headerH + accessContentH + headerH + armeContentH + bottomH;

		return Math.max(leftH, rightH) + EQUIP_PADDING;
	}

	private int drawWeaponRow(Graphics2D g2d, ResolvedEquipmentPiece piece, int sectionX, int y) {
		int h = rowHeight(piece);
		int textX = sectionX + EQUIP_PADDING + EQUIP_ICON_SIZE + 6;

		Item sigil1 = piece != null ? piece.upgrade() : null;
		Item sigil2 = piece != null ? piece.upgrade2() : null;
		int sigilCount = (sigil1 != null ? 1 : 0) + (sigil2 != null ? 1 : 0);

		// Pour 2 sigils : l'icône s'aligne avec le bloc sigils (2*18+2=38=EQUIP_ICON_SIZE)
		// Pour 0 ou 1 sigil : l'icône reste en haut
		int iconY = (sigilCount == 2) ? y + h - EQUIP_ICON_SIZE : y;
		drawItemIconPlain(g2d, piece, sectionX + EQUIP_PADDING, iconY, EQUIP_ICON_SIZE);
		drawItemHeader(g2d, piece, textX, y);

		if (sigilCount > 0) {
			int sigilY = (sigilCount == 2) ? iconY : y + h - SIGIL_SLOT_SIZE;
			for (Item sigil : new Item[]{sigil1, sigil2}) {
				if (sigil == null) continue;
				drawSigilSlot(g2d, sigil, textX, sigilY, SIGIL_SLOT_SIZE);
				if (sigil.getName() != null) {
					g2d.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 8));
					g2d.setColor(new Color(210, 170, 100));
					FontMetrics fm = g2d.getFontMetrics();
					int nameOffsetY = (SIGIL_SLOT_SIZE - fm.getHeight()) / 2 + fm.getAscent();
					g2d.drawString(sigil.getName(), textX + SIGIL_SLOT_SIZE + 4, sigilY + nameOffsetY);
				}
				sigilY += SIGIL_SLOT_SIZE + 2;
			}
		}

		return y + h + EQUIP_ROW_GAP;
	}

	private void drawItemIconPlain(Graphics2D g2d, ResolvedEquipmentPiece piece, int x, int y, int size) {
		if (piece == null || piece.item() == null) {
			g2d.setColor(new Color(50, 50, 50, 150));
			g2d.fillRoundRect(x, y, size, size, 4, 4);
			return;
		}
		BufferedImage icon = piece.item().getIcon() != null ? iconFetcherService.fetchImage(piece.item().getIcon()) : null;
		if (icon != null) {
			g2d.drawImage(icon, x, y, size, size, null);
		} else {
			g2d.setColor(new Color(50, 50, 50, 150));
			g2d.fillRoundRect(x, y, size, size, 4, 4);
		}
	}

	private void drawSigilSlot(Graphics2D g2d, Item upgrade, int x, int y, int size) {
		g2d.setColor(new Color(50, 50, 50, 150));
		g2d.fillRoundRect(x, y, size, size, 3, 3);
		if (upgrade != null && upgrade.getIcon() != null) {
			BufferedImage icon = iconFetcherService.fetchImage(upgrade.getIcon());
			if (icon != null) {
				g2d.drawImage(icon, x, y, size, size, null);
			}
		}
	}

	private int rowHeight(ResolvedEquipmentPiece piece) {
		int headerLines = 0;
		if (piece != null && (piece.item() != null || piece.stat() != null)) {
			String label = buildItemLabel(piece);
			String statName = piece.stat() != null ? capitalize(piece.stat().getName()) : null;
			headerLines = countHeaderLines(label, statName);
		}
		int sigilCount = (piece != null && piece.upgrade() != null ? 1 : 0)
				+ (piece != null && piece.upgrade2() != null ? 1 : 0);
		// 2 sigils empilés = 18+2+18 = 38 = exactement EQUIP_ICON_SIZE → alignement parfait icône/sigils
		int upgradeH = sigilCount == 2
				? (headerLines > 0 ? 4 : 0) + EQUIP_ICON_SIZE
				: sigilCount == 1
						? (headerLines > 0 ? 2 : 0) + SIGIL_SLOT_SIZE
						: 0;
		return Math.max(EQUIP_ICON_SIZE, headerLines * EQUIP_ATTR_LINE_HEIGHT + upgradeH);
	}

	private void drawItemHeader(Graphics2D g2d, ResolvedEquipmentPiece piece, int textX, int y) {
		if (piece == null) return;
		String label = buildItemLabel(piece);
		String statName = piece.stat() != null ? capitalize(piece.stat().getName()) : null;
		if (label == null && statName == null) return;
		Font plainFont = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 8);
		g2d.setColor(Color.WHITE);
		FontMetrics fmPlain = g2d.getFontMetrics(plainFont);
		FontMetrics fmBold = g2d.getFontMetrics(boldFont);
		int ascent = fmPlain.getAscent();
		if (label != null && statName != null) {
			String separator = " · ";
			int labelSepW = fmPlain.stringWidth(label + separator);
			int statW = fmBold.stringWidth(statName);
			if (labelSepW + statW <= MAX_TEXT_WIDTH) {
				g2d.setFont(plainFont);
				g2d.drawString(label + separator, textX, y + ascent);
				g2d.setFont(boldFont);
				g2d.drawString(statName, textX + labelSepW, y + ascent);
			} else {
				g2d.setFont(plainFont);
				g2d.drawString(label, textX, y + ascent);
				g2d.setFont(boldFont);
				g2d.drawString(statName, textX, y + EQUIP_ATTR_LINE_HEIGHT + ascent);
			}
		} else if (label != null) {
			g2d.setFont(plainFont);
			g2d.drawString(label, textX, y + ascent);
		} else {
			g2d.setFont(boldFont);
			g2d.drawString(statName, textX, y + ascent);
		}
	}

	private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
		if (fm.stringWidth(text) <= maxWidth) return List.of(text);
		List<String> lines = new ArrayList<>();
		String[] words = text.split(" ");
		StringBuilder current = new StringBuilder();
		for (String word : words) {
			String candidate = current.isEmpty() ? word : current + " " + word;
			if (fm.stringWidth(candidate) <= maxWidth) {
				current = new StringBuilder(candidate);
			} else {
				if (!current.isEmpty()) lines.add(current.toString());
				current = new StringBuilder(word);
			}
		}
		if (!current.isEmpty()) lines.add(current.toString());
		return lines.isEmpty() ? List.of(text) : lines;
	}

	private int countHeaderLines(String label, String statName) {
		if (label == null && statName == null) return 0;
		if (label == null || statName == null) return 1;
		BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		int labelSepW = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 8)).stringWidth(label + " · ");
		int statW = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 8)).stringWidth(statName);
		g.dispose();
		return labelSepW + statW <= MAX_TEXT_WIDTH ? 1 : 2;
	}

	private int drawArmureRow(Graphics2D g2d, ResolvedEquipmentPiece piece, int sectionX, int y) {
		int h = rowHeight(piece);
		drawItemIconPlain(g2d, piece, sectionX + EQUIP_PADDING, y, EQUIP_ICON_SIZE);
		int textX = sectionX + EQUIP_PADDING + EQUIP_ICON_SIZE + 6;
		drawItemHeader(g2d, piece, textX, y);

		Item rune = piece != null ? piece.upgrade() : null;
		if (rune != null) {
			int runeY = y + h - SIGIL_SLOT_SIZE;
			drawSigilSlot(g2d, rune, textX, runeY, SIGIL_SLOT_SIZE);
			if (rune.getName() != null) {
				g2d.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 8));
				g2d.setColor(new Color(210, 170, 100));
				FontMetrics fm = g2d.getFontMetrics();
				int nameOffsetY = (SIGIL_SLOT_SIZE - fm.getHeight()) / 2 + fm.getAscent();
				g2d.drawString(rune.getName(), textX + SIGIL_SLOT_SIZE + 4, runeY + nameOffsetY);
			}
		}

		return y + h + EQUIP_ROW_GAP;
	}

	private void drawRelicRow(Graphics2D g2d, ResolvedEquipmentPiece piece, int sectionX, int y) {
		drawItemIconPlain(g2d, piece, sectionX + EQUIP_PADDING, y, EQUIP_ICON_SIZE);
		if (piece == null) return;
		int textX = sectionX + EQUIP_PADDING + EQUIP_ICON_SIZE + 6;
		String label = buildItemLabel(piece);
		String statName = piece.stat() != null ? capitalize(piece.stat().getName()) : null;
		if (label == null && statName == null) return;
		int lineCount = countHeaderLines(label, statName);
		int offsetY = Math.max(0, (EQUIP_ICON_SIZE - lineCount * EQUIP_ATTR_LINE_HEIGHT) / 2);
		drawItemHeader(g2d, piece, textX, y + offsetY);
	}

	private Map<String, ItemStat> collectUniqueStats(ResolvedEquipment equipment) {
		Map<String, ItemStat> result = new LinkedHashMap<>();
		for (ResolvedEquipmentPiece piece : equipment.bySlot().values()) {
			if (piece != null && piece.stat() != null && piece.stat().getName() != null) {
				result.putIfAbsent(piece.stat().getName(), piece.stat());
			}
		}
		return result;
	}

	private String buildStatSummary(ItemStat stat) {
		StringBuilder sb = new StringBuilder(capitalize(stat.getName()));
		if (stat.getAttributes() != null && !stat.getAttributes().isEmpty()) {
			sb.append(" — ");
			List<String> attrNames = stat.getAttributes().stream()
					.sorted(Comparator.comparingDouble((ItemStatAttribute a) -> a.getMultiplier() != null ? a.getMultiplier() : 0).reversed())
					.map(attr -> ATTR_FR.getOrDefault(attr.getAttribute(), attr.getAttribute()))
					.toList();
			sb.append(String.join(", ", attrNames));
		}
		return sb.toString();
	}

	private String capitalize(String text) {
		if (text == null || text.isEmpty()) return text;
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}

	private String buildItemLabel(ResolvedEquipmentPiece piece) {
		if (piece == null) return null;
		if (piece.item() != null && TypeOfItem.WEAPON.equals(piece.item().getType())
				&& piece.item().getDetails() != null && piece.item().getDetails().getType() != null) {
			return capitalize(WEAPON_TYPE_FR.getOrDefault(piece.item().getDetails().getType(),
					piece.item().getDetails().getType()));
		}
		if (EquipmentSlot.RELIC.equals(piece.slot()) && piece.item() != null && piece.item().getName() != null) {
			return piece.item().getName();
		}
		return SLOT_FR.getOrDefault(piece.slot(), piece.slot().name());
	}

	private int drawSectionHeader(Graphics2D g2d, String title, int x, int y, int width) {
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
		g2d.setColor(new Color(180, 180, 180));
		FontMetrics fm = g2d.getFontMetrics();
		int textX = x + (width - fm.stringWidth(title)) / 2;
		g2d.drawString(title, textX, y + fm.getAscent());
		int lineY = y + SECTION_HEADER_HEIGHT;
		g2d.setColor(new Color(80, 80, 80));
		g2d.drawLine(x, lineY, x + width, lineY);
		return lineY + SECTION_CONTENT_GAP;
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
		int traitsZoneWidth = BUILD_WIDTH - traitsZoneLeft;
		int traitsStartX = traitsZoneLeft + (traitsZoneWidth - traitsAreaWidth) / 2;
		int columnTop = y + VERTICAL_PADDING;
		int centerY = y + SPEC_ROW_HEIGHT / 2;

		// Dessiner les lignes de connexion (sous les icônes)
		g2d.setColor(new Color(255, 255, 255, 220));
		g2d.setStroke(new BasicStroke(2f));
		int lineMargin = 4;

		// Paramètres de l'hexagone pointy-top pour les points de connexion
		double hexR = MINOR_TRAIT_SIZE / 2.0;
		double hexSideX = hexR * Math.sqrt(3) / 2.0;
		int hexHalfH = (int) (hexR / 2);

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

			if (hasPrev) {
				int hexInX = (int) (minorCenterX - hexSideX) - lineMargin;
				int hexInY = centerY;
				if (prevY < centerY) hexInY = centerY - hexHalfH;
				else if (prevY > centerY) hexInY = centerY + hexHalfH;

				g2d.drawLine(prevX, prevY, hexInX, hexInY);
			}

			int hexOutX = (int) (minorCenterX + hexSideX) + lineMargin;
			int hexOutY = centerY;
			if (selectedMajorCenterY < centerY) hexOutY = centerY - hexHalfH;
			else if (selectedMajorCenterY > centerY) hexOutY = centerY + hexHalfH;

			g2d.drawLine(hexOutX, hexOutY, majorColX - lineMargin, selectedMajorCenterY);

			prevX = majorColX + MAJOR_TRAIT_SIZE + lineMargin;
			prevY = selectedMajorCenterY;
			hasPrev = true;
		}

		// Dessiner les icônes par-dessus les lignes
		for (int tier = 0; tier < 3; tier++) {
			int elementIndex = tier * 2;
			int minorX = traitsStartX + elementIndex * (MINOR_TRAIT_SIZE + TRAIT_GAP);
			int majorColX = traitsStartX + (elementIndex + 1) * (MINOR_TRAIT_SIZE + TRAIT_GAP);

			int minorY = y + (SPEC_ROW_HEIGHT - MINOR_TRAIT_SIZE) / 2;
			if (tier < minorTraits.size()) {
				BufferedImage minorIcon = iconFetcherService.fetchImage(minorTraits.get(tier).getIcon());
				if (minorIcon != null) {
					g2d.drawImage(applyHexagonMask(minorIcon), minorX, minorY, MINOR_TRAIT_SIZE, MINOR_TRAIT_SIZE, null);
				}
			}

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
		double scaleX = (double) BUILD_WIDTH / bg.getWidth();
		double scaleY = (double) SPEC_ROW_HEIGHT / bg.getHeight();
		double scale = Math.max(scaleX, scaleY);

		int drawWidth = (int) Math.ceil(bg.getWidth() * scale);
		int drawHeight = (int) Math.ceil(bg.getHeight() * scale);

		int bgX = (BUILD_WIDTH - drawWidth) / 2;
		int bgY = y + (SPEC_ROW_HEIGHT - drawHeight) / 2;

		Shape oldClip = g2d.getClip();
		g2d.setClip(0, y, BUILD_WIDTH, SPEC_ROW_HEIGHT);
		g2d.drawImage(bg, bgX, bgY, drawWidth, drawHeight, null);
		g2d.setColor(new Color(0, 0, 0, 80));
		g2d.fillRect(0, y, BUILD_WIDTH, SPEC_ROW_HEIGHT);
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
		GradientPaint gradient = new GradientPaint(
				0, y, new Color(25, 25, 30, 230),
				0, y + SKILL_ROW_HEIGHT, new Color(15, 15, 20, 200));
		g2d.setPaint(gradient);
		g2d.fillRect(0, y, BUILD_WIDTH, SKILL_ROW_HEIGHT);
		g2d.setPaint(null);

		int gap = 10;
		int totalSkillsWidth = skills.size() * SKILL_ICON_SIZE + (skills.size() - 1) * gap;
		int x = (BUILD_WIDTH - totalSkillsWidth) / 2;
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
			ClassPathResource resource = new ClassPathResource(path, getClass().getClassLoader());
			return ImageIO.read(resource.getInputStream());
		} catch (IOException e) {
			log.warn("Impossible de charger l'image depuis le classpath : {}", path, e);
			return null;
		}
	}
}
