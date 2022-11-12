package fr.kuremento.gw2.web.rest.models.minis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Miniature {

	@JsonProperty("id")
	Integer id;
	
	@JsonProperty("name")
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("order")
	Integer order;

	@JsonProperty("item_id")
	Integer itemId;

}
