package fr.kuremento.gw2.web.rest.models.traits;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.traits.enums.FactType;
import fr.kuremento.gw2.web.rest.models.traits.enums.FieldType;
import fr.kuremento.gw2.web.rest.models.traits.enums.FinisherType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fact {

	@JsonProperty("text")
	String text;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("type")
	FactType type;

	@JsonProperty("value")
	String value;

	@JsonProperty("target")
	String target;

	@JsonProperty("duration")
	Integer duration;

	@JsonProperty("status")
	String status;

	@JsonProperty("description")
	String description;

	@JsonProperty("apply_count")
	Integer applyCount;

	@JsonProperty("percent")
	Integer percent;

	@JsonProperty("source")
	String source;

	@JsonProperty("field_type")
	FieldType fieldType;

	@JsonProperty("finisher_type")
	FinisherType finisherType;

	@JsonProperty("hit_count")
	Integer hitCount;

	@JsonProperty("distance")
	Integer distance;

	@JsonProperty("prefix")
	FactPrefix prefix;
}
