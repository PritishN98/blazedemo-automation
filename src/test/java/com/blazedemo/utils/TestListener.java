package com.blazedemo.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * TestNG listener to hook into test execution lifecycle and log results to ExtentReports.
 */
public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        
        ExtentTest test = extent.createTest(testName, description != null ? description : testName);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        testThreadLocal.set(test);
        
        System.out.println(">>> STARTING TEST: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThreadLocal.get().log(Status.PASS, "Test PASSED: " + result.getMethod().getMethodName());
        System.out.println(">>> TEST PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        testThreadLocal.get().log(Status.FAIL, "Test FAILED: " + testName);
        testThreadLocal.get().fail(result.getThrowable());

        String screenshotPath = ScreenshotUtil.captureScreenshot(testName);
        if (screenshotPath != null) {
            try {
                testThreadLocal.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            } catch (Exception e) {
                System.err.println("Could not attach screenshot to report: " + e.getMessage());
            }
        }
        System.out.println(">>> TEST FAILED: " + testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String reason = (result.getThrowable() != null) ? result.getThrowable().getMessage() : "Skipped";
        testThreadLocal.get().log(Status.SKIP, "Test SKIPPED: " + reason);
        System.out.println(">>> TEST SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println(">>> Extent report flushed successfully to /TestReport/ directory.");
    }
}
