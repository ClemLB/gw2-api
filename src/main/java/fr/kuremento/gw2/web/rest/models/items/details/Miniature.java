package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Miniature extends ItemDetails {

	@JsonProperty(value = "minipet_id", required = true)
	Integer minipetId;

}
