package com.weatherapp.acceptance.steps.steps;

import com.weatherapp.acceptance.helpers.NavigationTasks;
import com.weatherapp.acceptance.helpers.URLProperties;
import com.weatherapp.acceptance.pageObjects.pageActions.LandingPageActions;
import com.weatherapp.acceptance.state.RuntimeState;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LandingPageSteps extends PageSteps {

    private NavigationTasks navigationTasks;
    private LandingPageActions landingPageActions;
    private URLProperties urlProperties;

    @Autowired
    RuntimeState runtimeState;


    public LandingPageSteps(NavigationTasks navigationTasks, LandingPageActions landingPageActions, URLProperties urlProperties) {
        this.navigationTasks = navigationTasks;
        this.landingPageActions = landingPageActions;
        this.urlProperties = urlProperties;
    }

    @Given("I launch weather app$")
    public void i_launch_weather_app() {
        navigationTasks.goToURL(urlProperties.getBaseUrl());
        landingPageActions.waitForPageToLoad();
        String currentURL = utilMethods.getCurrentURL();
        assertTrue("Expected to be on Weather app landing page but landed on " + currentURL, currentURL.endsWith("3000/"));
    }

    @Given("I launch weather app for {string}")
    public void i_launch_weather_app_for_city(String cityName) {
        navigationTasks.goToURLForCity(urlProperties.getBaseUrl(), cityName);
        landingPageActions.waitForPageToLoad();
        String currentURL = utilMethods.getCurrentURL();
        assertTrue("Expected to be on Weather app landing page but landed on " + currentURL, currentURL.endsWith(cityName));
    }

    @Then("city name {string} is displayed on header")
    public void city_name_displayed(String cityName) {
        String cityNameFound = landingPageActions.getCityNameDisplayed();
        assertTrue("Expected city name to be displayed as " + cityName + " but found " + cityNameFound, cityNameFound.equalsIgnoreCase(cityName));
    }

    @Then("{int} days of forecast should be displayed")
    public void no_of_days_forecast(int noOfdays) {
        int forecastedDays = landingPageActions.getSummaryRowCount();
        assertTrue("Expected number of days " + noOfdays + " but found " + forecastedDays, forecastedDays == noOfdays);
    }

    @Then("I click forecast for day {int}")
    public void select_day(int noOfdays) {
        assertTrue("Unable to expand forecast for day " + noOfdays, landingPageActions.select_a_day(noOfdays));
        runtimeState.addVariable("daySelected", String.valueOf(noOfdays));
    }

    @Then("details section is expanded for day {int}")
    public void details_section_expanded(int noOfdays) {
        assertTrue("Details section is not expanded for day " + noOfdays, landingPageActions.isDetailsExpanded(noOfdays));
    }

    @Then("most dominant weather condition is displayed in summary for day {int}")
    public void most_dominant_condition(int whichDay) {
        assertTrue("Most dominant condition NOT displayed in summary for day " + whichDay, landingPageActions.isMostDominantConditionDisplayed(whichDay));
    }

    @Then("most dominant wind speed is displayed in summary for day {int}")
    public void most_dominant_speed(int whichDay) {
        assertTrue("Most dominant soeed NOT displayed in summary for day " + whichDay, landingPageActions.isMostDominantSpeedDisplayed(whichDay));
    }

    @Then("most dominant wind direction is displayed in summary for day {int}")
    public void most_dominant_direction(int whichDay) {
        assertTrue("Most dominant direction NOT displayed in summary for day " + whichDay, landingPageActions.isMostDominantDirectionDisplayed(whichDay));
    }

    @Then("minimum temperature is displayed correctly in summary for day {int}")
    public void minimum_temp_displayed_correctly(int whichDay) {
        assertTrue("Miniumum temperature is NOT correctly displayed in summary for day " + whichDay, landingPageActions.isMinimumTempDisplayed(whichDay));
    }

    @Then("maximum temperature is displayed correctly in summary for day {int}")
    public void maximum_temp_displayed_correctly(int whichDay) {
        assertTrue("Maximum temperature is NOT correctly displayed in summary for day " + whichDay, landingPageActions.isMaximumTempDisplayed(whichDay));
    }

    @Then("aggregate rainfall is displayed correctly for day {int}")
    public void aggregate_rainfall_displayed(int whichDay) {
        assertTrue("Maximum temperature is NOT correctly displayed in summary for day " + whichDay, landingPageActions.isAggregateDisplayed(whichDay));
    }

    @Then("details section is closed for day {int}")
    public void details_section_expandedclosed(int noOfdays) {
        assertFalse("Details section is not expanded for day " + noOfdays, landingPageActions.isDetailsExpanded(noOfdays));
    }


    @Then("hourly forecast is displayed for day {int}")
    public void hourly_forecast_for_day(int whichDay) {
        assertTrue("Forecast for day is not displayed " + whichDay, landingPageActions.isDetailsExpanded(whichDay));
    }


    @Then("{int} hourly forecast is displayed for day {int}")
    public void check_is_forecast_hourly(int forecastGap, int forDay) {
        assertTrue("Forecast is not " + forecastGap + " hourly", landingPageActions.isForecastGap(forecastGap, forDay));
    }
}