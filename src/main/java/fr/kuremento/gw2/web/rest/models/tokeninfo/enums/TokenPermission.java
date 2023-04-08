package fr.kuremento.gw2.web.rest.models.tokeninfo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TokenPermission {
    ACCOUNT("account"),
    BUILDS("builds"),
    CHARACTERS("characters"),
    GUILDS("guilds"),
    INVENTORIES("inventories"),
    PROGRESSION("progression"),
    PVP("pvp"),
    TRADINGPOST("tradingpost"),
    UNLOCKS("unlocks"),
    WALLET("wallet");

    private final String value;

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
