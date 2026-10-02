# End-to-End Automation Framework for BlazeDemo Flight Booking Application

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium%20WebDriver-4.45.0-43B02A?logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.12.0-FF7F00?logo=testng&logoColor=white)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![ExtentReports](https://img.shields.io/badge/ExtentReports-5.1.2-blue)](https://www.extentreports.com/)

---

## 📌 Project Overview
This project is an industry-standard, scalable **End-to-End Test Automation Framework** developed for the **BlazeDemo** flight booking application ([https://blazedemo.com](https://blazedemo.com)). 

It is designed following the **Page Object Model (POM)** architectural pattern, incorporating **TestNG**, **ThreadLocal WebDriver management**, **Data-Driven Testing**, automated **Failure Screenshots**, and rich **ExtentReports (HTML)** reporting.

---

## 🏗️ Framework Architecture

```text
blazedemo-automation/
├── .settings/                               <-- Eclipse IDE project preferences
├── src/
│   └── test/
│       ├── java/
│       │   └── com/blazedemo/
│       │       ├── base/
│       │       │   ├── BaseTest.java        <-- Setup & teardown lifecycle (@BeforeMethod, @AfterMethod)
│       │       │   └── DriverFactory.java   <-- ThreadLocal WebDriver manager (Chrome, Edge, Firefox)
│       │       ├── pages/                   <-- Page Object Model classes
│       │       │   ├── HomePage.java        <-- City selection & search flights
│       │       │   ├── ReservePage.java     <-- Available flights table & flight selection
│       │       │   ├── PurchasePage.java    <-- Passenger & payment information submission
│       │       │   └── ConfirmationPage.java<-- Booking confirmation ID & status validation
│       │       ├── tests/                   <-- TestNG Test Classes
│       │       │   ├── SmokeTest.java       <-- TC01: Homepage & dropdown validation
│       │       │   ├── FlightBookingTest.java <-- TC02: Flight search | TC03: Full Boston -> NYC booking
│       │       │   ├── DataDrivenBookingTest.java <-- TC04: Multi-passenger data-driven tests
│       │       │   └── NegativeBookingTest.java   <-- TC05, TC06, TC07: Validation & defect edge cases
│       │       └── utils/                   <-- Framework Utility Classes
│       │           ├── ConfigReader.java    <-- Reads environment configurations
│       │           ├── ScreenshotUtil.java  <-- Captures timestamped failure screenshots
│       │           ├── ExtentManager.java   <-- Initializes ExtentReports HTML instance
│       │           └── TestListener.java    <-- TestNG ITestListener for ExtentReports
│       └── resources/
│           └── config.properties            <-- Base URL, browser, timeout properties
├── Screenshots/                             <-- Captured screenshots on test failure
├── TestReport/                              <-- Generated ExtentReports HTML files
├── pom.xml                                  <-- Maven dependencies and plugins configuration
├── testng.xml                               <-- Master TestNG Suite XML
├── testng-smoke.xml                         <-- Smoke Test Suite XML
├── .classpath                               <-- Eclipse build path configuration
├── .project                                 <-- Eclipse project metadata
├── .gitignore                               <-- Git ignore rules
└── README.md                                <-- Project documentation
```

---

## 🧪 Test Scenarios & Coverage

| Test ID | Scenario Description | Type | Priority | Expected Outcome |
| :--- | :--- | :--- | :--- | :--- |
| **TC01** | Verify homepage loads and dropdowns visible | Smoke | 1 | Title contains "BlazeDemo", header displayed, departure and destination dropdowns loaded with cities, "Find Flights" button present. |
| **TC02** | Search flights with valid cities | Functional | 2 | Navigation to `reserve.php`, header displays selected cities, flight table contains available flights. |
| **TC03** | Complete a flight booking (Boston to New York) | Functional / Smoke | 3 | Full journey: Search $\rightarrow$ Select flight $\rightarrow$ Enter passenger & payment info $\rightarrow$ Verify `confirmation.php`, confirmation header, valid booking ID, and status `PendingCapture`. |
| **TC04** | Multiple bookings with different datasets | Data-Driven | 4 | Executes 3 unique passenger profiles with diverse names, addresses, routes, and credit cards using TestNG `@DataProvider`. |
| **TC05** | Blank credit card submission | Negative | 5 | Validates form behavior when credit card is empty. Documents missing validation defect (`BUG-01`) if confirmation page appears unexpectedly. |
| **TC06** | Invalid / non-numeric credit card characters | Negative | 6 | Tests special character inputs in card number field. Documents missing input validation defect (`BUG-02`). |
| **TC07** | Same departure and destination city | Negative | 7 | Verifies UI dropdown constraint preventing selection of identical departure and destination cities. |

---

## ⚙️ Prerequisites & Environment Setup
* **JDK:** Java SE 17 or higher
* **IDE:** Eclipse IDE (with TestNG & m2e plugins installed)
* **Browser:** Google Chrome (default), Microsoft Edge, or Mozilla Firefox

---

## 🚀 How to Import and Run in Eclipse IDE

### 1. Import Project into Eclipse
1. Open Eclipse IDE.
2. Go to **File** $\rightarrow$ **Import...**
3. Select **Maven** $\rightarrow$ **Existing Maven Projects**, then click **Next**.
4. In **Root Directory**, browse to:
   ```text
   C:\Users\<your-user>\eclipse-workspace\blazedemo-automation
   ```
5. Check the `pom.xml` checkbox and click **Finish**.
6. Allow Eclipse a few moments to resolve dependencies and build the workspace.

### 2. Run the Complete Test Suite (`testng.xml`)
1. In the Eclipse **Package Explorer**, expand `blazedemo-automation`.
2. Right-click on **`testng.xml`**.
3. Select **Run As** $\rightarrow$ **TestNG Suite**.
4. The tests will launch Chrome, execute all scenarios, and display real-time status in the TestNG view.

### 3. Run Individual Test Classes
1. Expand `src/test/java/com/blazedemo/tests`.
2. Right-click on any test class (e.g., `FlightBookingTest.java` or `SmokeTest.java`).
3. Select **Run As** $\rightarrow$ **TestNG Test**.

### 4. Run via Maven (Command Line or Eclipse)
* In Eclipse: Right-click project $\rightarrow$ **Run As** $\rightarrow$ **Maven test**.
* Or from command prompt in the project root:
  ```bash
  mvn clean test
  ```
* To run only smoke tests:
  ```bash
  mvn test -Dsurefire.suiteXmlFiles=testng-smoke.xml
  ```

---

## 📊 Test Reporting & Screenshots

### 1. ExtentReports (Rich Interactive HTML)
* Generated automatically under:
  ```text
  blazedemo-automation/TestReport/BlazeDemo_TestReport_<timestamp>.html
  ```
* Open this file in any web browser to view visual dashboards, execution timelines, step logs, and embedded failure screenshots.

### 2. Automated Failure Screenshots
* Any failing step triggers `ScreenshotUtil` to capture a screenshot automatically.
* Saved under:
  ```text
  blazedemo-automation/Screenshots/<testMethodName>_<timestamp>.png
  ```
* Attached directly inside the Extent HTML report.

### 3. TestNG Built-in Reports
* Available under:
  ```text
  blazedemo-automation/test-output/index.html
  blazedemo-automation/test-output/emailable-report.html
  ```

---

## 💡 Key Framework Design Highlights
* **ThreadLocal WebDriver:** Enables isolated browser instances per thread, ensuring parallel testing safety and clean state teardown.
* **Page Object Model (POM):** Complete separation between web locators/actions (`com.blazedemo.pages`) and test logic (`com.blazedemo.tests`).
* **Robust Explicit Waits:** Uses `WebDriverWait` with `ExpectedConditions` to avoid flakiness and hardcoded sleeps.
* **Configurable Environment:** Centralized `config.properties` for easily toggling target URLs, browsers, headless mode, and timeouts without changing code.
* **Defect Documentation:** Negative tests clearly assert expected business rules and record identified application defects (`SoftAssert`) with descriptive notes.
