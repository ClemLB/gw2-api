package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemFlag {

	ACCOUNT_BIND_ON_USE("AccountBindOnUse"),
	ACCOUNT_BOUND("AccountBound"),
	ATTUNED("Attuned"),
	BULK_CONSUME("BulkConsume"),
	DELETE_WARNING("DeleteWarning"),
	HIDE_SUFFIX("HideSuffix"),
	INFUSED("Infused"),
	MONSTER_ONLY("MonsterOnly"),
	NO_MYSTIC_FORGE("NoMysticForge"),
	NO_SALVAGE("NoSalvage"),
	NO_SELL("NoSell"),
	NOT_UPGRADEABLE("NotUpgradeable"),
	NO_UNDERWATER("NoUnderwater"),
	SOULBIND_ON_ACQUIRE("SoulbindOnAcquire"),
	SOUL_BIND_ON_USE("SoulBindOnUse"),
	TONIC("Tonic"),
	UNIQUE("Unique");

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
