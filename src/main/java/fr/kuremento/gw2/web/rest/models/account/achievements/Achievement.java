package fr.kuremento.gw2.web.rest.models.account.achievements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Achievement {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("bits")
	List<Integer> bits = new ArrayList<>();

	@JsonProperty("current")
	Integer current;

	@JsonProperty("max")
	Integer max;

	@JsonProperty("done")
	Boolean done;

	@JsonProperty("repeated")
	Integer repeated;

	@JsonProperty("unlocked")
	Boolean unlocked;

}
