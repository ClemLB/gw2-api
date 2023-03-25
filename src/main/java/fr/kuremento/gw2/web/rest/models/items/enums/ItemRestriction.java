package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemRestriction {

	ASURA("Asura"),
	CHARR("Charr"),
	FEMALE("Female"),
	HUMAN("Human"),
	NORN("Norn"),
	SYLVARI("Sylvari"),
	ELEMENTALIST("Elementalist"),
	ENGINEER("Engineer"),
	GUARDIAN("Guardian"),
	MESMER("Mesmer"),
	NECROMANCER("Necromancer"),
	RANGER("Ranger"),
	THIEF("Thief"),
	WARRIOR("Warrior"),
	REVENANT("Revenant");

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
