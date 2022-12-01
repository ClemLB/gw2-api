package fr.kuremento.gw2.web.rest.models.traits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Skill {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("description")
	String description;

	@JsonProperty("facts")
	List<Fact> facts = new ArrayList<>();

	@JsonProperty("traited_facts")
	List<TraitedFact> traitedFacts = new ArrayList<>();
}
