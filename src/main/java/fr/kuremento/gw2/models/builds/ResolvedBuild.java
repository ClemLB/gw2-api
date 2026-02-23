package fr.kuremento.gw2.models.builds;

import fr.kuremento.gw2.web.rest.models.skills.Skill;

import java.util.List;

public record ResolvedBuild(ProfessionId profession, List<ResolvedSpecLine> specLines, List<Skill> skills,
							ResolvedEquipment equipment) {

	public ResolvedBuild(ProfessionId profession, List<ResolvedSpecLine> specLines, List<Skill> skills) {
		this(profession, specLines, skills, null);
	}
}
