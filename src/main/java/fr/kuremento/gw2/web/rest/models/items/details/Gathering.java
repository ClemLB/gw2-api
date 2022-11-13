package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import fr.kuremento.gw2.web.rest.models.items.details.enums.GatheringType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Gathering extends ItemDetails {

	@JsonProperty(value = "type", required = true)
	GatheringType type;

}
