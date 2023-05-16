package fr.kuremento.gw2.web.rest.models.guild.upgrades.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GuildUpgradeCostType {

	ITEM("Item"), COLLECTIBLE("Collectible"), CURRENCY("Currency"), COINS("Coins");

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
