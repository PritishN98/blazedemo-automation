package com.blazedemo.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object representing the BlazeDemo Home Page (flight search).
 */
public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By headerText = By.xpath("//h1[contains(text(),'Welcome to the Simple Travel Agency!')]");
    private final By departureDropdown = By.name("fromPort");
    private final By destinationDropdown = By.name("toPort");
    private final By findFlightsButton = By.cssSelector("input[type='submit'][value='Find Flights']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isHeaderDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(headerText)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDepartureDropdownDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(departureDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDestinationDropdownDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(destinationDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFindFlightsButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(findFlightsButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectDepartureCity(String city) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(departureDropdown));
        Select select = new Select(element);
        select.selectByVisibleText(city);
    }

    public void selectDestinationCity(String city) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(destinationDropdown));
        Select select = new Select(element);
        select.selectByVisibleText(city);
    }

    public String getSelectedDepartureCity() {
        Select select = new Select(driver.findElement(departureDropdown));
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedDestinationCity() {
        Select select = new Select(driver.findElement(destinationDropdown));
        return select.getFirstSelectedOption().getText();
    }

    public List<String> getAvailableDepartureCities() {
        Select select = new Select(driver.findElement(departureDropdown));
        List<String> cities = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            cities.add(option.getText());
        }
        return cities;
    }

    public List<String> getAvailableDestinationCities() {
        Select select = new Select(driver.findElement(destinationDropdown));
        List<String> cities = new ArrayList<>();
        for (WebElement option : select.getOptions()) {
            cities.add(option.getText());
        }
        return cities;
    }

    public ReservePage clickFindFlights() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(findFlightsButton));
        button.click();
        return new ReservePage(driver);
    }

    public ReservePage searchFlights(String fromCity, String toCity) {
        selectDepartureCity(fromCity);
        selectDestinationCity(toCity);
        return clickFindFlights();
    }
}
