package fr.kuremento.gw2.models.builds;

import java.util.Arrays;

public enum ProfessionId {

	GUARDIAN(1, "Guardian"),
	WARRIOR(2, "Warrior"),
	ENGINEER(3, "Engineer"),
	RANGER(4, "Ranger"),
	THIEF(5, "Thief"),
	ELEMENTALIST(6, "Elementalist"),
	MESMER(7, "Mesmer"),
	NECROMANCER(8, "Necromancer"),
	REVENANT(9, "Revenant");

	private final int id;
	private final String apiName;

	ProfessionId(int id, String apiName) {
		this.id = id;
		this.apiName = apiName;
	}

	public int getId() {
		return id;
	}

	public String getApiName() {
		return apiName;
	}

	public static ProfessionId fromId(int id) {
		return Arrays.stream(values())
					 .filter(p -> p.id == id)
					 .findFirst()
					 .orElseThrow(() -> new IllegalArgumentException("Unknown profession id: " + id));
	}
}
