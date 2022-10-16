package fr.kuremento.gw2.web.rest.models.colors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Color {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("name")
	String name;

	@JsonProperty("base_rgb")
	List<Integer> baseRgb;

	@JsonProperty("cloth")
	Appearence cloth;

	@JsonProperty("leather")
	Appearence leather;

	@JsonProperty("metal")
	Appearence metal;

	@JsonProperty("fur")
	Appearence fur;

	@JsonProperty("item")
	Integer item;

	@JsonProperty("categories")
	List<Category> categories = new ArrayList<>();

}
