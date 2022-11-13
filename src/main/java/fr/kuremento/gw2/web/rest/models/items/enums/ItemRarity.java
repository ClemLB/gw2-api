package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemRarity {

	JUNK("Junk"), BASIC("Basic"), FINE("Fine"), MASTERWORK("Masterwork"), RARE("Rare"), EXOTIC("Exotic"), ASCENDED("Ascended"), LEGENDARY("Legendary");

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
