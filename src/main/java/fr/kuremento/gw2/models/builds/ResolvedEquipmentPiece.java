package fr.kuremento.gw2.models.builds;

import fr.kuremento.gw2.web.rest.models.items.Item;
import fr.kuremento.gw2.web.rest.models.itemstats.ItemStat;

import java.util.List;

public record ResolvedEquipmentPiece(
		EquipmentSlot slot,
		Item item,
		Item upgrade,
		Item upgrade2,
		List<Item> infusions,
		ItemStat stat
) {
}
