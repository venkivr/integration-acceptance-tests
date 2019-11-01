package com.weatherapp.acceptance.helpers;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NavigationTasks {

    Logger LOGGER = LoggerFactory.getLogger(NavigationTasks.class);

    private CucumberWebDriver webDriver;

    public NavigationTasks(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void goToURL(String url) {
        webDriver.get(url);
    }

    public void goToURLForCity(String url,String cityName) {
        webDriver.get(url + "?city=" + cityName);
    }

    public void openNewTab() {
        ((JavascriptExecutor)webDriver).executeScript("window.open();");
    }

    public void switchToTab(Integer tab) {
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tab));
    }
}
