package com.blazedemo.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.blazedemo.utils.ConfigReader;

/**
 * BaseTest serves as the parent class for all TestNG test classes.
 * Handles WebDriver lifecycle (@BeforeMethod and @AfterMethod).
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("") String browser) {
        if (browser == null || browser.trim().isEmpty()) {
            browser = ConfigReader.getBrowser();
        }
        boolean headless = ConfigReader.isHeadless();
        
        driver = DriverFactory.initDriver(browser, headless);
        driver.get(ConfigReader.getUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
