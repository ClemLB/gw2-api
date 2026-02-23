package fr.kuremento.gw2.web.rest.models.itemstats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemStat {

	@JsonProperty(value = "id", required = true)
	Integer id;

	@JsonProperty(value = "name", required = true)
	String name;

	@JsonProperty("attributes")
	List<ItemStatAttribute> attributes = new ArrayList<>();
}
