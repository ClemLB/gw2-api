package fr.kuremento.gw2.web.rest.models.specializations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Specialization {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("profession")
	String profession;

	@JsonProperty("elite")
	Boolean elite;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("background")
	String background;

	@JsonProperty("minor_traits")
	List<Integer> minorTraits = new ArrayList<>();

	@JsonProperty("major_traits")
	List<Integer> majorTraits = new ArrayList<>();
}
