package fr.kuremento.gw2.web.rest.models.account.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Access {
	F2P("PlayForFree"), BASE_GAME("GuildWars2"), HOT("HeartOfThorns"), POF("PathOfFire"), EOD("EndOfDragons");
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
