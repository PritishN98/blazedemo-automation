package com.blazedemo.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.blazedemo.base.BaseTest;
import com.blazedemo.pages.ConfirmationPage;
import com.blazedemo.pages.HomePage;
import com.blazedemo.pages.PurchasePage;
import com.blazedemo.pages.ReservePage;

/**
 * Negative Test suite covering validation edge cases and defect verification.
 * Evaluates application behavior against expected business rules defined in the specification.
 */
public class NegativeBookingTest extends BaseTest {

    @Test(priority = 5, groups = {"negative"}, description = "TC05: Submit blank credit card - Verify validation behavior")
    public void TC05_blankCreditCardValidation() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(driver);

        // 1. Search flight and proceed to purchase page
        ReservePage reservePage = homePage.searchFlights("Boston", "New York");
        PurchasePage purchasePage = reservePage.chooseFirstFlight();

        // 2. Fill required passenger fields but leave Credit Card Number BLANK
        purchasePage.enterPassengerDetails("Jane Doe", "456 Elm St", "Boston", "MA", "02101");
        purchasePage.enterPaymentDetails("Visa", "", "11", "2028", "Jane Doe");
        ConfirmationPage confirmationPage = purchasePage.clickPurchaseFlight();

        // 3. Expected Outcome per Capstone Spec: Confirmation page should NOT appear
        boolean isConfirmationLoaded = driver.getCurrentUrl().contains("confirmation.php");
        
        if (isConfirmationLoaded && confirmationPage.isConfirmationDisplayed()) {
            String bookingId = confirmationPage.getBookingId();
            System.out.println("[DEFECT LOGGED - BUG-01] BlazeDemo mock application does not perform client/server validation for empty credit card. Confirmation was generated with ID: " + bookingId);
            // Log defect assertion note
            softAssert.fail("Defect Identified: Confirmation page appeared despite submitting a blank credit card (Booking ID: " + bookingId + "). Expected proper validation preventing booking.");
        } else {
            Assert.assertFalse(isConfirmationLoaded, "Confirmation page should not appear when credit card is blank.");
        }
        
        // Assert all soft assertions for reporting
        softAssert.assertAll();
    }

    @Test(priority = 6, groups = {"negative"}, description = "TC06: Submit invalid/non-numeric credit card characters - Verify validation behavior")
    public void TC06_invalidCreditCardCharactersValidation() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(driver);

        // 1. Search flight and proceed to purchase page
        ReservePage reservePage = homePage.searchFlights("Boston", "New York");
        PurchasePage purchasePage = reservePage.chooseFirstFlight();

        // 2. Enter non-numeric / special characters into Credit Card Number
        String invalidCard = "INVALID_CARD_!@#$%";
        purchasePage.enterPassengerDetails("Sam Wilson", "789 Pine St", "Boston", "MA", "02102");
        purchasePage.enterPaymentDetails("Visa", invalidCard, "11", "2028", "Sam Wilson");
        ConfirmationPage confirmationPage = purchasePage.clickPurchaseFlight();

        // 3. Expected Outcome per Capstone Spec: Proper validation or error behavior
        boolean isConfirmationLoaded = driver.getCurrentUrl().contains("confirmation.php");
        
        if (isConfirmationLoaded && confirmationPage.isConfirmationDisplayed()) {
            String bookingId = confirmationPage.getBookingId();
            System.out.println("[DEFECT LOGGED - BUG-02] BlazeDemo mock application accepted non-numeric credit card input '" + invalidCard + "'. Generated Booking ID: " + bookingId);
            softAssert.fail("Defect Identified: Application accepted invalid non-numeric credit card characters and proceeded to confirmation (Booking ID: " + bookingId + "). Expected proper input validation.");
        } else {
            Assert.assertFalse(isConfirmationLoaded, "Confirmation page should not appear for invalid credit card characters.");
        }

        softAssert.assertAll();
    }

    @Test(priority = 7, groups = {"negative"}, description = "TC07: Same departure and destination city - Verify booking prevention")
    public void TC07_sameDepartureAndDestinationCity() {
        HomePage homePage = new HomePage(driver);

        // 1. Verify UI design prevents same departure and destination selection
        List<String> departureCities = homePage.getAvailableDepartureCities();
        List<String> destinationCities = homePage.getAvailableDestinationCities();

        String testCity = "Paris";
        Assert.assertTrue(departureCities.contains(testCity), "Departure cities should contain " + testCity);

        // Per Capstone Spec: Same departure & destination city -> Booking should not proceed.
        // At the UI level, BlazeDemo dropdown lists are mutually disjoint to prevent identical city selection.
        boolean isSameCityInDestination = destinationCities.contains(testCity);
        Assert.assertFalse(isSameCityInDestination, 
                "UI Constraint Validated: Destination dropdown correctly excludes departure city '" + testCity + "', preventing same-city flight selection.");

        System.out.println("Negative Test TC07 Passed: Dropdown architecture successfully restricts same departure and destination selection.");
    }
}
