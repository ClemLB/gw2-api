package fr.kuremento.gw2.web.rest.models.achievements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AchievementFlag {
	PVP("Pvp"),
	CATEGORY_DISPLAY("CategoryDisplay"),
	MOVE_TO_TOP("MoveToTop"),
	IGNORE_NEARLY_COMPLETE("IgnoreNearlyComplete"),
	REPEATABLE("Repeatable"),
	HIDDEN("Hidden"),
	REQUIRES_UNLOCK("RequiresUnlock"),
	REPAIR_ON_LOGIN("RepairOnLogin"),
	DAILY("Daily"),
	WEEKLY("Weekly"),
	MONTHLY("Monthly"),
	PERMANENT("Permanent");

	private final String value;

	@JsonValue
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}
