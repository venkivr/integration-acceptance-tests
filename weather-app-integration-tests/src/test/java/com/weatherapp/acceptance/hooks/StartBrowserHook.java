package com.weatherapp.acceptance.hooks;

import com.weatherapp.acceptance.helpers.BrowserTasks;
import com.weatherapp.acceptance.state.RuntimeState;
import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.logging.Level;
import java.util.stream.Collectors;

public class StartBrowserHook {


    ThreadLocal<Boolean> initialised = ThreadLocal.withInitial(() -> false);

    private RuntimeState runtimeState;
    private BrowserTasks browserTasks;

    private Logger LOG = LoggerFactory.getLogger(StartBrowserHook.class);

    public StartBrowserHook(RuntimeState runtimeState, BrowserTasks browserTasks) {
        LOG.debug("Creating new StartBrowserHook instance");

        this.runtimeState = runtimeState;
        this.browserTasks = browserTasks;
    }

    @Before
    public void before(final Scenario scenario) {
        LOG.debug("Running before on scenario \"{}\" for thread \"{}\"", scenario.getName(), Thread.currentThread().getName());

        if (!initialised.get()) {
            initialised.set(true);
            LOG.debug("Running the initialisation block");

            Runtime.getRuntime().addShutdownHook(new ShutDownBrowserThread(runtimeState.getWebDriver()));
        }
        runtimeState.setScenario(scenario);
    }

    @After
    public void after(final Scenario scenario) {
        LOG.debug("Running after on scenario \"{}\" for thread \"{}\"", scenario.getName(), Thread.currentThread().getName());
        runtimeState.getWebDriver().manage().deleteAllCookies();
        takeScreenshotOnFailedScenario(scenario);
        printConsoleLogsForFailedScenario(scenario);
        printScnearioVariablesForFailedScenario(scenario);
    }

    private void takeScreenshotOnFailedScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            LOG.info("Test failed. Taking screenshot to help debug");
            browserTasks.takeScreenShot(scenario,scenario.getName());
        }
    }

    private void printConsoleLogsForFailedScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            LOG.info("Test failed. Printing console logs to help debug");
            scenario.write(printConsoleLogs().toString());
        }
    }

    private void printScnearioVariablesForFailedScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String scnVariables = runtimeState.getAllScnarioVariables().entrySet().stream().map(Object::toString).collect(Collectors.joining("\n"));
            LOG.info("Scenario variables : " + scnVariables);
            scenario.write(scnVariables);
        }
    }

    private StringBuilder printConsoleLogs() {
        LogEntries logEntries = runtimeState.getWebDriver().manage().logs().get(LogType.BROWSER);

        StringBuilder logBuilder = new StringBuilder();
        if (logEntries != null) {
            for (LogEntry logEntry : logEntries) {
                if (logEntry.getLevel().equals(Level.SEVERE) && !logEntry.getMessage().contains("herokuapp") && !logEntry.getMessage().contains("analytics") && !logEntry.getMessage().contains("InviteTriggers")) {      //herokuapp always fails in test
                    logBuilder.append("Console Error :" + logEntry.getMessage() + "\n");
                }
            }
            LOG.error(logBuilder.toString());
        }
        return logBuilder;
    }

    class ShutDownBrowserThread extends Thread {

        private CucumberWebDriver webDriver;

        public ShutDownBrowserThread(CucumberWebDriver webDriver) {
            this.webDriver = webDriver;
        }

        @Override
        public void run() {
            LOG.debug("Shutting down the browser");

            if (webDriver != null) {
                webDriver.quit();
                webDriver.shutdown();
            }
        }
    }
}
