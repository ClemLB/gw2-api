package fr.kuremento.gw2.web.rest.models.traits;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.traits.enums.TraitSlot;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trait {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("description")
	String description;

	@JsonProperty("specialization")
	Integer specialization;

	@JsonProperty("tier")
	Integer tier;

	@JsonProperty("slot")
	TraitSlot slot;

	@JsonProperty("facts")
	List<Fact> facts = new ArrayList<>();

	@JsonProperty("traited_facts")
	List<TraitedFact> traitedFacts = new ArrayList<>();

	@JsonProperty("skills")
	List<Skill> skills = new ArrayList<>();
}
