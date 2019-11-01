package com.weatherapp.acceptance.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true
        , features = "classpath:features"
        , junit = { "--step-notifications"}
        , glue = {"com.weatherapp.acceptance.steps", "com.weatherapp.acceptance.hooks"}
        , tags = {"not @wip","@dailyForecast"}
        , plugin = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json"})
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class WeatherAppTestRunner {

}
