package com.blazedemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object representing the Purchase Page (passenger and payment details).
 */
public class PurchasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By heading = By.xpath("//h2[contains(text(),'reserved')]");
    private final By totalCost = By.xpath("//p[contains(text(),'Total Cost:')]//em");
    private final By nameInput = By.id("inputName");
    private final By addressInput = By.id("address");
    private final By cityInput = By.id("city");
    private final By stateInput = By.id("state");
    private final By zipCodeInput = By.id("zipCode");
    private final By cardTypeDropdown = By.id("cardType");
    private final By cardNumberInput = By.id("creditCardNumber");
    private final By cardMonthInput = By.id("creditCardMonth");
    private final By cardYearInput = By.id("creditCardYear");
    private final By nameOnCardInput = By.id("nameOnCard");
    private final By rememberMeCheckbox = By.id("rememberMe");
    private final By purchaseFlightButton = By.cssSelector("input[type='submit'][value='Purchase Flight']");

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }

    public String getTotalCost() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(totalCost)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void enterPassengerDetails(String name, String address, String city, String state, String zipCode) {
        WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        nameEl.clear();
        if (name != null) nameEl.sendKeys(name);

        WebElement addrEl = driver.findElement(addressInput);
        addrEl.clear();
        if (address != null) addrEl.sendKeys(address);

        WebElement cityEl = driver.findElement(cityInput);
        cityEl.clear();
        if (city != null) cityEl.sendKeys(city);

        WebElement stateEl = driver.findElement(stateInput);
        stateEl.clear();
        if (state != null) stateEl.sendKeys(state);

        WebElement zipEl = driver.findElement(zipCodeInput);
        zipEl.clear();
        if (zipCode != null) zipEl.sendKeys(zipCode);
    }

    public void enterPaymentDetails(String cardType, String cardNumber, String month, String year, String nameOnCard) {
        if (cardType != null && !cardType.isEmpty()) {
            Select select = new Select(driver.findElement(cardTypeDropdown));
            select.selectByVisibleText(cardType);
        }

        WebElement cardNumEl = driver.findElement(cardNumberInput);
        cardNumEl.clear();
        if (cardNumber != null) cardNumEl.sendKeys(cardNumber);

        if (month != null && !month.isEmpty()) {
            WebElement monthEl = driver.findElement(cardMonthInput);
            monthEl.clear();
            monthEl.sendKeys(month);
        }

        if (year != null && !year.isEmpty()) {
            WebElement yearEl = driver.findElement(cardYearInput);
            yearEl.clear();
            yearEl.sendKeys(year);
        }

        WebElement nameOnCardEl = driver.findElement(nameOnCardInput);
        nameOnCardEl.clear();
        if (nameOnCard != null) nameOnCardEl.sendKeys(nameOnCard);
    }

    public void setRememberMe(boolean remember) {
        WebElement checkbox = driver.findElement(rememberMeCheckbox);
        if (checkbox.isSelected() != remember) {
            checkbox.click();
        }
    }

    public ConfirmationPage clickPurchaseFlight() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(purchaseFlightButton));
        button.click();
        return new ConfirmationPage(driver);
    }

    public ConfirmationPage completePurchase(String name, String address, String city, String state, String zipCode,
                                            String cardType, String cardNumber, String month, String year,
                                            String nameOnCard, boolean remember) {
        enterPassengerDetails(name, address, city, state, zipCode);
        enterPaymentDetails(cardType, cardNumber, month, year, nameOnCard);
        setRememberMe(remember);
        return clickPurchaseFlight();
    }
}
