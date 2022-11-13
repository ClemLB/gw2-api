package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.items.enums.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

	@JsonProperty(value = "id", required = true)
	Integer id;

	@JsonProperty(value = "chat_link", required = true)
	String chatLink;

	@JsonProperty(value = "name", required = true)
	String name;

	@JsonProperty("icon")
	String icon;

	@JsonProperty("description")
	String description;

	@JsonProperty(value = "type", required = true)
	TypeOfItem type;

	@JsonProperty(value = "rarity", required = true)
	ItemRarity rarity;

	@JsonProperty(value = "level", required = true)
	Integer level;

	@JsonProperty(value = "vendor_value", required = true)
	Integer vendorValue;

	@JsonProperty("default_skin")
	Integer defaultSkin;

	@JsonProperty(value = "flags", required = true)
	List<ItemFlag> flags = new ArrayList<>();

	@JsonProperty(value = "game_types", required = true)
	List<ItemGameType> gameTypes = new ArrayList<>();

	@JsonProperty(value = "restrictions", required = true)
	List<ItemRestriction> restrictions = new ArrayList<>();

	@JsonProperty("upgrades_into")
	List<UpgradeEvolution> upgradesInto = new ArrayList<>();

	@JsonProperty("upgrades_from")
	List<UpgradeEvolution> upgradesFrom = new ArrayList<>();

	@JsonProperty("details")
	ItemDetails details;

}
