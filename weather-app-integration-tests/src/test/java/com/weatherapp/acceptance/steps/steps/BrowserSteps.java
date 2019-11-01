package com.weatherapp.acceptance.steps.steps;

import com.weatherapp.acceptance.helpers.BrowserTasks;
import cucumber.api.java.en.Then;

public class BrowserSteps extends PageSteps {

    private BrowserTasks browserTasks;

    public BrowserSteps(BrowserTasks browserTasks) {
        this.browserTasks = browserTasks;
    }

    @Then("^take screenshot$")
    public void take_screenshot() {
        browserTasks.takeScreenShot(runtimeState.getScenario(), "Screenshot");
    }
}
