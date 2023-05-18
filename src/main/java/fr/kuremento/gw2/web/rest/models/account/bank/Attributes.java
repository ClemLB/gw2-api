package fr.kuremento.gw2.web.rest.models.account.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attributes {

	@JsonProperty("AgonyResistance")
	Integer agonyResistance;

	@JsonProperty("BoonDuration")
	Integer boonDuration;

	@JsonProperty("ConditionDamage")
	Integer conditionDamage;

	@JsonProperty("ConditionDuration")
	Integer conditionDuration;

	@JsonProperty("CritDamage")
	Integer critDamage;

	@JsonProperty("Healing")
	Integer healing;

	@JsonProperty("Power")
	Integer power;

	@JsonProperty("Precision")
	Integer precision;

	@JsonProperty("Toughness")
	Integer toughness;

	@JsonProperty("Vitality")
	Integer vitality;
}
