package com.qa.automation.project06.config;

/**
 * Test configuration for PROJECT_06 (E-Commerce Shopping Cart).
 * Base URL and credentials from env; no secrets in code.
 */
public final class TestConfig {

    public static final String KEY_SAUCEDEMO_BASE_URL = "SAUCEDEMO_BASE_URL";
    public static final String KEY_SAUCEDEMO_USERNAME = "SAUCEDEMO_USERNAME";
    public static final String KEY_SAUCEDEMO_PASSWORD = "SAUCEDEMO_PASSWORD";

    private static final String DEFAULT_SAUCEDEMO = "https://www.saucedemo.com/";
    private static final String DEFAULT_USERNAME = "standard_user";
    private static final String DEFAULT_PASSWORD = "secret_sauce";
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

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

    public static String getSaucedemoUsername() {
        String value = System.getProperty(KEY_SAUCEDEMO_USERNAME);
        if (value != null && !value.isBlank()) return value;
        value = System.getenv(KEY_SAUCEDEMO_USERNAME);
        if (value != null && !value.isBlank()) return value;
        return DEFAULT_USERNAME;
    }

    public static String getSaucedemoPassword() {
        String value = System.getProperty(KEY_SAUCEDEMO_PASSWORD);
        if (value != null && !value.isBlank()) return value;
        value = System.getenv(KEY_SAUCEDEMO_PASSWORD);
        if (value != null && !value.isBlank()) return value;
        return DEFAULT_PASSWORD;
    }

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
