package fr.kuremento.gw2.models.fractals;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum FractalEnum {

    AETHERBLADE("Étherlame", List.of(14, 45, 65, 93)),
    AQUATIC_RUINS("Ruines aquatiques", List.of(7, 26, 61, 76)),
    CAPTAIN_MAI_TRIN("Boss capitaine Mai Trin", List.of(18, 42, 71, 91)),
    CHAOS_ISLES("Chaos", List.of(13, 30, 63, 88)),
    CLIFFSIDE("Flanc de falaise", List.of(6, 46, 68, 94)),
    DEEPSTONE("Roche des abysses", List.of(11, 33, 67, 84)),
    MOLTEN_BOSS("Boss de la Fusion", List.of(10, 40, 69, 90)),
    MOLTEN_FURNACE("Fournaise de la Fusion", List.of(9, 39, 58, 83)),
    NIGHTMARE("Cauchemars", List.of(22, 47, 72, 96)),
    OBSERVATORY("Observatoire détruit", List.of(23, 48, 73, 97)),
    SIRENS_REEF("Récif de la sirène", List.of(12, 37, 54, 78)),
    SNOWBLIND("Aveugleneige", List.of(3, 27, 51, 86)),
    SOLID_OCEAN("Océan solide", List.of(20, 35, 44, 60, 80)),
    SWAMPLAND("Marais", List.of(5, 32, 56, 77, 89)),
    THAUMANOVA_REACTOR("Réacteur de Thaumanova", List.of(15, 34, 55, 64, 82)),
    TWILIGHT_OASIS("Oasis du crépuscule", List.of(16, 41, 59, 87)),
    UNCATEGORIZED("Non classé", List.of(2, 36, 62, 79)),
    UNDERGROUND_FACILITY("Complexe souterrain", List.of(8, 29, 53, 81)),
    URBAN_BATTLEGROUND("Champ de bataille urbain", List.of(4, 31, 57, 85)),
    VOLCANIC("Volcanique", List.of(1, 19, 28, 52, 92)),
    SUNQUA_PEAK("Pic de Sunqua", List.of(24, 49, 74, 98)),
    SILENT_SURF("Ressac silencieux", List.of(21, 43, 66, 99)),
    LONELY_TOWER("Tour solitaire", List.of(25, 50, 75, 100)),
    KINFALL("Deuil du foyer", List.of(17, 38, 70, 95));

    private final String name;
    private final List<Integer> levels;

    public static FractalEnum getFractalFromLevel(final int level) {
        return Arrays.stream(FractalEnum.values()).filter(fractalEnum -> fractalEnum.getLevels().contains(level)).findFirst().orElseThrow();
    }

    public static Map<Integer, String> getAllFractals() {
        HashMap<Integer, String> fractals = new HashMap<>(100);
        Arrays.stream(FractalEnum.values()).forEach(fractalEnum -> fractalEnum.getLevels().forEach(level -> fractals.put(level, fractalEnum.getName())));
        return fractals;
    }
}
