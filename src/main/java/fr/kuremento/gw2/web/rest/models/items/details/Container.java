package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import fr.kuremento.gw2.web.rest.models.items.details.enums.ContainerType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Container extends ItemDetails {

	@JsonProperty(value = "type", required = true)
	ContainerType type;

}
