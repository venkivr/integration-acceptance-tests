package com.weatherapp.acceptance.helpers;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import cucumber.api.Scenario;
import org.springframework.stereotype.Component;

@Component
public class BrowserTasks {

    private CucumberWebDriver webDriver;

    public BrowserTasks(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void takeScreenShot(Scenario scenario, String description) {
        webDriver.takeScreenShot(scenario, description);
    }
}
