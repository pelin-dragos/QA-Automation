package com.qa.automation.project07.config;

/**
 * Test configuration for PROJECT_07 (Dynamic Content Testing).
 * Base URL from env; The Internet (herokuapp).
 */
public final class TestConfig {

    public static final String KEY_THE_INTERNET_BASE_URL = "THE_INTERNET_BASE_URL";

    private static final String DEFAULT_THE_INTERNET = "https://the-internet.herokuapp.com/";
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;
    public static final int AJAX_WAIT_TIMEOUT_SECONDS = 20;

    private TestConfig() {}

    public static String getTheInternetBaseUrl() {
        String value = System.getProperty(KEY_THE_INTERNET_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        value = System.getenv(KEY_THE_INTERNET_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return DEFAULT_THE_INTERNET;
    }

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
