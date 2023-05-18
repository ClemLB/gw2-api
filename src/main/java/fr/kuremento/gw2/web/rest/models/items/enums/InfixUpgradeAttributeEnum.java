package fr.kuremento.gw2.web.rest.models.items.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InfixUpgradeAttributeEnum {

	AGONY_RESISTANCE("AgonyResistance"),
	BOON_DURATION("BoonDuration"),
	CONDITION_DAMAGE("ConditionDamage"),
	CONDITION_DURATION("ConditionDuration"),
	CRIT_DAMAGE("CritDamage"),
	HEALING("Healing"),
	POWER("Power"),
	PRECISION("Precision"),
	TOUGHNESS("Toughness"),
	VITALITY("Vitality");

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
