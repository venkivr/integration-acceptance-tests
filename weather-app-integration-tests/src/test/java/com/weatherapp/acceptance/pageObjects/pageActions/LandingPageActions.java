package com.weatherapp.acceptance.pageObjects.pageActions;

import com.weatherapp.acceptance.pageObjects.pages.*;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


@Component
public class LandingPageActions extends PageActions {

    private CucumberWebDriver webDriver;
    private LandingPage landingPage;

    public LandingPageActions(CucumberWebDriver webDriver, LandingPage shopLandingPage) {
        this.webDriver = webDriver;
        this.landingPage = shopLandingPage;
    }

    public void waitForPageToLoad() {
        landingPage.waitForPageToLoad();
    }

    public String getCityNameDisplayed() {
        return landingPage.getCityNameInput().getAttribute("value");
    }

    public Integer getSummaryRowCount() {
        return landingPage.getSummaryRows().size();
    }

    public boolean select_a_day(int day) {
        try {
            landingPage.getSummaryRows().get(day - 1).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDetailsExpanded(int whichDay) {
        try {
            return landingPage.areDetailsExpandedForDay(whichDay - 1);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMostDominantConditionDisplayed(int whichDay) {
        boolean isMostDominantDisplayed = false;
        try {
            List<String> conditions = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("description")).collect(Collectors.toList());
            String mostDominant = getMostDominant(conditions);
            String conditionInSummary = landingPage.getSummaryForDay(whichDay).get("description");
            if (mostDominant.equalsIgnoreCase(conditionInSummary)) {
                isMostDominantDisplayed = true;
            }
        } catch (Exception e) {
            isMostDominantDisplayed = false;
        }
        return isMostDominantDisplayed;
    }

    public boolean isMostDominantSpeedDisplayed(int whichDay) {
        boolean isMostDominantDisplayed = false;
        try {
            List<String> speeds = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("speed")).collect(Collectors.toList());
            String mostDominantSpeed = getMostDominant(speeds);
            String speedInSummary = landingPage.getSummaryForDay(whichDay).get("speed");
            if (mostDominantSpeed.equalsIgnoreCase(speedInSummary)) {
                isMostDominantDisplayed = true;
            }
        } catch (Exception e) {
            isMostDominantDisplayed = false;
        }
        return isMostDominantDisplayed;
    }

    public boolean isMostDominantDirectionDisplayed(int whichDay) {
        boolean isMostDominantDisplayed = false;
        try {
            List<String> conditions = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("direction")).collect(Collectors.toList());
            String mostDominant = getMostDominant(conditions);
            String conditionInSummary = landingPage.getSummaryForDay(whichDay).get("direction");
            if (mostDominant.equalsIgnoreCase(conditionInSummary)) {
                isMostDominantDisplayed = true;
            }
        } catch (Exception e) {
            isMostDominantDisplayed = false;
        }
        return isMostDominantDisplayed;
    }

    public boolean isMinimumTempDisplayed(int whichDay) {
        boolean isCorrectTempDisplayed = false;
        try {
            List<String> conditions = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("temperature-min")).collect(Collectors.toList());
            Integer minimumTemp = getSmallest(conditions);
            String minTempInSummary = landingPage.getSummaryForDay(whichDay).get("temperature-min");
            if (minimumTemp == Integer.valueOf(minTempInSummary)) {
                isCorrectTempDisplayed = true;
            }
        } catch (Exception e) {
            isCorrectTempDisplayed = false;
        }
        return isCorrectTempDisplayed;
    }

    public boolean isMaximumTempDisplayed(int whichDay) {
        boolean isCorrectTempDisplayed = false;
        try {
            List<String> maxTemps = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("temperature-max")).collect(Collectors.toList());
            Integer maxTemp = getLargest(maxTemps);
            String maxTempInSummary = landingPage.getSummaryForDay(whichDay).get("temperature-max");
            if (maxTemp == Integer.valueOf(maxTempInSummary)) {
                isCorrectTempDisplayed = true;
            }
        } catch (Exception e) {
            isCorrectTempDisplayed = false;
        }
        return isCorrectTempDisplayed;
    }

    public boolean isAggregateDisplayed(int whichDay) {
        boolean isCorrectTempDisplayed = false;
        try {
            List<String> conditions = landingPage.getDetailsForDay(whichDay).stream().map(row -> row.get("rainfall")).collect(Collectors.toList());
            Integer aggregateRainfall = getSum(conditions);
            String aggRainFallInSummary = landingPage.getSummaryForDay(whichDay).get("rainfall");
            if (aggregateRainfall == Integer.valueOf(aggRainFallInSummary)) {
                isCorrectTempDisplayed = true;
            }
        } catch (Exception e) {
            isCorrectTempDisplayed = false;
        }
        return isCorrectTempDisplayed;
    }

    public String getMostDominant(List<String> list) {
        return list.stream()
                .reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(list, o)))).orElse(null);
    }

    public Integer getSmallest(List<String> list) {
        List<Integer> intList = convertStringToIntList(list);
        return intList.stream()
                .min(Comparator.comparing(Integer::valueOf))
                .get();
    }

    public Integer getLargest(List<String> list) {
        List<Integer> intList = convertStringToIntList(list);
        return intList.stream()
                .max(Comparator.comparing(Integer::valueOf))
                .get();
    }

    public Integer getSum(List<String> list) {
        List<Integer> intList = convertStringToIntList(list);
        return intList.stream().mapToInt(i -> i.intValue()).sum();
    }

    public List<Integer> convertStringToIntList(List<String> stringList) {
        return stringList.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());
    }

    public boolean isForecastGap(int gap, int day) {
        List<String> hours = landingPage.getDetailsForDay(day).stream().map(row -> row.get("hour")).collect(Collectors.toList());
        for (int i = hours.size() - 1; i > 0; i--) {
            if (gap * 100 == Integer.valueOf(hours.get(i)) - Integer.valueOf(hours.get(i - 1))) {
                //Do Nothing;
            } else {
                return false;
            }
        }
        return true;
    }

}
