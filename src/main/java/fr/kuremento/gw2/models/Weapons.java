package fr.kuremento.gw2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Weapons {

	AXE("Axe"), DAGGER("Dagger"), FOCUS("Focus"), GREATSWORD("Greatsword"), HAMMER("Hammer"), HARPOON("Harpoon"), LONG_BOW("LongBow"), MACE("Mace"), PISTOL("Pistol"), RIFLE(
			"Rifle"), SCEPTER(
			"Scepter"), SHIELD("Shield"), SHORT_BOW("ShortBow"), SPEARGUN("Speargun"), STAFF("Staff"), SWORD("Sword"), TORCH("Torch"), TRIDENT("Trident"), WARHORN("Warhorn");

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
