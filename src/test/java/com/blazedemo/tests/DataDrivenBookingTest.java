package com.blazedemo.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.blazedemo.base.BaseTest;
import com.blazedemo.pages.ConfirmationPage;
import com.blazedemo.pages.HomePage;
import com.blazedemo.pages.PurchasePage;
import com.blazedemo.pages.ReservePage;

/**
 * Data-Driven Test suite running end-to-end flight booking with multiple passenger profiles.
 */
public class DataDrivenBookingTest extends BaseTest {

    @DataProvider(name = "bookingData")
    public Object[][] getBookingData() {
        return new Object[][] {
            { "Paris", "Buenos Aires", "Alice Smith", "45 Rue de Rivoli", "Paris", "IDF", "75001", "Visa", "4111111111111111", "05", "2028" },
            { "Portland", "Berlin", "Bob Johnson", "742 Evergreen Terrace", "Portland", "OR", "97201", "American Express", "378282246310005", "09", "2027" },
            { "San Diego", "Dublin", "Charlie Davis", "555 Pacific Coast Hwy", "San Diego", "CA", "92101", "Diner's Club", "30569309025904", "12", "2029" }
        };
    }

    @Test(priority = 4, dataProvider = "bookingData", groups = {"datadriven"}, description = "TC04: Multiple bookings with different data sets")
    public void TC04_multipleBookingsWithDifferentDataSets(
            String fromCity, String toCity,
            String name, String address, String city, String state, String zip,
            String cardType, String cardNumber, String month, String year) {

        HomePage homePage = new HomePage(driver);

        // 1. Search flight with provided cities
        ReservePage reservePage = homePage.searchFlights(fromCity, toCity);
        Assert.assertTrue(reservePage.getFlightCount() > 0, 
                "No flights available between " + fromCity + " and " + toCity);

        // 2. Select first flight
        PurchasePage purchasePage = reservePage.chooseFirstFlight();

        // 3. Enter details from data provider and complete purchase
        ConfirmationPage confirmationPage = purchasePage.completePurchase(
                name, address, city, state, zip,
                cardType, cardNumber, month, year, name, true
        );

        // 4. Validate successful booking
        Assert.assertTrue(confirmationPage.isConfirmationDisplayed(), 
                "Confirmation page was not displayed for passenger: " + name);

        String bookingId = confirmationPage.getBookingId();
        Assert.assertNotNull(bookingId, "Booking ID should not be null for passenger: " + name);
        Assert.assertFalse(bookingId.trim().isEmpty(), "Booking ID should not be empty for passenger: " + name);

        System.out.println("Data-Driven Booking Successful -> Passenger: " + name 
                + " | Route: " + fromCity + " -> " + toCity 
                + " | Booking ID: " + bookingId);
    }
}
