package com.weatherapp.acceptance.state;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import cucumber.api.Scenario;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class RuntimeState {

    private CucumberWebDriver webDriver;
    private Scenario scenario;
    public HashMap<String, String> scenarioVariables = new HashMap<>();

    public RuntimeState(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public CucumberWebDriver getWebDriver() {
        return webDriver;
    }

    public Scenario getScenario() {
        if (scenario == null) {
            throw new RuntimeException("Could not retrieve Cucumber Scenario. Please ensure that a Cucumber Scenario is injected at the start of each test run.");
        }
        return scenario;
    }

    public void setScenario(Scenario inputScenario) {
        scenario = inputScenario;
    }

    public void addVariable(String key, String val) {
        scenarioVariables.put(key, val);
    }

    public Map<String, String> getAllScnarioVariables() {
        return scenarioVariables;
    }


}
