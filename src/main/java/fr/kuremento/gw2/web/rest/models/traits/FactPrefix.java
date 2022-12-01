package fr.kuremento.gw2.web.rest.models.traits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FactPrefix {

	@JsonProperty("text")
	String text;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("status")
	String status;

	@JsonProperty("description")
	String description;
}
