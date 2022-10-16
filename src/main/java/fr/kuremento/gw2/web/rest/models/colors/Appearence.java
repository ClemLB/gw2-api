package fr.kuremento.gw2.web.rest.models.colors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appearence {

	@JsonProperty("brightness")
	int brightness;

	@JsonProperty("contrast")
	double contrast;

	@JsonProperty("hue")
	int hue;

	@JsonProperty("saturation")
	double saturation;

	@JsonProperty("lightness")
	double lightness;

	@JsonProperty("rgb")
	List<Integer> rgb = new ArrayList<>(3);
}
