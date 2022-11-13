package fr.kuremento.gw2.web.rest.models.items.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.ItemDetails;
import fr.kuremento.gw2.web.rest.models.items.details.enums.GuizmoType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Guizmo extends ItemDetails {

	@JsonProperty(value = "type", required = true)
	GuizmoType type;

	@JsonProperty("guild_upgrade_id")
	Integer guildUpgradeId;

	@JsonProperty("vendor_ids")
	List<Integer> vendorIds = new ArrayList<>();

}
