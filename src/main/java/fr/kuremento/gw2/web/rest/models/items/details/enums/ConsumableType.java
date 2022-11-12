package fr.kuremento.gw2.web.rest.models.items.details.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConsumableType {

	APPEARANCE_CHANGE("AppearanceChange"),
	BOOZE("Booze"),
	CONTRACT_NPC("ContractNpc"),
	CURRENCY("Currency"),
	FOOD("Food"),
	GENERIC("Generic"),
	HALLOWEEN("Halloween"),
	IMMEDIATE("Immediate"),
	MOUNT_RANDOM_UNLOCK("MountRandomUnlock"),
	RANDOM_UNLOCK("RandomUnlock"),
	TRANSMUTATION("Transmutation"),
	UNLOCK("Unlock"),
	UPGRADE_REMOVAL("UpgradeRemoval"),
	UTILITY("Utility"),
	TELEPORT_TO_FRIEND("TeleportToFriend");

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
