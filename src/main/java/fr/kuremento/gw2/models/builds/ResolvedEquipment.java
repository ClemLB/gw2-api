package fr.kuremento.gw2.models.builds;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ResolvedEquipment(List<ResolvedEquipmentPiece> pieces) {

	public Map<EquipmentSlot, ResolvedEquipmentPiece> bySlot() {
		return pieces.stream().collect(Collectors.toMap(ResolvedEquipmentPiece::slot, Function.identity()));
	}

	public ResolvedEquipmentPiece get(EquipmentSlot slot) {
		return bySlot().get(slot);
	}
}
