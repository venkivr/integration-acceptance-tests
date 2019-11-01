package com.weatherapp.acceptance.webdriver.environments;

import com.weatherapp.acceptance.helpers.OSDetector;
import com.weatherapp.acceptance.webdriver.drivers.ChromeCucumberWebDriver;
import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import com.google.common.primitives.Ints;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ChromeRuntimeEnvironment extends RuntimeEnvironment {
    private static final Map<List<Integer>, String> CHROME_DRIVER_MAPPER = new LinkedHashMap<>();

    static {
        CHROME_DRIVER_MAPPER.put(Ints.asList(77), "77.0");
        CHROME_DRIVER_MAPPER.put(Ints.asList(78), "78.0");
        CHROME_DRIVER_MAPPER.put(Ints.asList(56), "2.29");
    }

    public ChromeRuntimeEnvironment(Environment environment) {
        super(environment);
    }

    @Override
    public void initialise() {
        setDriver();
    }

    @Override
    public CucumberWebDriver createWebDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        return new ChromeCucumberWebDriver(options);
    }

    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", getDriverPath("chrome", "chromedriver"));
    }

    @Override
    public String getDriverVersion() {

        Integer chromeMajorVersion = getLocallyInstalledVersion();

        for (List<Integer> majorVersions : CHROME_DRIVER_MAPPER.keySet()) {
            if (majorVersions.contains(chromeMajorVersion)) {
                return CHROME_DRIVER_MAPPER.get(majorVersions);
            }
        }

        throw new RuntimeException("Unable to find chrome driver version for major chrome version " + chromeMajorVersion);
    }

    @Override
    public Integer getLocallyInstalledVersion() {

        switch (OSDetector.getOSFamily()) {
            case OSDetector.LINUX:
                return runCommandForResult(
                        asList(
                                "google-chrome",
                                "-version")
                        , "(\\d*)\\."
                );
            case OSDetector.WINDOWS:
                return runCommandForResult(
                        asList(
                                "wmic",
                                "datafile",
                                "where",
                                "name='C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe'",
                                "get",
                                "Version",
                                "/value")
                        , "=(\\d*)\\."
                );
            case OSDetector.MAC:
                return runCommandForResult(
                        asList(
                                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
                                "--version")
                        , "(\\d*)\\."
                );
            default:
                throw new RuntimeException("Unsupported OS!");
        }
    }
}
