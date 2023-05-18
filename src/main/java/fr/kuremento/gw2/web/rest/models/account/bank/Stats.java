package fr.kuremento.gw2.web.rest.models.account.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stats {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("attributes")
	Attributes attributes;
}
