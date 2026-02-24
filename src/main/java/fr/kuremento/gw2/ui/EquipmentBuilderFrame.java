package fr.kuremento.gw2.ui;

import fr.kuremento.gw2.models.builds.AscendedLightArmorStat;
import fr.kuremento.gw2.models.builds.Equipment;
import fr.kuremento.gw2.models.builds.EquipmentPiece;
import fr.kuremento.gw2.models.builds.EquipmentSlot;
import fr.kuremento.gw2.services.builds.BuildTemplateService;
import fr.kuremento.gw2.web.rest.models.items.Item;
import fr.kuremento.gw2.web.rest.models.items.enums.TypeOfItem;
import fr.kuremento.gw2.services.builds.IconFetcherService;
import fr.kuremento.gw2.web.rest.services.items.ItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class EquipmentBuilderFrame extends JFrame {

	private static final Color FRAME_BG = new Color(0x1e, 0x1e, 0x26);
	private static final Color SECTION_BG = new Color(0x24, 0x24, 0x2e);
	private static final Color TITLE_COLOR = new Color(0xcc, 0xaa, 0x44);

	private static final Set<String> TWO_HANDED_WEAPON_TYPES = Set.of(
			"Greatsword", "Hammer", "LongBow", "Rifle", "ShortBow", "Staff",
			"Harpoon", "Trident", "Speargun"
	);

	private final BuildTemplateService buildTemplateService;
	private final ItemsService itemsService;
	private final IconFetcherService iconFetcherService;

	private final Map<EquipmentSlot, SlotData> slotDataMap = new EnumMap<>(EquipmentSlot.class);
	private final Map<EquipmentSlot, EquipmentSlotPanel> slotPanels = new EnumMap<>(EquipmentSlot.class);

	private JTextField chatCodeField;

	public EquipmentBuilderFrame(BuildTemplateService buildTemplateService,
			ItemsService itemsService,
			IconFetcherService iconFetcherService) {
		this.buildTemplateService = buildTemplateService;
		this.itemsService = itemsService;
		this.iconFetcherService = iconFetcherService;

		setTitle("GW2 Equipment Builder");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(FRAME_BG);
		setLayout(new BorderLayout(8, 8));

		add(buildNorthPanel(), BorderLayout.NORTH);
		add(buildCenterPanel(), BorderLayout.CENTER);
		add(buildSouthPanel(), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private JPanel buildNorthPanel() {
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
		north.setBackground(FRAME_BG);
		north.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0x44, 0x44, 0x55)));

		north.add(new JLabel("Charger un code composite :"));
		JTextField loadCodeField = new JTextField(36);
		north.add(loadCodeField);

		JButton loadBtn = new JButton("Charger");
		loadBtn.addActionListener(e -> {
			String code = loadCodeField.getText().trim();
			if (!code.isEmpty()) {
				loadFromCode(code);
			}
		});
		north.add(loadBtn);

		return north;
	}

	private JPanel buildCenterPanel() {
		JPanel center = new JPanel(new BorderLayout(8, 0));
		center.setBackground(FRAME_BG);
		center.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));

		center.add(buildArmorSection(), BorderLayout.WEST);
		center.add(buildRightSection(), BorderLayout.CENTER);

		return center;
	}

	private JPanel buildArmorSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(SECTION_BG);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x44, 0x44, 0x55), 1),
				BorderFactory.createEmptyBorder(6, 6, 6, 6)
		));

		panel.add(sectionTitle("ARMURE"));
		panel.add(Box.createVerticalStrut(4));

		for (EquipmentSlot slot : List.of(
				EquipmentSlot.HELM, EquipmentSlot.SHOULDERS, EquipmentSlot.CHEST,
				EquipmentSlot.GLOVES, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS)) {
			panel.add(createSlotPanel(slot));
			panel.add(Box.createVerticalStrut(3));
		}

		return panel;
	}

	private JPanel buildRightSection() {
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(FRAME_BG);

		right.add(buildAccessoriesSection());
		right.add(Box.createVerticalStrut(6));
		right.add(buildWeaponsSection());
		right.add(Box.createVerticalStrut(6));
		right.add(buildRelicSection());

		return right;
	}

	private JPanel buildAccessoriesSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(SECTION_BG);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x44, 0x44, 0x55), 1),
				BorderFactory.createEmptyBorder(6, 6, 6, 6)
		));

		panel.add(sectionTitle("ACCESSOIRES"));
		panel.add(Box.createVerticalStrut(4));

		JPanel grid = new JPanel(new GridLayout(2, 3, 4, 3));
		grid.setBackground(SECTION_BG);

		for (EquipmentSlot slot : List.of(
				EquipmentSlot.AMULET, EquipmentSlot.RING_1, EquipmentSlot.RING_2,
				EquipmentSlot.ACCESSORY_1, EquipmentSlot.ACCESSORY_2, EquipmentSlot.BACK)) {
			grid.add(createSlotPanel(slot));
		}

		panel.add(grid);
		return panel;
	}

	private JPanel buildWeaponsSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(SECTION_BG);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x44, 0x44, 0x55), 1),
				BorderFactory.createEmptyBorder(6, 6, 6, 6)
		));

		panel.add(sectionTitle("ARMES"));
		panel.add(Box.createVerticalStrut(4));

		JPanel grid = new JPanel(new GridLayout(2, 2, 4, 3));
		grid.setBackground(SECTION_BG);

		for (EquipmentSlot slot : List.of(
				EquipmentSlot.MAIN_HAND_1, EquipmentSlot.OFF_HAND_1,
				EquipmentSlot.MAIN_HAND_2, EquipmentSlot.OFF_HAND_2)) {
			grid.add(createSlotPanel(slot));
		}

		panel.add(grid);
		return panel;
	}

	private JPanel buildRelicSection() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(SECTION_BG);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0x44, 0x44, 0x55), 1),
				BorderFactory.createEmptyBorder(6, 6, 6, 6)
		));

		panel.add(sectionTitle("RELIQUE"));
		panel.add(Box.createVerticalStrut(4));
		panel.add(createSlotPanel(EquipmentSlot.RELIC));

		return panel;
	}

	private EquipmentSlotPanel createSlotPanel(EquipmentSlot slot) {
		EquipmentSlotPanel panel = new EquipmentSlotPanel(slot);
		panel.setClickHandler(this::onSlotClick);
		slotPanels.put(slot, panel);
		return panel;
	}

	private JPanel buildSouthPanel() {
		JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
		south.setBackground(FRAME_BG);
		south.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x44, 0x44, 0x55)));

		south.add(new JLabel("Code de build (optionnel) :"));
		chatCodeField = new JTextField(28);
		south.add(chatCodeField);

		JButton generateBtn = new JButton("Générer l'image");
		generateBtn.addActionListener(e -> onGenerateImage());
		south.add(generateBtn);

		JButton copyCodeBtn = new JButton("Copier le code");
		copyCodeBtn.addActionListener(e -> onCopyCode());
		south.add(copyCodeBtn);

		return south;
	}

	private JLabel sectionTitle(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(TITLE_COLOR);
		label.setFont(new Font("SansSerif", Font.BOLD, 11));
		label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		return label;
	}

	private void onSlotClick(EquipmentSlot slot) {
		SlotData existing = slotDataMap.get(slot);
		SlotEditorDialog dialog = new SlotEditorDialog(this, slot, itemsService, existing);
		dialog.showAndGetResult().ifPresent(newData -> {
			slotDataMap.put(slot, newData);
			loadIconsAsync(slot, newData);
		});
	}

	private void loadIconsAsync(EquipmentSlot slot, SlotData data) {
		new SwingWorker<SlotData, Void>() {
			boolean twoHanded = false;

			@Override
			protected SlotData doInBackground() {
				BufferedImage itemIcon = null;
				BufferedImage upgradeIcon = null;
				BufferedImage upgrade2Icon = null;
				String upgradeName = data.upgradeName();
				String upgrade2Name = data.upgrade2Name();
				String itemName = data.itemName();

				if (data.itemId() != null) {
					try {
						Item item = itemsService.get(data.itemId());
						if (item != null) {
							if (item.getIcon() != null) {
								itemIcon = iconFetcherService.fetchImage(item.getIcon());
							}
							if (itemName == null) {
								itemName = item.getName();
							}
							if (isMainHandSlot(slot)) {
								twoHanded = isTwoHandedWeapon(item);
							}
						}
					} catch (Exception ex) {
						log.warn("Impossible de charger l'icône pour l'item {}: {}", data.itemId(), ex.getMessage());
					}
				}

				if (data.upgradeId() != null) {
					try {
						var upgradeItem = itemsService.get(data.upgradeId());
						if (upgradeItem != null) {
							if (upgradeItem.getIcon() != null) {
								upgradeIcon = iconFetcherService.fetchImage(upgradeItem.getIcon());
							}
							if (upgradeName == null) {
								upgradeName = upgradeItem.getName();
							}
						}
					} catch (Exception ex) {
						log.warn("Impossible de charger l'amélioration {}: {}", data.upgradeId(), ex.getMessage());
					}
				}

				if (data.upgrade2Id() != null) {
					try {
						var upgrade2Item = itemsService.get(data.upgrade2Id());
						if (upgrade2Item != null) {
							if (upgrade2Item.getIcon() != null) {
								upgrade2Icon = iconFetcherService.fetchImage(upgrade2Item.getIcon());
							}
							if (upgrade2Name == null) {
								upgrade2Name = upgrade2Item.getName();
							}
						}
					} catch (Exception ex) {
						log.warn("Impossible de charger l'amélioration 2 {}: {}", data.upgrade2Id(), ex.getMessage());
					}
				}

				return new SlotData(
						data.itemId(), data.statId(), data.upgradeId(), data.upgrade2Id(),
						data.statName(), upgradeName, upgrade2Name, itemName,
						itemIcon, upgradeIcon, upgrade2Icon
				);
			}

			@Override
			protected void done() {
				try {
					SlotData enrichedData = get();
					slotDataMap.put(slot, enrichedData);
					EquipmentSlotPanel panel = slotPanels.get(slot);
					if (panel != null) {
						panel.setSlotData(enrichedData);
					}
				} catch (Exception ex) {
					log.warn("Erreur lors du chargement des icônes pour {}: {}", slot, ex.getMessage());
					EquipmentSlotPanel panel = slotPanels.get(slot);
					if (panel != null) {
						panel.setSlotData(data);
					}
				}
				if (isMainHandSlot(slot)) {
					updateOffHandState(slot, twoHanded);
				}
			}
		}.execute();
	}

	private static boolean isMainHandSlot(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAIN_HAND_1 || slot == EquipmentSlot.MAIN_HAND_2;
	}

	private static boolean isTwoHandedWeapon(Item item) {
		return item.getType() == TypeOfItem.WEAPON
				&& item.getDetails() != null
				&& TWO_HANDED_WEAPON_TYPES.contains(item.getDetails().getType());
	}

	private void updateOffHandState(EquipmentSlot mainSlot, boolean isTwoHanded) {
		EquipmentSlot offHandSlot = switch (mainSlot) {
			case MAIN_HAND_1 -> EquipmentSlot.OFF_HAND_1;
			case MAIN_HAND_2 -> EquipmentSlot.OFF_HAND_2;
			default -> null;
		};
		if (offHandSlot == null) return;

		EquipmentSlotPanel offHandPanel = slotPanels.get(offHandSlot);
		if (offHandPanel == null) return;

		offHandPanel.setSlotDisabled(isTwoHanded);
		if (isTwoHanded) {
			slotDataMap.remove(offHandSlot);
			offHandPanel.setSlotData(null);
		}
	}

	private void loadFromCode(String code) {
		try {
			var fullBuildCode = buildTemplateService.decodeBuildCode(code);
			chatCodeField.setText(fullBuildCode.chatCode());

			slotDataMap.clear();
			slotPanels.values().forEach(p -> p.setSlotData(null));

			for (EquipmentPiece piece : fullBuildCode.equipment().pieces()) {
				String statName = resolveStatName(piece.statId());
				SlotData data = new SlotData(
						piece.itemId(), piece.statId(), piece.upgradeId(), piece.upgrade2Id(),
						statName, null, null, null, null, null, null
				);
				slotDataMap.put(piece.slot(), data);
				EquipmentSlotPanel panel = slotPanels.get(piece.slot());
				if (panel != null) {
					panel.setSlotData(data);
				}
				loadIconsAsync(piece.slot(), data);
			}
		} catch (Exception ex) {
			log.error("Erreur lors du chargement du code", ex);
			JOptionPane.showMessageDialog(this, "Code invalide : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static String resolveStatName(Integer statId) {
		if (statId == null) return null;
		for (AscendedLightArmorStat stat : AscendedLightArmorStat.values()) {
			if (stat.getStatId() == statId) return stat.getLabel();
		}
		return null;
	}

	private void onGenerateImage() {
		List<EquipmentPiece> pieces = slotDataMap.entrySet().stream()
				.filter(e -> e.getValue().itemId() != null)
				.map(e -> e.getValue().toEquipmentPiece(e.getKey()))
				.toList();

		if (pieces.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Aucun slot configuré.", "Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Equipment equipment = new Equipment(pieces);
		String chatCode = chatCodeField.getText().trim();

		new SwingWorker<BufferedImage, Void>() {
			@Override
			protected BufferedImage doInBackground() throws Exception {
				if (chatCode.isEmpty()) {
					return buildTemplateService.generateEquipmentImage(equipment);
				} else {
					return buildTemplateService.generateEquipmentImage(equipment, chatCode);
				}
			}

			@Override
			protected void done() {
				try {
					BufferedImage img = get();
					JFrame imgFrame = new JFrame("Équipement généré");
					imgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					imgFrame.add(new JLabel(new ImageIcon(img)));
					imgFrame.pack();
					imgFrame.setLocationRelativeTo(EquipmentBuilderFrame.this);
					imgFrame.setVisible(true);
				} catch (Exception ex) {
					log.error("Erreur lors de la génération de l'image", ex);
					JOptionPane.showMessageDialog(EquipmentBuilderFrame.this,
							"Erreur lors de la génération : " + ex.getMessage(),
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}

	private void onCopyCode() {
		List<EquipmentPiece> pieces = slotDataMap.entrySet().stream()
				.filter(e -> e.getValue().itemId() != null)
				.map(e -> e.getValue().toEquipmentPiece(e.getKey()))
				.toList();

		if (pieces.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Aucun slot configuré.", "Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String chatCode = chatCodeField.getText().trim();
		if (chatCode.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Un code de build (chat code) est requis pour encoder.", "Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Equipment equipment = new Equipment(pieces);
		try {
			String encoded = buildTemplateService.encodeBuildCode(chatCode, equipment);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(encoded), null);
			JOptionPane.showMessageDialog(this, "Code copié dans le presse-papier !", "Succès", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			log.error("Erreur lors de l'encodage du code", ex);
			JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
}
