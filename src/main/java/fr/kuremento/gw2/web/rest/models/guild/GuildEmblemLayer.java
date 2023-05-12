package fr.kuremento.gw2.web.rest.models.guild;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuildEmblemLayer {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("colors")
	List<Integer> colors = new ArrayList<>();

}
