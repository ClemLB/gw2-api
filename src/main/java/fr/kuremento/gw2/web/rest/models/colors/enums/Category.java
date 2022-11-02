package fr.kuremento.gw2.web.rest.models.colors.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
	GRAY("Gray"),
	BROWN("Brown"),
	RED("Red"),
	ORANGE("Orange"),
	YELLOW("Yellow"),
	GREEN("Green"),
	BLUE("Blue"),
	PURPLE("Purple"),
	VIBRANT("Vibrant"),
	LEATHER("Leather"),
	METAL("Metal"),
	STARTER("Starter"),
	COMMON("Common"),
	UNCOMMON("Uncommon"),
	RARE("Rare"),
	EXCLUSIVE("Exclusive");
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