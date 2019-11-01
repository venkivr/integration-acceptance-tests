package com.weatherapp.acceptance.webdriver.drivers;

import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;

import java.io.File;

public interface CucumberWebDriver extends WebDriver {

    void takeScreenShot(Scenario scenario, String linkText);

    File getScreenshot();

    String getBrowserName();

    String getBrowserVersion();

    String getPlatform();

    String getPlatformVersion();

    void shutdown();

    /**
     * Captures screenshot to set directory for FT reporting purposes
     */
    void captureScreenshot();
}
