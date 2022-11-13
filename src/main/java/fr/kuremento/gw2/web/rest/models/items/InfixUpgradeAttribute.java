package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.InfixUpgradeAttributeEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfixUpgradeAttribute {

	@JsonProperty(value = "attribute", required = true)
	InfixUpgradeAttributeEnum attribute;

	@JsonProperty(value = "flags", required = true)
	Integer modifier;

}
