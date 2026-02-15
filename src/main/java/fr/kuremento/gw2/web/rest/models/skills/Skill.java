package fr.kuremento.gw2.web.rest.models.skills;

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

	@JsonProperty("type")
	String type;

	@JsonProperty("professions")
	List<String> professions = new ArrayList<>();
}
