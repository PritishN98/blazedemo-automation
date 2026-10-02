package com.blazedemo.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Manages the ExtentReports instance and HTML report generation configuration.
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportDir = System.getProperty("user.dir") + File.separator + "TestReport";
        
        File dir = new File(reportDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String reportPath = reportDir + File.separator + "BlazeDemo_TestReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("BlazeDemo Test Automation Report");
        sparkReporter.config().setReportName("BlazeDemo Automation Execution Results");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Application", "BlazeDemo Flight Booking");
        reports.setSystemInfo("Environment", "QA Demo");
        reports.setSystemInfo("User", System.getProperty("user.name"));
        reports.setSystemInfo("OS", System.getProperty("os.name"));
        reports.setSystemInfo("Java Version", System.getProperty("java.version"));

        return reports;
    }
}
