package fr.kuremento.gw2.web.rest.models.guild.treasury;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpgradeTreasury {

	@JsonProperty("upgrade_id")
	Integer upgradeId;

	@JsonProperty("count")
	Integer count;
}
