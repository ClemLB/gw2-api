package fr.kuremento.gw2.web.rest.models.account.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.account.bank.enums.Binding;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemSlot {

	@JsonProperty("id")
	Integer id;

	@JsonProperty("count")
	Integer count;

	@JsonProperty("charges")
	Integer charges;

	@JsonProperty("skin")
	Integer skin;

	@JsonProperty("dyes")
	List<Integer> dyes;

	@JsonProperty("upgrades")
	List<Integer> upgrades;

	@JsonProperty("upgrade_slot_indices")
	List<Integer> upgradeSlotIndices;

	@JsonProperty("infusions")
	List<Integer> infusions;

	@JsonProperty("binding")
	Binding binding;

	@JsonProperty("bound_to")
	String boundTo;

	@JsonProperty("stats")
	Stats stats;
}
