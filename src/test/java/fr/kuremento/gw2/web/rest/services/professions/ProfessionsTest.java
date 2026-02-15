package fr.kuremento.gw2.web.rest.services.professions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessionsTest {

	@Autowired
	private ProfessionsService service;

	@Test
	@DisplayName("Check number of professions")
	void test1() {
		assertFalse(service.get().isEmpty(), "Service should return a list of professions id");
	}

	@Test
	@DisplayName("Check request one profession")
	void test2() {
		var profession = service.get("Necromancer");
		assertNotNull(profession, "Requested profession should not be null");
		assertNotNull(profession.getName(), "Profession name should not be null");
	}

	@Test
	@DisplayName("Check profession has skills_by_palette")
	void test3() {
		var profession = service.get("Necromancer");
		assertNotNull(profession.getSkillsByPalette(), "skills_by_palette should not be null");
		assertFalse(profession.getSkillsByPalette().isEmpty(), "skills_by_palette should not be empty");
	}

	@Test
	@DisplayName("Check getAll request")
	void test4() {
		assertFalse(service.getAll().isEmpty(), "Service should return a list of professions");
	}
}
