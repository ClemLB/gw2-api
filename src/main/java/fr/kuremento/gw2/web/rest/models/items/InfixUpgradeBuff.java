package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfixUpgradeBuff {

	@JsonProperty(value = "skill_id", required = true)
	Integer skillId;

	@JsonProperty("description")
	String description;

}
