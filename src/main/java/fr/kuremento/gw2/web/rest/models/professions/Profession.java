package fr.kuremento.gw2.web.rest.models.professions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profession {

	@JsonProperty("id")
	String id;

	@JsonProperty("name")
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("specializations")
	List<Integer> specializations = new ArrayList<>();

	@JsonProperty("skills_by_palette")
	List<List<Integer>> skillsByPalette = new ArrayList<>();
}
