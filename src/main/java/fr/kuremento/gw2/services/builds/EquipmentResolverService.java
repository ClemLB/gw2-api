package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.models.builds.*;
import fr.kuremento.gw2.web.rest.models.items.Item;
import fr.kuremento.gw2.web.rest.models.itemstats.ItemStat;
import fr.kuremento.gw2.web.rest.services.items.ItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import fr.kuremento.gw2.exceptions.TooManyArgumentsException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipmentResolverService {

	private final ItemsService itemsService;
	private final Map<Integer, ItemStat> itemStatsReferential;

	public ResolvedEquipment resolve(Equipment equipment) throws TooManyArgumentsException {
		log.info("Résolution de l'équipement ({} pièces)", equipment.pieces().size());

		Set<Integer> allItemIds = new LinkedHashSet<>();
		for (EquipmentPiece piece : equipment.pieces()) {
			allItemIds.add(piece.itemId());
			if (piece.upgradeId() != null) allItemIds.add(piece.upgradeId());
			if (piece.upgrade2Id() != null) allItemIds.add(piece.upgrade2Id());
			if (piece.infusionIds() != null) piece.infusionIds().forEach(allItemIds::add);
		}

		Map<Integer, Item> itemsById = itemsService.get(new ArrayList<>(allItemIds)).stream()
				.collect(Collectors.toMap(Item::getId, Function.identity()));

		List<ResolvedEquipmentPiece> resolved = equipment.pieces().stream()
				.map(piece -> resolvePiece(piece, itemsById))
				.toList();

		return new ResolvedEquipment(resolved);
	}

	private ResolvedEquipmentPiece resolvePiece(EquipmentPiece piece, Map<Integer, Item> itemsById) {
		Item item = itemsById.get(piece.itemId());
		Item upgrade = piece.upgradeId() != null ? itemsById.get(piece.upgradeId()) : null;
		Item upgrade2 = piece.upgrade2Id() != null ? itemsById.get(piece.upgrade2Id()) : null;

		List<Item> infusions = piece.infusionIds() != null
				? piece.infusionIds().stream().map(itemsById::get).filter(Objects::nonNull).toList()
				: List.of();

		ItemStat stat = null;
		Integer statId = piece.statId();
		if (statId == null && item != null && item.getDetails() != null
				&& item.getDetails().getInfixUpgrade() != null) {
			statId = item.getDetails().getInfixUpgrade().getId();
		}
		if (statId != null) {
			stat = itemStatsReferential.get(statId);
			if (stat == null) {
				log.warn("Stat ID {} introuvable dans le référentiel local", statId);
			}
		}

		return new ResolvedEquipmentPiece(piece.slot(), item, upgrade, upgrade2, infusions, stat);
	}
}
