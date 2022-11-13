package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.InfusionSlotFlag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfusionSlot {

	@JsonProperty(value = "flags", required = true)
	List<InfusionSlotFlag> flags = new ArrayList<>(1);

	@JsonProperty("item_id")
	Integer itemId;

}
