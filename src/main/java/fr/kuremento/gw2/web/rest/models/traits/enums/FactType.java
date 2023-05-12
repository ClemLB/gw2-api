package fr.kuremento.gw2.web.rest.models.traits.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FactType {
	ATTRIBUTE_ADJUST("AttributeAdjust"), BUFF("Buff"), BUFF_CONVERSION("BuffConversion"), COMBO_FIELD("ComboField"), COMBO_FINISHER("ComboFinisher"), DAMAGE("Damage"), DISTANCE(
			"Distance"), NO_DATA("NoData"), NUMBER("Number"), PERCENT("Percent"), PREFIXED_BUFF("PrefixedBuff"), RADIUS("Radius"), RANGE("Range"), RECHARGE("Recharge"), TIME(
					"Time"), UNBLOCKABLE(
			"Unblockable"), STUN_BREAK("StunBreak");

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
