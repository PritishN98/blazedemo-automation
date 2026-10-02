package com.blazedemo.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object representing the Reserve Page (list of available flights).
 */
public class ReservePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By heading = By.xpath("//h3[contains(text(),'Flights from')]");
    private final By flightTable = By.cssSelector("table.table");
    private final By flightRows = By.cssSelector("table.table tbody tr");
    private final By chooseFlightButtons = By.cssSelector("table.table tbody tr input[type='submit'][value='Choose This Flight']");

    public ReservePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHeadingText() {
        WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        return headingElement.getText();
    }

    public boolean isFlightTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(flightTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getFlightCount() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(flightRows));
        return rows.size();
    }

    public PurchasePage chooseFirstFlight() {
        return chooseFlightByIndex(0);
    }

    public PurchasePage chooseFlightByIndex(int index) {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(chooseFlightButtons));
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid flight index: " + index + ". Available: " + buttons.size());
        }
        buttons.get(index).click();
        return new PurchasePage(driver);
    }

    public String getFlightAirline(int index) {
        List<WebElement> rows = driver.findElements(flightRows);
        if (index >= 0 && index < rows.size()) {
            List<WebElement> cells = rows.get(index).findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                return cells.get(2).getText();
            }
        }
        return "";
    }

    public String getFlightPrice(int index) {
        List<WebElement> rows = driver.findElements(flightRows);
        if (index >= 0 && index < rows.size()) {
            List<WebElement> cells = rows.get(index).findElements(By.tagName("td"));
            if (cells.size() >= 6) {
                return cells.get(5).getText();
            }
        }
        return "";
    }
}
