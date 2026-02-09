package com.qa.automation.project03.config;

/**
 * Centralised test configuration for PROJECT_03 (Google Search).
 * Base URL read from environment; no secrets in code.
 */
public final class TestConfig {

    public static final String KEY_BASE_URL = "GOOGLE_BASE_URL";
    private static final String DEFAULT_BASE_URL = "https://www.google.com";
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {}

    public static String getBaseUrl() {
        String value = System.getProperty(KEY_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value.replaceAll("/+$", "") : value;
        }
        value = System.getenv(KEY_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value.replaceAll("/+$", "") : value;
        }
        return DEFAULT_BASE_URL;
    }

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
