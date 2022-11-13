package fr.kuremento.gw2.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class EnumUtil {

	private static final String STRING_TO_TRANSFORM = """
			AgonyResistance – Agony Resistance
			BoonDuration – Concentration
			ConditionDamage – Condition Damage
			ConditionDuration – Expertise
			CritDamage – Ferocity
			Healing – Healing Power
			Power – Power
			Precision – Precision
			Toughness – Toughness
			Vitality – Vitality
			""";

	public static void main(String[] args) {
		stringToEnum(STRING_TO_TRANSFORM);
	}

	private static void stringToEnum(String stringToTransform) {
		String result = Arrays.stream(stringToTransform.split("\n"))
		                      .map(EnumUtil::deleteWikiDescription)
		                      .map(s -> camelToSnake(s).concat("(\"").concat(s).concat("\")"))
		                      .collect(Collectors.joining(",\n", "", ";"));
		setAsText(result);
		log.info("Enum stocked in clipboard : \n{}", result);
	}

	private static String camelToSnake(String str) {
		String regexCamelCase = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";

		return str.replaceAll(regexCamelCase, replacement).toUpperCase();
	}

	private static String deleteWikiDescription(String str) {
		String regexWikiDescription = "(?= )(.*)(?=\\r?)";
		str = str.trim();
		return str.replaceAll(regexWikiDescription, "");
	}

	/**
	 * Copier du texte vers le presse-papier
	 *
	 * @param text String texte à copier
	 */
	private static void setAsText(String text) {
		if (text != null) {
			StringSelection contents = new StringSelection(text);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(contents, null);
		}
	}
}
