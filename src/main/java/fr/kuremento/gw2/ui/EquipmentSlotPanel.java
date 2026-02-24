package fr.kuremento.gw2.ui;

import fr.kuremento.gw2.models.builds.EquipmentSlot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class EquipmentSlotPanel extends JPanel {

	private static final int WIDTH = 160;
	private static final int HEIGHT = 70;
	private static final int ARC = 8;

	private static final Color BG_COLOR = new Color(0x28, 0x28, 0x30);
	private static final Color BG_HOVER = new Color(0x38, 0x38, 0x48);
	private static final Color BG_DISABLED = new Color(0x1a, 0x1a, 0x20);
	private static final Color BORDER_COLOR = new Color(0x50, 0x50, 0x60);
	private static final Color BORDER_HOVER = new Color(0x80, 0x80, 0xa0);
	private static final Color BORDER_DISABLED = new Color(0x30, 0x30, 0x38);
	private static final Color STAT_COLOR = new Color(0xdd, 0xaa, 0x00);
	private static final Color UPGRADE_COLOR = new Color(0x66, 0xcc, 0x66);
	private static final Color SLOT_LABEL_COLOR = new Color(0x99, 0x99, 0xaa);
	private static final Color HINT_COLOR = new Color(0x66, 0x66, 0x77);
	private static final Color DISABLED_LABEL_COLOR = new Color(0x44, 0x44, 0x50);

	private final EquipmentSlot slot;
	private SlotData data;
	private boolean hovered = false;
	private boolean slotDisabled = false;
	private Consumer<EquipmentSlot> clickHandler;

	public EquipmentSlotPanel(EquipmentSlot slot) {
		this.slot = slot;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!slotDisabled) {
					hovered = true;
					repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				hovered = false;
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!slotDisabled && clickHandler != null) {
					clickHandler.accept(slot);
				}
			}
		});
	}

	public void setClickHandler(Consumer<EquipmentSlot> handler) {
		this.clickHandler = handler;
	}

	public void setSlotData(SlotData data) {
		this.data = data;
		repaint();
	}

	public void setSlotDisabled(boolean disabled) {
		this.slotDisabled = disabled;
		this.hovered = false;
		setCursor(disabled
				? Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
				: Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Fond
		g2.setColor(slotDisabled ? BG_DISABLED : (hovered ? BG_HOVER : BG_COLOR));
		g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, ARC, ARC));

		// Bord
		g2.setColor(slotDisabled ? BORDER_DISABLED : (hovered ? BORDER_HOVER : BORDER_COLOR));
		g2.setStroke(slotDisabled ? new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[]{4f, 4f}, 0f) : new BasicStroke(1f));
		g2.draw(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, ARC, ARC));

		if (slotDisabled) {
			paintDisabled(g2, w, h);
		} else if (data == null || data.itemId() == null) {
			paintEmpty(g2, w, h);
		} else {
			paintFilled(g2, w, h);
		}

		g2.dispose();
	}

	private void paintDisabled(Graphics2D g2, int w, int h) {
		g2.setFont(new Font("SansSerif", Font.ITALIC, 10));
		g2.setColor(DISABLED_LABEL_COLOR);
		String line1 = slotDisplayName(slot);
		String line2 = "Occupé (2 mains)";
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString(line1, (w - fm.stringWidth(line1)) / 2, h / 2 - 5);
		g2.drawString(line2, (w - fm.stringWidth(line2)) / 2, h / 2 + 9);
	}

	private void paintEmpty(Graphics2D g2, int w, int h) {
		String slotLabel = slotDisplayName(slot);

		g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
		g2.setColor(SLOT_LABEL_COLOR);
		FontMetrics fm = g2.getFontMetrics();
		int labelWidth = fm.stringWidth(slotLabel);
		g2.drawString(slotLabel, (w - labelWidth) / 2, h / 2 - 4);

		g2.setFont(new Font("SansSerif", Font.ITALIC, 9));
		g2.setColor(HINT_COLOR);
		String hint = "Cliquer pour configurer";
		FontMetrics fmHint = g2.getFontMetrics();
		int hintWidth = fmHint.stringWidth(hint);
		g2.drawString(hint, (w - hintWidth) / 2, h / 2 + 10);
	}

	private void paintFilled(Graphics2D g2, int w, int h) {
		int iconSize = 48;
		int iconX = 6;
		int iconY = (h - iconSize) / 2;

		// Icône item
		BufferedImage itemIcon = data.itemIcon();
		if (itemIcon != null) {
			g2.drawImage(itemIcon, iconX, iconY, iconSize, iconSize, null);
		} else {
			g2.setColor(new Color(0x44, 0x44, 0x55));
			g2.fillRoundRect(iconX, iconY, iconSize, iconSize, 4, 4);
			g2.setColor(SLOT_LABEL_COLOR);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 8));
			g2.drawString(slotDisplayName(slot), iconX + 2, iconY + iconSize / 2 + 4);
		}

		int textX = iconX + iconSize + 6;
		int textMaxWidth = w - textX - 4;

		// Nom stat (doré) ou nom de l'item si pas de stat (ex : relique)
		String firstLine = data.statName() != null ? data.statName() : data.itemName();
		if (firstLine != null) {
			g2.setFont(new Font("SansSerif", Font.BOLD, 10));
			g2.setColor(data.statName() != null ? STAT_COLOR : SLOT_LABEL_COLOR);
			g2.drawString(truncate(g2, firstLine, textMaxWidth), textX, iconY + 14);
		}

		// Icône + nom amélioration 1 (vert)
		int upgradeY = iconY + 30;
		BufferedImage upgradeIcon = data.upgradeIcon();
		if (upgradeIcon != null) {
			g2.drawImage(upgradeIcon, textX, upgradeY - 12, 14, 14, null);
			textX += 18;
			textMaxWidth -= 18;
		}
		if (data.upgradeName() != null) {
			g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
			g2.setColor(UPGRADE_COLOR);
			String upgradeText = truncate(g2, data.upgradeName(), textMaxWidth);
			g2.drawString(upgradeText, textX, upgradeY);
		}

		// Icône + nom amélioration 2 (vert)
		if (data.upgrade2Id() != null) {
			int u2x = iconX + iconSize + 6;
			int u2MaxWidth = w - u2x - 4;
			BufferedImage upgrade2Icon = data.upgrade2Icon();
			if (upgrade2Icon != null) {
				g2.drawImage(upgrade2Icon, u2x, iconY + 34, 14, 14, null);
				u2x += 18;
				u2MaxWidth -= 18;
			}
			if (data.upgrade2Name() != null) {
				g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
				g2.setColor(UPGRADE_COLOR);
				g2.drawString(truncate(g2, data.upgrade2Name(), u2MaxWidth), u2x, iconY + 46);
			}
		}
	}

	private String truncate(Graphics2D g2, String text, int maxWidth) {
		FontMetrics fm = g2.getFontMetrics();
		if (fm.stringWidth(text) <= maxWidth) {
			return text;
		}
		while (text.length() > 1 && fm.stringWidth(text + "…") > maxWidth) {
			text = text.substring(0, text.length() - 1);
		}
		return text + "…";
	}

	public static String slotDisplayName(EquipmentSlot slot) {
		return switch (slot) {
			case HELM -> "Casque";
			case SHOULDERS -> "Épaulières";
			case CHEST -> "Plastron";
			case GLOVES -> "Gants";
			case LEGGINGS -> "Jambières";
			case BOOTS -> "Bottes";
			case MAIN_HAND_1 -> "Main droite 1";
			case OFF_HAND_1 -> "Main gauche 1";
			case MAIN_HAND_2 -> "Main droite 2";
			case OFF_HAND_2 -> "Main gauche 2";
			case AMULET -> "Amulette";
			case RING_1 -> "Anneau 1";
			case RING_2 -> "Anneau 2";
			case ACCESSORY_1 -> "Bijou 1";
			case ACCESSORY_2 -> "Bijou 2";
			case BACK -> "Dos";
			case RELIC -> "Relique";
		};
	}
}
