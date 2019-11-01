package com.weatherapp.acceptance.pageObjects.pages;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LandingPage extends PageObject {

    private final String DT_DAY = "day-";
    private final String DT_DATE = "date-";
    private final String DT_DESCRIPTION = "description-";
    private final String DT_TEMPERATURE_MAX = "maximum-";
    private final String DT_TEMPERATURE_MIN = "minimum-";
    private final String DT_SPEED = "speed-";
    private final String DT_DIRECTION = "direction-";
    private final String DT_RAINFALL = "rainfall-";
    private final String DT_PRESSURE = "pressure-";
    private final String DT_HOUR = "hour-";
    private final String DT_BASE_START = ".//*[@data-test='";
    private final String DT_BASE_END = "']";
    private final String EXPANDED_STYLE = "max-height: 2000px";

    public LandingPage(CucumberWebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(xpath = "//h1[contains(text(),'Day Weather Forecast for')]")
    WebElement dayForeCastHeading;

    @FindBy(xpath = "//input[@id='city']")
    WebElement cityName;

    @FindBy(xpath = "//div[@class='summary']")
    List<WebElement> summaryRows;

    @FindBy(xpath = "//div[@class='details']")
    List<WebElement> detailsRows;

    public void waitForPageToLoad() {
        this.waitForElement(dayForeCastHeading);
    }

    public WebElement getCityNameInput() {
        return waitAndReturnElement(cityName);
    }

    public List<WebElement> getSummaryRows() {
        return summaryRows;
    }

    private List<WebElement> getDetailsRows() {
        return detailsRows;
    }

    private WebElement getDetailsRowForDay(int day) {
        return getDetailsRows().get(day);
    }

    public boolean areDetailsExpandedForDay(int day) {
        return getDetailsRowForDay(day).getAttribute("style").contains(EXPANDED_STYLE);
    }

    private List<WebElement> getDetailsRowsForDay(WebElement summaryRow) {
        return summaryRow.findElements(By.xpath("./following-sibling::div[@class='details']/div[@class='detail']"));
    }

    public Map<String, String> getSummaryForDay(Integer dayNum) {
        Map<String, String> summaryRow = new HashMap<>();
        WebElement row = getSummaryRows().get(dayNum - 1);
        summaryRow.put("day", row.findElement(By.xpath(getDayXpath(dayNum))).getText());
        summaryRow.put("date", row.findElement(By.xpath(getDateXpath(dayNum))).getText());
        summaryRow.put("description", row.findElement(By.xpath(getDescriptionXpath(dayNum))).getAttribute("aria-label"));
        summaryRow.put("temperature-max", row.findElement(By.xpath(getTemperatureMaxXpath(dayNum))).getAttribute("innerText").replaceAll(".$", "")); //Remove degree char
        summaryRow.put("temperature-min", row.findElement(By.xpath(getTemperatureMinXpath(dayNum))).getAttribute("innerText").replaceAll(".$", ""));
        summaryRow.put("speed", row.findElement(By.xpath(getSpeedXpath(dayNum))).getAttribute("innerText"));

        String style = row.findElement(By.xpath(getDirectionXpath(dayNum))).getAttribute("style");
        String direction = style.substring(style.indexOf("deg") - 3, style.indexOf("deg"));
        summaryRow.put("direction", direction);

        summaryRow.put("rainfall", row.findElement(By.xpath(getRainfallXpath(dayNum))).getText().replace("mm", ""));
        summaryRow.put("pressure", row.findElement(By.xpath(getPressureXpath(dayNum))).getText());
        return summaryRow;
    }

    public List<HashMap<String, String>> getDetailsForDay(Integer dayNum) {
        List<HashMap<String, String>> allDetails = new ArrayList<>();
        WebElement row = getSummaryRows().get(dayNum - 1);
        List<WebElement> detailRows = getDetailsRowsForDay(row);
        for (int i = 1; i <= detailRows.size(); i++) {
            HashMap<String, String> detailRow = new HashMap<>();
            detailRow.put("row", String.valueOf(i));
            detailRow.put("hour", getHourDetails(dayNum, i));
            detailRow.put("description", getDetailsDescription(dayNum, i));
            detailRow.put("temperature-max", getMaxTemperatureDetails(dayNum, i));
            detailRow.put("temperature-min", getMinTemperatureDetails(dayNum, i));
            detailRow.put("speed", getSpeedDetails(dayNum, i));
            detailRow.put("direction", getDirectionDetails(dayNum, i));
            detailRow.put("rainfall", getRainfallDetails(dayNum, i));
            detailRow.put("pressure", getPressureDetails(dayNum, i));

            allDetails.add(detailRow);
        }
        return allDetails;
    }

    private String getDayXpath(Integer day) {
        return DT_BASE_START + DT_DAY + day + DT_BASE_END;
    }

    private String getDateXpath(Integer day) {
        return DT_BASE_START + DT_DATE + day + DT_BASE_END;
    }

    private String getDescriptionXpath(Integer day) {
        return DT_BASE_START + DT_DESCRIPTION + day + DT_BASE_END;
    }

    private String getDetailsDescription(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_DESCRIPTION + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("aria-label");
    }

    private String getTemperatureMaxXpath(Integer day) {
        return DT_BASE_START + DT_TEMPERATURE_MAX + day + DT_BASE_END;
    }

    private String getMaxTemperatureDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_TEMPERATURE_MAX + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText").replaceAll(".$", "");
    }

    private String getTemperatureMinXpath(Integer day) {
        return DT_BASE_START + DT_TEMPERATURE_MIN + day + DT_BASE_END;
    }

    private String getMinTemperatureDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_TEMPERATURE_MIN + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText").replaceAll(".$", "");
    }

    private String getSpeedXpath(Integer day) {
        return DT_BASE_START + DT_SPEED + day + DT_BASE_END;
    }

    private String getSpeedDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_SPEED + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText");
    }

    private String getDirectionXpath(Integer day) {
        return DT_BASE_START + DT_DIRECTION + day + DT_BASE_END + "/child::*";
    }

    private String getDirectionDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_DIRECTION + day + "-" + row + DT_BASE_END + "/child::*";
        String style = getWebDriver().findElement(By.xpath(xpath)).getAttribute("style");
        return style.substring(style.indexOf("deg") - 3, style.indexOf("deg"));
    }

    private String getRainfallXpath(Integer day) {
        return DT_BASE_START + DT_RAINFALL + day + DT_BASE_END;
    }

    private String getRainfallDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_RAINFALL + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText").replace("mm", "");
    }

    private String getPressureXpath(Integer day) {
        return DT_BASE_START + DT_PRESSURE + day + DT_BASE_END;
    }

    private String getPressureDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_PRESSURE + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText");
    }

    private String getHourDetails(Integer day, Integer row) {
        String xpath = DT_BASE_START + DT_HOUR + day + "-" + row + DT_BASE_END;
        return getWebDriver().findElement(By.xpath(xpath)).getAttribute("innerText");
    }

}