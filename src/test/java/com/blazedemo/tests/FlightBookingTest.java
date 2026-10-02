package com.blazedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.blazedemo.base.BaseTest;
import com.blazedemo.pages.ConfirmationPage;
import com.blazedemo.pages.HomePage;
import com.blazedemo.pages.PurchasePage;
import com.blazedemo.pages.ReservePage;

/**
 * Functional Test suite covering flight searching and complete end-to-end booking.
 */
public class FlightBookingTest extends BaseTest {

    @Test(priority = 2, groups = {"functional"}, description = "TC02: Search flights with valid departure and destination cities")
    public void TC02_searchFlightsWithValidCities() {
        HomePage homePage = new HomePage(driver);

        String departureCity = "Paris";
        String destinationCity = "Rome";

        // Select cities and search
        ReservePage reservePage = homePage.searchFlights(departureCity, destinationCity);

        // Verify URL and heading
        Assert.assertTrue(driver.getCurrentUrl().contains("reserve.php"), 
                "Page URL did not navigate to reserve.php.");

        String heading = reservePage.getHeadingText();
        Assert.assertTrue(heading.contains(departureCity) && heading.contains(destinationCity),
                "Reserve page heading did not show expected cities. Actual: " + heading);

        // Verify flights table and count
        Assert.assertTrue(reservePage.isFlightTableDisplayed(), "Flights table was not displayed.");
        int flightCount = reservePage.getFlightCount();
        Assert.assertTrue(flightCount > 0, "No flights found in reserve table. Count: " + flightCount);
    }

    @Test(priority = 3, groups = {"functional", "smoke"}, description = "TC03: Complete end-to-end flight booking from Boston to New York")
    public void TC03_completeFlightBooking() {
        HomePage homePage = new HomePage(driver);

        // Per Capstone specification: Automate booking a flight from Boston to New York
        String departureCity = "Boston";
        String destinationCity = "New York";

        // Step 1: Search flight
        ReservePage reservePage = homePage.searchFlights(departureCity, destinationCity);

        // Step 2: Choose first available flight
        PurchasePage purchasePage = reservePage.chooseFirstFlight();

        // Step 3: Verify purchase page loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("purchase.php"),
                "Did not navigate to purchase.php.");

        // Step 4: Fill passenger and payment details
        String passengerName = "John Doe";
        ConfirmationPage confirmationPage = purchasePage.completePurchase(
                passengerName,
                "123 Beacon Street",
                "Boston",
                "MA",
                "02108",
                "Visa",
                "4111222233334444",
                "11",
                "2028",
                passengerName,
                true
        );

        // Step 5: Assert Confirmation Page
        Assert.assertTrue(driver.getCurrentUrl().contains("confirmation.php"),
                "Did not navigate to confirmation.php.");
        Assert.assertTrue(confirmationPage.isConfirmationDisplayed(),
                "Confirmation success banner was not displayed.");

        String headerText = confirmationPage.getHeaderText();
        Assert.assertEquals(headerText, "Thank you for your purchase today!",
                "Confirmation header message does not match expected text.");

        // Step 6: Validate Booking Details
        String bookingId = confirmationPage.getBookingId();
        Assert.assertNotNull(bookingId, "Booking ID should not be null.");
        Assert.assertFalse(bookingId.isEmpty(), "Booking ID should not be empty.");

        String status = confirmationPage.getStatus();
        Assert.assertEquals(status, "PendingCapture", "Booking status did not match expected 'PendingCapture'.");

        String amount = confirmationPage.getAmount();
        Assert.assertTrue(amount.contains("USD"), "Amount string did not contain currency USD. Actual: " + amount);

        System.out.println("Flight booked successfully! Generated Booking ID: " + bookingId);
    }
}
