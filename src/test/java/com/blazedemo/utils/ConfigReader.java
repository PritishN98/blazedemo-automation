package com.blazedemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read framework configuration properties from config.properties.
 */
public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            String configFilePath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configFilePath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
            // Fallback defaults
            properties = new Properties();
            properties.setProperty("url", "https://blazedemo.com");
            properties.setProperty("browser", "chrome");
            properties.setProperty("implicitWait", "10");
            properties.setProperty("headless", "false");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getUrl() {
        return properties.getProperty("url", "https://blazedemo.com");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicitWait", "10"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }
}
