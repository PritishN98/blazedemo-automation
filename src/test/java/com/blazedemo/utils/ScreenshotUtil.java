package com.blazedemo.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.blazedemo.base.DriverFactory;

/**
 * Utility class to capture screenshots upon test failure or on demand.
 */
public class ScreenshotUtil {

    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotsDir = System.getProperty("user.dir") + File.separator + "Screenshots";
        
        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String screenshotPath = screenshotsDir + File.separator + testName + "_" + timestamp + ".png";

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), new File(screenshotPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }
}
