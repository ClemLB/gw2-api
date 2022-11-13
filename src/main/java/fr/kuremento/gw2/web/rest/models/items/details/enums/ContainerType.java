package fr.kuremento.gw2.web.rest.models.items.details.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContainerType {

	DEFAULT("Default"), GIFT_BOX("GiftBox"), IMMEDIATE("Immediate"), OPEN_UI("OpenUI");

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
