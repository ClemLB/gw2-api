package fr.kuremento.gw2.web.rest.models.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import fr.kuremento.gw2.web.rest.models.items.details.Armor;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@JsonTypeInfo(use = Id.DEDUCTION)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonSubTypes({@Type(value = Armor.class, name = "armor")})
public abstract class ItemDetails {

}
