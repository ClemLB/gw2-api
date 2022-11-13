package fr.kuremento.gw2.web.rest.models.items.details.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GuizmoType {

	DEFAULT("Default"), CONTAINER_KEY("ContainerKey"), RENTABLE_CONTRACT_NPC("RentableContractNpc"), UNLIMITED_CONSUMABLE("UnlimitedConsumable");

	private final String value;

	@JsonValue
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}
