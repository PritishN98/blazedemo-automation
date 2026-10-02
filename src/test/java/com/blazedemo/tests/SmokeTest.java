package com.blazedemo.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.blazedemo.base.BaseTest;
import com.blazedemo.pages.HomePage;

/**
 * Smoke Test suite verifying basic portal accessibility and critical UI elements.
 */
public class SmokeTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke"}, description = "TC01: Verify homepage loads and dropdowns are visible")
    public void TC01_verifyHomePageLoadsAndDropdownsVisible() {
        HomePage homePage = new HomePage(driver);

        // 1. Verify Page Title
        String pageTitle = homePage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("BlazeDemo"), 
                "Page title did not contain 'BlazeDemo'. Actual title: " + pageTitle);

        // 2. Verify Welcome Header
        Assert.assertTrue(homePage.isHeaderDisplayed(), 
                "Welcome header 'Welcome to the Simple Travel Agency!' was not displayed.");

        // 3. Verify Departure Dropdown
        Assert.assertTrue(homePage.isDepartureDropdownDisplayed(), 
                "Departure dropdown ('fromPort') was not visible on the homepage.");

        List<String> departureCities = homePage.getAvailableDepartureCities();
        Assert.assertFalse(departureCities.isEmpty(), "Departure cities list should not be empty.");
        Assert.assertTrue(departureCities.contains("Paris"), "Departure list should contain Paris.");
        Assert.assertTrue(departureCities.contains("Boston"), "Departure list should contain Boston.");

        // 4. Verify Destination Dropdown
        Assert.assertTrue(homePage.isDestinationDropdownDisplayed(), 
                "Destination dropdown ('toPort') was not visible on the homepage.");

        List<String> destinationCities = homePage.getAvailableDestinationCities();
        Assert.assertFalse(destinationCities.isEmpty(), "Destination cities list should not be empty.");
        Assert.assertTrue(destinationCities.contains("Rome"), "Destination list should contain Rome.");
        Assert.assertTrue(destinationCities.contains("New York"), "Destination list should contain New York.");

        // 5. Verify Find Flights Button
        Assert.assertTrue(homePage.isFindFlightsButtonDisplayed(), 
                "'Find Flights' button was not visible on the homepage.");
    }
}
