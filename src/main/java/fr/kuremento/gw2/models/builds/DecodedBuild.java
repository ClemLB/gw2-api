package fr.kuremento.gw2.models.builds;

import java.util.List;

public record DecodedBuild(ProfessionId profession, List<SpecLine> specLines, List<Integer> terrestrialSkillPaletteIds) {
}
