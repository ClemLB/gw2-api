package fr.kuremento.gw2.web.rest.models.traits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraitedFact extends Fact {

	@JsonProperty("requires_trait")
	Integer requiresTrait;

	@JsonProperty("overrides")
	Integer overrides;
}
