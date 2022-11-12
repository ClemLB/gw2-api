package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bag extends ItemDetails {

	@JsonProperty(value = "size", required = true)
	Integer size;

	@JsonProperty(value = "no_sell_or_sort", required = true)
	Boolean noSellOrSort;

}
