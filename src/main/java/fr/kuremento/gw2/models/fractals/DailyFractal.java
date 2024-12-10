package fr.kuremento.gw2.models.fractals;

import java.io.Serializable;
import java.util.List;

public record DailyFractal(int level, String name, List<String> instabilities) implements Serializable {
}
