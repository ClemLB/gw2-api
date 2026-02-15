package fr.kuremento.gw2.services.builds;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.models.builds.DecodedBuild;
import fr.kuremento.gw2.models.builds.ProfessionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildChatCodeDecoderTest {

	private final BuildChatCodeDecoder decoder = new BuildChatCodeDecoder();

	@Test
	@DisplayName("Decode a valid Necromancer build chat code")
	void test1() {
		// [&DQg1OTIlTCsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=] = Necromancer chat code example
		String chatCode = "[&DQg1OTIlTCsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=]";
		DecodedBuild build = decoder.decode(chatCode);

		assertNotNull(build, "Decoded build should not be null");
		assertEquals(ProfessionId.NECROMANCER, build.profession(), "Profession should be Necromancer");
		assertEquals(3, build.specLines().size(), "Should have 3 spec lines");
		assertEquals(5, build.terrestrialSkillPaletteIds().size(), "Should have 5 terrestrial skill palette IDs");
	}

	@Test
	@DisplayName("Decode extracts spec IDs correctly")
	void test2() {
		String chatCode = "[&DQg1OTIlTCsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=]";
		DecodedBuild build = decoder.decode(chatCode);

		assertEquals(53, build.specLines().get(0).specializationId(), "First spec ID should be 53");
		assertEquals(50, build.specLines().get(1).specializationId(), "Second spec ID should be 50");
	}

	@Test
	@DisplayName("Reject invalid header")
	void test3() {
		// Create a base64 string with wrong header byte
		String invalidChatCode = "[&AAg1OTIlTCsAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=]";
		assertThrows(TechnicalException.class, () -> decoder.decode(invalidChatCode), "Should throw on invalid header");
	}

	@Test
	@DisplayName("Reject too short chat code")
	void test4() {
		String shortChatCode = "[&DQg=]";
		assertThrows(TechnicalException.class, () -> decoder.decode(shortChatCode), "Should throw on too short chat code");
	}
}
