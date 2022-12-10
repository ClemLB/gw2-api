package fr.kuremento.gw2.web.rest.models.account.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.InfixUpgradeAttributeEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stats {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("attributes")
	InfixUpgradeAttributeEnum attributes;
}
