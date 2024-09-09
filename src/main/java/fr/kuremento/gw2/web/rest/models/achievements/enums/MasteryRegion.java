package fr.kuremento.gw2.web.rest.models.achievements.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MasteryRegion {
	TYRIA("Tyria"), MAGUUMA("Maguuma"), DESERT("Desert"), TUNDRA("Tundra"), JADE("Jade"), SKY("Sky"), UNKNOWN("Unknown");

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
