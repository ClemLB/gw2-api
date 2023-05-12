package fr.kuremento.gw2.web.rest.models.home.cats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cat {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("hint")
	String hint;
}
