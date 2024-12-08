package fr.kuremento.gw2.models.fractals;

import java.util.Map;

public record JsonInstabilities(Map<String, int[][]> instabilities, String[] instability_names) {
}
