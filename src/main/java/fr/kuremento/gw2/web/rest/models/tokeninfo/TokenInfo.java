package fr.kuremento.gw2.web.rest.models.tokeninfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kuremento.gw2.web.rest.models.tokeninfo.enums.TokenPermission;
import fr.kuremento.gw2.web.rest.models.tokeninfo.enums.TokenType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenInfo {

	@JsonProperty("id")
	String id;

	@JsonProperty("name")
	String name;

	@JsonProperty("permissions")
	List<TokenPermission> permissions = new ArrayList<>();

	@JsonProperty("type")
	TokenType type;

	@JsonProperty("expires_at")
	String expiresAt;

	@JsonProperty("issued_at")
	String issuedAt;

	@JsonProperty("urls")
	List<String> urls = new ArrayList<>();
}
