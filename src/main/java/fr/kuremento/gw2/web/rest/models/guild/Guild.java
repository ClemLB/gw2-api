package fr.kuremento.gw2.web.rest.models.guild;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Guild {

	@JsonProperty("id")
	String id;

	@JsonProperty("name")
	String name;

	@JsonProperty("tag")
	String tag;

	@JsonProperty("emblem")
	GuildEmblem emblem;

	@JsonProperty("level")
	Integer level;

	@JsonProperty("motd")
	String motd;

	@JsonProperty("influence")
	Integer influence;

	@JsonProperty("aetherium")
	Integer aetherium;

	@JsonProperty("favor")
	Integer favor;

	@JsonProperty("member_count")
	Integer memberCount;

	@JsonProperty("member_capacity")
	Integer memberCapacity;
}
