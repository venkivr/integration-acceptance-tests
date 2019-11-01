package com.weatherapp.acceptance.webdriver.drivers;

import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ChromeCucumberWebDriver extends ChromeDriver implements CucumberWebDriver {

    private static final String AUTOMATION_CAPTURE_PATH = "/automation/screenshots/capture.png";
    private static final String IMAGE_PNG = "image/png";
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeCucumberWebDriver.class.getName());

    public ChromeCucumberWebDriver(ChromeOptions options) {
        super(options);
    }

    public void takeScreenShot(Scenario scenario, String linkText) {

        final byte[] screenshot = this.getScreenshotAs(OutputType.BYTES);
        scenario.write(linkText);
        scenario.embed(screenshot, IMAGE_PNG);
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