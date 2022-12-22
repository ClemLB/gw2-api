package fr.kuremento.gw2.web.rest.models.home.nodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {

	@JsonProperty("id")
	String id;
}
