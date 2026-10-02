package com.blazedemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object representing the Confirmation Page (post-purchase booking summary).
 */
public class ConfirmationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By confirmationHeader = By.xpath("//h1[contains(text(),'Thank you for your purchase today!')]");
    private final By detailsTable = By.cssSelector("table.table");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isConfirmationDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getHeaderText() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationHeader));
        return header.getText();
    }

    public String getCellValueByLabel(String label) {
        try {
            By cellLocator = By.xpath("//tr[td[1][contains(normalize-space(), '" + label + "')]]/td[2]");
            WebElement cell = wait.until(ExpectedConditions.visibilityOfElementLocated(cellLocator));
            return cell.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getBookingId() {
        return getCellValueByLabel("Id");
    }

    public String getStatus() {
        return getCellValueByLabel("Status");
    }

    public String getAmount() {
        return getCellValueByLabel("Amount");
    }

    public String getCardNumber() {
        return getCellValueByLabel("Card Number");
    }

    public String getExpiration() {
        return getCellValueByLabel("Expiration");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
