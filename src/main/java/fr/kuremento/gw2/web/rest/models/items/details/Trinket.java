package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import fr.kuremento.gw2.web.rest.models.items.details.enums.TrinketType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trinket extends ItemDetails {

	@JsonProperty(value = "type", required = true)
	TrinketType type;

	@JsonProperty(value = "infusion_slots", required = true)
	List<InfusionSlot> infusionSlots = new ArrayList<>();

	@JsonProperty(value = "attribute_adjustment", required = true)
	Integer attributeAdjustment;

	@JsonProperty("infix_upgrade")
	Object infixUpgrade;

	@JsonProperty("suffix_item_id")
	Integer suffixItemId;

	@JsonProperty(value = "secondary_suffix_item_id", required = true)
	String secondarySuffixItemId;

	@JsonProperty("stat_choices")
	List<Integer> statChoices = new ArrayList<>();

}
