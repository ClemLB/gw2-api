package fr.kuremento.gw2.web.rest.models.quaggans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quaggan {

	@JsonProperty("id")
	String id;

	@JsonProperty("url")
	String url;
}

