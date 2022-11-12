package fr.kuremento.gw2.web.rest.models.raids;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.raids.enums.TypeOfEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

	@JsonProperty("id")
	String id;

	@JsonProperty("type")
	TypeOfEvent type;

}
