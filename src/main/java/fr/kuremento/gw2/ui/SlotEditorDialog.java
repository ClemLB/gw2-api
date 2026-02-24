package fr.kuremento.gw2.ui;

import fr.kuremento.gw2.models.builds.AscendedLightArmorStat;
import fr.kuremento.gw2.models.builds.EquipmentSlot;
import fr.kuremento.gw2.web.rest.services.items.ItemsService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.Set;

public class SlotEditorDialog extends JDialog {

	private static final Set<EquipmentSlot> ARMOR_SLOTS = Set.of(
			EquipmentSlot.HELM, EquipmentSlot.SHOULDERS, EquipmentSlot.CHEST,
			EquipmentSlot.GLOVES, EquipmentSlot.LEGGINGS, EquipmentSlot.BOOTS
	);

	private final EquipmentSlot slot;
	private final ItemsService itemsService;
	private SlotData result = null;

	// Composants communs
	private JComboBox<AscendedLightArmorStat> statCombo;
	private JTextField itemIdField;
	private JTextField upgradeIdField;
	private JLabel upgradeNameLabel;
	private JTextField upgrade2IdField;
	private JLabel upgrade2NameLabel;

	public SlotEditorDialog(Frame parent, EquipmentSlot slot, ItemsService itemsService, SlotData existing) {
		super(parent, "Configurer : " + EquipmentSlotPanel.slotDisplayName(slot), true);
		this.slot = slot;
		this.itemsService = itemsService;

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		JPanel content = buildContent(existing);
		setContentPane(content);
		pack();
		setLocationRelativeTo(parent);
	}

