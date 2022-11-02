package fr.kuremento.gw2.web.rest.models.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.account.enums.Access;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

	@JsonProperty("id")
	String id;

	@JsonProperty("name")
	String name;

	@JsonProperty("age")
	Integer age;

	@JsonProperty("world")
	Integer world;

	@JsonProperty("guilds")
	List<String> guilds = new ArrayList<>();

	@JsonProperty("guild_leader")
	List<String> guildLeader = new ArrayList<>();

	@JsonProperty("created")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX")
	Instant created;

	@JsonProperty("access")
	List<Access> access = new ArrayList<>();

	@JsonProperty("commander")
	Boolean commander;

	@JsonProperty("fractal_level")
	Integer fractalLevel;

	@JsonProperty("daily_ap")
	Integer dailyAp;

	@JsonProperty("monthly_ap")
	Integer monthlyAp;

	@JsonProperty("wvw_rank")
	Integer wvwRank;

}
