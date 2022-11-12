package fr.kuremento.gw2.web.rest.models.raids;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Raid {

	@JsonProperty("id")
	String id;

	@JsonProperty("wings")
	List<Wing> wings = new ArrayList<>();
}
