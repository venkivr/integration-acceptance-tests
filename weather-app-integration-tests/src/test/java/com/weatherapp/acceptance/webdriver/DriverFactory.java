package com.weatherapp.acceptance.webdriver;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import com.weatherapp.acceptance.webdriver.environments.ChromeRuntimeEnvironment;
import com.weatherapp.acceptance.webdriver.environments.FirefoxRuntimeEnvironment;
import com.weatherapp.acceptance.webdriver.environments.RuntimeEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Component
public class DriverFactory {
    private static final String WEB_DRIVER_BROWSER_PROPERTY = "web.driver.browser";
    private static final int WAIT = 120;
    private static final int IMPLICIT_WAIT = 5;
    private static final String FIREFOX = "FIREFOX";
    private static final String CHROME = "CHROME";

    public static CucumberWebDriver getInstance(Environment environment) {

        RuntimeEnvironment runtimeEnvironment;
        String browser = System.getProperty(WEB_DRIVER_BROWSER_PROPERTY, CHROME);

        switch (browser.toUpperCase()) {
            case CHROME:
                runtimeEnvironment = new ChromeRuntimeEnvironment(environment);
                break;
            case FIREFOX:
                runtimeEnvironment = new FirefoxRuntimeEnvironment(environment);
                break;
            default:
                throw new RuntimeException(format("Unable to determine which browser to load. Property '%s' set to '%s'", WEB_DRIVER_BROWSER_PROPERTY, browser));
        }

        runtimeEnvironment.initialise();
        CucumberWebDriver webDriver = runtimeEnvironment.createWebDriver();
        webDriver.manage().timeouts().pageLoadTimeout(WAIT, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        return webDriver;
    }
}
