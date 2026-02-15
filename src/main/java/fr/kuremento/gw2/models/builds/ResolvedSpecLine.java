package fr.kuremento.gw2.models.builds;

import fr.kuremento.gw2.web.rest.models.specializations.Specialization;
import fr.kuremento.gw2.web.rest.models.traits.Trait;

import java.util.List;

public record ResolvedSpecLine(Specialization specialization, List<Trait> minorTraits, List<Trait> majorTraits,
							   int[] selectedMajorIndices) {
}
