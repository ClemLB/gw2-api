package fr.kuremento.gw2.models.raids;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum RaidBountySlot {

	BOSS_1(6, List.of(
			"Col des Cimefroides",
			"Voix et Griffe des déchus",
			"Fraenir de Jormag",
			"Gorseval le Disparate",
			"Cairn l'Indomptable",
			"Surveillant mursaat"
	)),
	BOSS_2(12, List.of(
			"Cachette des Étherlames",
			"Cardinal Sabir",
			"Murmure de Jormag",
			"Gardien de la Vallée",
			"Observatoire cosmique",
			"Guerre froide",
			"Désosseur",
			"Sabetha la saboteuse",
			"Décharge de Xunlai Jade",
			"Temple de Febe",
			"Titan du fort",
			"Kela, sénéchal des vagues"
	)),
	BOSS_3(12, List.of(
			"Paressor",
			"Matthias Gabrel",
			"Xera",
			"Samarog",
			"Amalgame conjuré",
			"Jumeaux largos",
			"Decima, l'antienne de la tempête",
			"Cardinale Adina",
			"Cour du vieux Lion",
			"Ura, la vapeur fulminante",
			"Belvédère de Kaineng",
			"Deimos"
	)),
	BOSS_4(6, List.of(
			"Qadim",
			"Qadim l'Inégalé",
			"Horreur sans âme",
			"Temple des moissons",
			"Dhuum",
			"Greer, le porte-fléau"
	));

	private final int cycleLength;
	private final List<String> encounters;

}
