package fr.kuremento.gw2.web.rest.models.guild.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmblemFlag {
	FLIP_BACKGROUND_HORIZONTAL("FlipBackgroundHorizontal"), FLIP_BACKGROUND_VERTICAL("FlipBackgroundVertical"), FLIP_FOREGROUND_HORIZONTAL("FlipForegroundHorizontal"), FLIP_FOREGROUND_VERTICAL("FlipForegroundVertical");

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