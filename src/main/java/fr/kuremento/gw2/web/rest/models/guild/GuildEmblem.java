package fr.kuremento.gw2.web.rest.models.guild;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.guild.enums.EmblemFlag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuildEmblem {

    @JsonProperty("background")
    GuildEmblemLayer background;

    @JsonProperty("foreground")
    GuildEmblemLayer foreground;

    @JsonProperty("flags")
    List<EmblemFlag> flags = new ArrayList<>();
}
