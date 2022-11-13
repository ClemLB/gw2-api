package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.UpgradeMethod;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpgradeEvolution {

	@JsonProperty(value = "item_id", required = true)
	Integer itemId;

	@JsonProperty(value = "upgrade", required = true)
	UpgradeMethod upgrade;
}
