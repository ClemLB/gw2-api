package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.builds.*;
import fr.kuremento.gw2.web.rest.models.professions.Profession;
import fr.kuremento.gw2.web.rest.models.skills.Skill;
import fr.kuremento.gw2.web.rest.models.specializations.Specialization;
import fr.kuremento.gw2.web.rest.models.traits.Trait;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildResolverService {

	private final Map<String, Profession> professionsReferential;
	private final Map<Integer, Skill> skillsReferential;
	private final Map<Integer, Specialization> specializationsReferential;
	private final Map<Integer, Trait> traitsReferential;

	public ResolvedBuild resolve(DecodedBuild decoded) {
		try {
			Profession profession = professionsReferential.get(decoded.profession().getApiName());
			if (profession == null) {
				throw new TechnicalException("Profession inconnue : " + decoded.profession().getApiName());
			}

			Map<Integer, Integer> paletteToSkill = profession.getSkillsByPalette().stream()
					.filter(pair -> pair.size() == 2)
					.collect(Collectors.toMap(pair -> pair.get(0), pair -> pair.get(1), (a, b) -> a));

			List<Integer> skillIds = decoded.terrestrialSkillPaletteIds().stream()
					.filter(paletteId -> paletteId != 0)
					.map(paletteId -> paletteToSkill.getOrDefault(paletteId, paletteId))
					.filter(skillId -> skillId != 0)
					.toList();

			List<Skill> skills = skillIds.stream()
					.map(skillsReferential::get)
					.filter(Objects::nonNull)
					.toList();

			List<ResolvedSpecLine> resolvedSpecLines = new ArrayList<>();
			for (SpecLine specLine : decoded.specLines()) {
				if (specLine.specializationId() == 0) {
					continue;
				}

				Specialization spec = specializationsReferential.get(specLine.specializationId());
				if (spec == null) {
					continue;
				}

				List<Trait> minorTraits = spec.getMinorTraits().stream()
						.map(traitsReferential::get)
						.filter(Objects::nonNull)
						.toList();
				List<Trait> majorTraits = spec.getMajorTraits().stream()
						.map(traitsReferential::get)
						.filter(Objects::nonNull)
						.toList();

				int[] selectedMajorIndices = new int[]{
						specLine.traitChoice1(),
						specLine.traitChoice2(),
						specLine.traitChoice3()
				};

				resolvedSpecLines.add(new ResolvedSpecLine(spec, minorTraits, majorTraits, selectedMajorIndices));
			}

			return new ResolvedBuild(decoded.profession(), resolvedSpecLines, skills);
		} catch (TechnicalException e) {
			throw e;
		} catch (Exception e) {
			throw new TechnicalException("Erreur lors de la résolution du build : " + e.getMessage());
		}
	}
}
