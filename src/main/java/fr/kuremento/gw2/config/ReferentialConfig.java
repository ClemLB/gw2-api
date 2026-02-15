package fr.kuremento.gw2.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kuremento.gw2.web.rest.models.professions.Profession;
import fr.kuremento.gw2.web.rest.models.skills.Skill;
import fr.kuremento.gw2.web.rest.models.specializations.Specialization;
import fr.kuremento.gw2.web.rest.models.traits.Trait;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class ReferentialConfig {

	private final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Bean
	public Map<Integer, Trait> traitsReferential(@Value("classpath:data/referential/traits.json") Resource resource) throws IOException {
		List<Trait> traits = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
		log.info("Référentiel traits chargé : {} éléments", traits.size());
		return traits.stream().collect(Collectors.toMap(Trait::getId, Function.identity()));
	}

	@Bean
	public Map<Integer, Skill> skillsReferential(@Value("classpath:data/referential/skills.json") Resource resource) throws IOException {
		List<Skill> skills = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
		log.info("Référentiel skills chargé : {} éléments", skills.size());
		return skills.stream().collect(Collectors.toMap(Skill::getId, Function.identity()));
	}

	@Bean
	public Map<Integer, Specialization> specializationsReferential(@Value("classpath:data/referential/specializations.json") Resource resource) throws IOException {
		List<Specialization> specializations = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
		log.info("Référentiel specializations chargé : {} éléments", specializations.size());
		return specializations.stream().collect(Collectors.toMap(Specialization::getId, Function.identity()));
	}

	@Bean
	public Map<String, Profession> professionsReferential(@Value("classpath:data/referential/professions.json") Resource resource) throws IOException {
		List<Profession> professions = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
		log.info("Référentiel professions chargé : {} éléments", professions.size());
		return professions.stream().collect(Collectors.toMap(Profession::getId, Function.identity()));
	}
}
