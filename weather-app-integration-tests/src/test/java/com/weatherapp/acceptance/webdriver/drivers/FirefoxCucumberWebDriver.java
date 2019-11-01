package com.weatherapp.acceptance.webdriver.drivers;

import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FirefoxCucumberWebDriver extends FirefoxDriver implements CucumberWebDriver {

    private static final int IS_500 = 500;
    private static final int IS_20 = 20;
    private static final String IMAGE_PNG = "image/png";
    private static final String AUTOMATION_CAPTURE_PATH = "/automation/screenshots/capture.png";
    private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxCucumberWebDriver.class.getName());

    private WebDriverException cause;

    public FirefoxCucumberWebDriver(FirefoxOptions options) {
        super(options);
    }

    public void takeScreenShot(Scenario scenario, String linkText) {

        int count = 0;

        while (!snap(scenario, linkText)) {

            count++;
            if (count > IS_20) {

                LOGGER.info("Could not take screenshot", this.cause);

                break;
            }

            try {
                Thread.sleep(IS_500);
            } catch (InterruptedException e) {
                LOGGER.info("Waiting for page to load before attempting another screenshot.");
            }

        }

    }

    private boolean snap(Scenario scenario, String linkText) {

        boolean isScreenShotTaken = false;

        try {

            final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
            scenario.write(linkText);
            scenario.embed(screenshot, IMAGE_PNG);
            isScreenShotTaken = true;

        } catch (WebDriverException cause) {

            this.cause = cause;

        }

        return isScreenShotTaken;

    }

    @Override
    public File getScreenshot() {
        return getScreenshotAs(OutputType.FILE);
    }

    @Override
    public String getBrowserName() {
        return getCapabilities().getBrowserName();
    }

    @Override
    public String getBrowserVersion() {

        String browserVersion = getCapabilities().getVersion();

        if (browserVersion.contains(".")) {
            browserVersion = browserVersion.substring(0, browserVersion.indexOf("."));
        }
        return browserVersion;
    }

    @Override
    public String getPlatform() {
        return getCapabilities().getPlatform().family().name();
    }

    @Override
    public String getPlatformVersion() {
        return "" + getCapabilities().getPlatform().getMajorVersion();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void captureScreenshot() {
        boolean folderCreated = false;
        boolean folderExists = false;

        try {
            LOGGER.info("Attempting to capture screenshot");

            //Call getScreenshotAs method to create image file
            File screenshotFile = getScreenshotAs(OutputType.FILE);

            //Move image file to new destination
            File browserImgFile = new File(AUTOMATION_CAPTURE_PATH);

            if (browserImgFile.getParentFile() != null) {
                folderExists = browserImgFile.getParentFile().exists();
                if (!folderExists) {
                    folderCreated = browserImgFile.getParentFile().mkdirs();
                }
            }

            if (folderExists || folderCreated) {
                FileUtils.copyFile(screenshotFile, browserImgFile);
                LOGGER.info("Screenshot successfully captured at : {}", AUTOMATION_CAPTURE_PATH);
            } else {
                LOGGER.error("Failed to create folder");
            }
        } catch (IOException | WebDriverException e) {
            LOGGER.error("Failed to capture screenshot - " + e);
        }
    }
}