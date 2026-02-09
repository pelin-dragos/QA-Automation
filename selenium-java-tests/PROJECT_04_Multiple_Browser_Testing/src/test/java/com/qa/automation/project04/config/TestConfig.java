package com.qa.automation.project04.config;

/**
 * Test configuration for PROJECT_04 (Multiple Browser).
 * Base URLs from env; no secrets in code.
 */
public final class TestConfig {

    public static final String KEY_SAUCEDEMO_BASE_URL = "SAUCEDEMO_BASE_URL";
    public static final String KEY_THE_INTERNET_BASE_URL = "THE_INTERNET_BASE_URL";
    public static final String KEY_BROWSER = "BROWSER";

    private static final String DEFAULT_SAUCEDEMO = "https://www.saucedemo.com/";
    private static final String DEFAULT_THE_INTERNET = "https://the-internet.herokuapp.com/";
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {}

    public static String getSaucedemoBaseUrl() {
        return getUrl(KEY_SAUCEDEMO_BASE_URL, DEFAULT_SAUCEDEMO);
    }

    public static String getTheInternetBaseUrl() {
        return getUrl(KEY_THE_INTERNET_BASE_URL, DEFAULT_THE_INTERNET);
    }

    private static String getUrl(String key, String defaultUrl) {
        String value = System.getProperty(key);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        value = System.getenv(key);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return defaultUrl;
    }

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
