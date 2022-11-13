package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfixUpgrade {

	@JsonProperty("id")
	Integer id;

	@JsonProperty(value = "attributes", required = true)
	List<InfixUpgradeAttribute> attributes = new ArrayList<>();

	@JsonProperty("buff")
	InfixUpgradeBuff buff;


}
