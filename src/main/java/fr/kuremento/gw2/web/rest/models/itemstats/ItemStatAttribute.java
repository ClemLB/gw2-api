package fr.kuremento.gw2.web.rest.models.itemstats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemStatAttribute {

	@JsonProperty("attribute")
	String attribute;

	@JsonProperty("multiplier")
	Double multiplier;

	@JsonProperty("value")
	Integer value;
}
