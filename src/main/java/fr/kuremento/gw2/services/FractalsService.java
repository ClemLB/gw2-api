package fr.kuremento.gw2.services;

import fr.kuremento.gw2.exceptions.TechnicalException;
import fr.kuremento.gw2.exceptions.TooManyArgumentsException;
import fr.kuremento.gw2.models.fractals.*;
import fr.kuremento.gw2.web.rest.models.achievements.Achievements;
import fr.kuremento.gw2.web.rest.models.achievements.categories.AchievementInCategory;
import fr.kuremento.gw2.web.rest.services.achievements.AchievementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FractalsService {

    private final JsonInstabilities jsonInstabilities;
    private final JsonFractal jsonFractal;
    private final AchievementsService achievementsService;

    public List<DailyFractal> dailies() {
        return this.getFractals(jsonFractal.rotation()[this.getRotation()]);
    }

    public List<DailyFractal> cms() {
        int[] cmIds = {23, 8, 9, 20, 21, 22};
        return this.getFractals(cmIds);
    }

    private List<DailyFractal> getFractals(int[] fractalsNumber) {
        List<DailyFractal> fractals = new ArrayList<>(3);
        for (int numFractal : fractalsNumber) {
            Fractal fractal = jsonFractal.fractals()[numFractal];
            int[] instaNumbers = jsonInstabilities.instabilities().get(String.valueOf(fractal.level()))[this.getRotation()];
            List<String> instabilities = Arrays.stream(instaNumbers).mapToObj(i -> jsonInstabilities.instability_details()[i].name().get("fr")).toList();
            fractals.add(new DailyFractal(fractal.level(), FractalEnum.getFractalFromLevel(fractal.level()).getName(), instabilities));
        }
        return fractals.stream().sorted(Comparator.comparingInt(DailyFractal::level)).toList();
    }

    public List<DailyFractal> recs() {
        List<Integer> fractalsAchievement = achievementsService.categories().get(88).getAchievements().stream().map(AchievementInCategory::getId).toList();
        try {
            List<Achievements> achievements = achievementsService.get(fractalsAchievement);
            return achievements.stream()
                               .map(Achievements::getName)
                               .map(s -> StringUtils.remove(s, "\u00A0"))
                               .filter(s -> StringUtils.contains(s, "Fractale quotidienne recommandée"))
                               .map(s -> StringUtils.remove(s, "Fractale quotidienne recommandée"))
                               .map(s -> StringUtils.remove(s, "de niveau"))
                               .map(StringUtils::strip)
                               .map(s -> new DailyFractal(Integer.parseInt(s), FractalEnum.getFractalFromLevel(Integer.parseInt(s)).getName(),
                                                          Collections.emptyList()))
                               .sorted(Comparator.comparingInt(DailyFractal::level))
                               .toList();
        } catch (TooManyArgumentsException e) {
            throw new TechnicalException(e);
        }
    }

    private int getRotation() {
        LocalDate currentRotation = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        return Math.toIntExact(ChronoUnit.DAYS.between(currentRotation, LocalDate.now())) % 15;
    }
}