	private JPanel buildContent(SlotData existing) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 4, 3, 4);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int row = 0;
		boolean isArmor = ARMOR_SLOTS.contains(slot);
		boolean isRelic = slot == EquipmentSlot.RELIC;

		// --- Stat ---
		if (!isRelic) {
			gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
			panel.add(new JLabel("Statistiques :"), gbc);

			statCombo = new JComboBox<>(AscendedLightArmorStat.values());
			statCombo.setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					if (value instanceof AscendedLightArmorStat s) {
						setText(s.getLabel());
					}
					return this;
				}
			});
			if (existing != null && existing.statName() != null) {
				for (int i = 0; i < statCombo.getItemCount(); i++) {
					if (statCombo.getItemAt(i).getLabel().equals(existing.statName())) {
						statCombo.setSelectedIndex(i);
						break;
					}
				}
			}
			gbc.gridx = 1; gbc.weightx = 1.0;
			panel.add(statCombo, gbc);
			row++;
		}

		// --- Item ID ---
		gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
		panel.add(new JLabel("Item ID :"), gbc);

		if (isArmor) {
			itemIdField = new JTextField(12);
			itemIdField.setEditable(false);
			itemIdField.setForeground(Color.GRAY);
			updateArmorItemId();
			if (statCombo != null) {
				statCombo.addActionListener(e -> updateArmorItemId());
			}
		} else {
			itemIdField = new JTextField(12);
			if (existing != null && existing.itemId() != null) {
				itemIdField.setText(String.valueOf(existing.itemId()));
			}
		}
		gbc.gridx = 1; gbc.weightx = 1.0;
		panel.add(itemIdField, gbc);
		row++;

		// --- Amélioration 1 ---
		if (!isRelic) {
			gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
			panel.add(new JLabel("Amélioration 1 :"), gbc);

			upgradeIdField = new JTextField(8);
			if (existing != null && existing.upgradeId() != null) {
				upgradeIdField.setText(String.valueOf(existing.upgradeId()));
			}
			upgradeNameLabel = new JLabel(" ");
			if (existing != null && existing.upgradeName() != null) {
				upgradeNameLabel.setText(existing.upgradeName());
			}

			JButton resolveBtn1 = new JButton("Résoudre");
			resolveBtn1.addActionListener(e -> resolveUpgradeName(upgradeIdField, upgradeNameLabel));

			JPanel upgradePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
			upgradePanel.add(upgradeIdField);
			upgradePanel.add(resolveBtn1);
			upgradePanel.add(upgradeNameLabel);

			gbc.gridx = 1; gbc.weightx = 1.0;
			panel.add(upgradePanel, gbc);
			row++;

			// --- Amélioration 2 ---
			gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
			panel.add(new JLabel("Amélioration 2 :"), gbc);

			upgrade2IdField = new JTextField(8);
			if (existing != null && existing.upgrade2Id() != null) {
				upgrade2IdField.setText(String.valueOf(existing.upgrade2Id()));
			}
			upgrade2NameLabel = new JLabel(" ");
			if (existing != null && existing.upgrade2Name() != null) {
				upgrade2NameLabel.setText(existing.upgrade2Name());
			}

			JButton resolveBtn2 = new JButton("Résoudre");
			resolveBtn2.addActionListener(e -> resolveUpgradeName(upgrade2IdField, upgrade2NameLabel));

			JPanel upgrade2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
			upgrade2Panel.add(upgrade2IdField);
			upgrade2Panel.add(resolveBtn2);
			upgrade2Panel.add(upgrade2NameLabel);

			gbc.gridx = 1; gbc.weightx = 1.0;
			panel.add(upgrade2Panel, gbc);
			row++;
		}

		// --- Boutons OK / Annuler ---
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
		JButton okBtn = new JButton("OK");
		JButton cancelBtn = new JButton("Annuler");

		okBtn.addActionListener(e -> onOk());
		cancelBtn.addActionListener(e -> dispose());

		btnPanel.add(okBtn);
		btnPanel.add(cancelBtn);

		gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1.0;
		gbc.insets = new Insets(10, 4, 4, 4);
		panel.add(btnPanel, gbc);

		return panel;
	}

	private void updateArmorItemId() {
		if (statCombo == null || itemIdField == null) return;
		AscendedLightArmorStat stat = (AscendedLightArmorStat) statCombo.getSelectedItem();
		if (stat == null) return;
		int itemId = switch (slot) {
			case HELM -> stat.getHelm();
			case SHOULDERS -> stat.getShoulders();
			case CHEST -> stat.getChest();
			case GLOVES -> stat.getGloves();
			case LEGGINGS -> stat.getLeggings();
			case BOOTS -> stat.getBoots();
			default -> 0;
		};
		itemIdField.setText(itemId != 0 ? String.valueOf(itemId) : "");
	}

	private void resolveUpgradeName(JTextField idField, JLabel nameLabel) {
		String text = idField.getText().trim();
		if (text.isEmpty()) return;
		int id;
		try {
			id = Integer.parseInt(text);
		} catch (NumberFormatException ex) {
			nameLabel.setText("ID invalide");
			return;
		}
		nameLabel.setText("Chargement...");
		new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() {
				try {
					var item = itemsService.get(id);
					return item != null ? item.getName() : "Inconnu";
				} catch (Exception ex) {
					return "Erreur : " + ex.getMessage();
				}
			}

			@Override
			protected void done() {
				try {
					nameLabel.setText(get());
					pack();
				} catch (Exception ex) {
					nameLabel.setText("Erreur");
				}
			}
		}.execute();
	}

	private void onOk() {
		Integer itemId = null;
		Integer statId = null;
		String statName = null;
		Integer upgradeId = null;
		String upgradeName = null;
		Integer upgrade2Id = null;
		String upgrade2Name = null;

		// Item ID
		String itemText = itemIdField.getText().trim();
		if (!itemText.isEmpty()) {
			try {
				itemId = Integer.parseInt(itemText);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Item ID invalide : " + itemText, "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Stat
		if (statCombo != null) {
			AscendedLightArmorStat stat = (AscendedLightArmorStat) statCombo.getSelectedItem();
			if (stat != null) {
				statId = stat.getStatId();
				statName = stat.getLabel();
			}
		}

		// Amélioration 1
		if (upgradeIdField != null) {
			String upText = upgradeIdField.getText().trim();
			if (!upText.isEmpty()) {
				try {
					upgradeId = Integer.parseInt(upText);
					upgradeName = upgradeNameLabel.getText().trim();
					if (upgradeName.isBlank() || upgradeName.equals("Chargement...") || upgradeName.equals("Erreur")) {
						upgradeName = null;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID Amélioration 1 invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		// Amélioration 2
		if (upgrade2IdField != null) {
			String up2Text = upgrade2IdField.getText().trim();
			if (!up2Text.isEmpty()) {
				try {
					upgrade2Id = Integer.parseInt(up2Text);
					upgrade2Name = upgrade2NameLabel.getText().trim();
					if (upgrade2Name.isBlank() || upgrade2Name.equals("Chargement...") || upgrade2Name.equals("Erreur")) {
						upgrade2Name = null;
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "ID Amélioration 2 invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		result = new SlotData(itemId, statId, upgradeId, upgrade2Id, statName, upgradeName, upgrade2Name, null, null, null, null);
		dispose();
	}

	public Optional<SlotData> showAndGetResult() {
		setVisible(true);
		return Optional.ofNullable(result);
	}
}
