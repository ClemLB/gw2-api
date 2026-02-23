package fr.kuremento.gw2.models.builds;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Equipment(List<EquipmentPiece> pieces) {

	public Map<EquipmentSlot, EquipmentPiece> bySlot() {
		return pieces.stream().collect(Collectors.toMap(EquipmentPiece::slot, Function.identity()));
	}
}
