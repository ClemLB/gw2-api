package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.builds.*;
import fr.kuremento.gw2.web.rest.models.professions.Profession;
import fr.kuremento.gw2.web.rest.models.skills.Skill;
import fr.kuremento.gw2.web.rest.models.specializations.Specialization;
import fr.kuremento.gw2.web.rest.models.traits.Trait;
import fr.kuremento.gw2.web.rest.services.professions.ProfessionsService;
import fr.kuremento.gw2.web.rest.services.skills.SkillsService;
import fr.kuremento.gw2.web.rest.services.specializations.SpecializationsService;
import fr.kuremento.gw2.web.rest.services.traits.TraitsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildResolverService {

	private final ProfessionsService professionsService;
	private final SkillsService skillsService;
	private final SpecializationsService specializationsService;
	private final TraitsService traitsService;

	public ResolvedBuild resolve(DecodedBuild decoded) {
		try {
			Profession profession = professionsService.get(decoded.profession().getApiName());

			Map<Integer, Integer> paletteToSkill = profession.getSkillsByPalette().stream()
					.filter(pair -> pair.size() == 2)
					.collect(Collectors.toMap(pair -> pair.get(0), pair -> pair.get(1), (a, b) -> a));

			List<Integer> skillIds = decoded.terrestrialSkillPaletteIds().stream()
					.filter(paletteId -> paletteId != 0)
					.map(paletteId -> paletteToSkill.getOrDefault(paletteId, paletteId))
					.filter(skillId -> skillId != 0)
					.toList();

			List<Skill> skills = skillIds.isEmpty() ? List.of() : skillsService.get(skillIds);

			List<ResolvedSpecLine> resolvedSpecLines = new ArrayList<>();
			for (SpecLine specLine : decoded.specLines()) {
				if (specLine.specializationId() == 0) {
					continue;
				}

				Specialization spec = specializationsService.get(specLine.specializationId());

				List<Trait> minorTraits = traitsService.get(spec.getMinorTraits());
				List<Trait> majorTraits = traitsService.get(spec.getMajorTraits());

				int[] selectedMajorIndices = new int[]{
						specLine.traitChoice1(),
						specLine.traitChoice2(),
						specLine.traitChoice3()
				};

				resolvedSpecLines.add(new ResolvedSpecLine(spec, minorTraits, majorTraits, selectedMajorIndices));
			}

			return new ResolvedBuild(decoded.profession(), resolvedSpecLines, skills);
		} catch (TooManyArgumentsException e) {
			throw new TechnicalException("Erreur lors de la résolution du build : " + e.getMessage());
		}
	}
}
