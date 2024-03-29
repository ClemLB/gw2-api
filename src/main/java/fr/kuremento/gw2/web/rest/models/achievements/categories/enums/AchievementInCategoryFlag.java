package fr.kuremento.gw2.web.rest.models.achievements.categories.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AchievementInCategoryFlag {
	PVP("PvP"), PVE("PvE"), WVW("WvW"), SPECIAL_EVENT("SpecialEvent");

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
