package com.qa.automation.project05.config;

/**
 * Test configuration for PROJECT_05 (Screenshot on Failure).
 * Base URL from env; screenshot output dir configurable.
 */
public final class TestConfig {

    public static final String KEY_SAUCEDEMO_BASE_URL = "SAUCEDEMO_BASE_URL";
    public static final String KEY_SCREENSHOT_DIR = "SCREENSHOT_OUTPUT_DIR";

    private static final String DEFAULT_SAUCEDEMO = "https://www.saucedemo.com/";
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;
    public static final String DEFAULT_SCREENSHOT_DIR = "target/screenshot-on-failure";

    private TestConfig() {}

    public static String getSaucedemoBaseUrl() {
        String value = System.getProperty(KEY_SAUCEDEMO_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        value = System.getenv(KEY_SAUCEDEMO_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return DEFAULT_SAUCEDEMO;
    }

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }

    public static String getScreenshotOutputDir() {
        String value = System.getProperty(KEY_SCREENSHOT_DIR);
        if (value != null && !value.isBlank()) return value;
        value = System.getenv(KEY_SCREENSHOT_DIR);
        if (value != null && !value.isBlank()) return value;
        return DEFAULT_SCREENSHOT_DIR;
    }
}
