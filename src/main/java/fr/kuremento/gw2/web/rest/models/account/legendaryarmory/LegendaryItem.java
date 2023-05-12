package fr.kuremento.gw2.web.rest.models.account.legendaryarmory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegendaryItem {

	@JsonProperty("id")
	String id;

	@JsonProperty("count")
	Integer count;
}
