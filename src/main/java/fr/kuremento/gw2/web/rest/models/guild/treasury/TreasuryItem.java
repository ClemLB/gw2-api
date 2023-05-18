package fr.kuremento.gw2.web.rest.models.guild.treasury;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreasuryItem {

	@JsonProperty("item_id")
	Integer itemId;

	@JsonProperty("count")
	Integer count;

	@JsonProperty("needed_by")
	List<UpgradeTreasury> neededBy = new ArrayList<>();
}
