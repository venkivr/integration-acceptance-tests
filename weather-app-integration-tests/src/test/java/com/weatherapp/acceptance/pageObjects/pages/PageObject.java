package com.weatherapp.acceptance.pageObjects.pages;

import com.google.common.base.Function;
import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static org.junit.Assert.fail;

@Component
public class PageObject {

    protected static final int TEN_SECONDS = 10;
    public static final Duration SIXTY_SECONDS_DURATION = Duration.ofSeconds(60);
    public static final Duration ONE_SECOND_DURATION = Duration.ofSeconds(1);
    public static final Duration THIRTY_SECOND_DURATION = Duration.ofSeconds(30);
    public static final String ELEMENT_IS_CLICKABLE = "elementIsClickable";
    public static final String ELEMENT_IS_VISIBLE = "elementIsVisible";


    private CucumberWebDriver webDriver;

    public PageObject(CucumberWebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public void waitForElement(final WebElement element) {
        Wait wait = new FluentWait(webDriver)
                .withTimeout(THIRTY_SECOND_DURATION)
                .pollingEvery(ONE_SECOND_DURATION)
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return element.findElement(By.xpath(".")) != null;
            }
        });
    }

    public WebElement waitAndReturnElement(WebElement element) {
        try {
            waitForElement(element);
            return element;
        } catch (Exception e) {
            fail("Element not found");
            return null;
        }
    }


    public WebDriver getWebDriver() {
        return this.webDriver;
    }

    public String getElementXPath(WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "getXPath=function(node)" +
                        "{" +
                        "if (node.id !== '')" +
                        "{" +
                        "return '//' + node.tagName.toLowerCase() + '[@id=\"' + node.id + '\"]'" +
                        "}" +

                        "if (node === document.body)" +
                        "{" +
                        "return node.tagName.toLowerCase()" +
                        "}" +

                        "var nodeCount = 0;" +
                        "var childNodes = node.parentNode.childNodes;" +

                        "for (var i=0; i<childNodes.length; i++)" +
                        "{" +
                        "var currentNode = childNodes[i];" +

                        "if (currentNode === node)" +
                        "{" +
                        "return getXPath(node.parentNode) + '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) + ']'" + "}" +
                        "if (currentNode.nodeType === 1 && " +
                        "currentNode.tagName.toLowerCase() === node.tagName.toLowerCase())" +
                        "{" +
                        "nodeCount++" +
                        "}" +
                        "}" +
                        "};" +

                        "return getXPath(arguments[0]);", element);
    }

}
