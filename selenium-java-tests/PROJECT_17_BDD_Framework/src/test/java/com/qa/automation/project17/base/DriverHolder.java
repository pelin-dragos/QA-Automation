package com.qa.automation.project17.base;

import org.openqa.selenium.WebDriver;

/**
 * Holds the WebDriver for the current Cucumber scenario.
 * Hooks set the driver before each scenario; step definitions get it from here.
 * ThreadLocal keeps scenarios independent when running in parallel.
 */
public final class DriverHolder {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverHolder() {}

    public static void set(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver get() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}
