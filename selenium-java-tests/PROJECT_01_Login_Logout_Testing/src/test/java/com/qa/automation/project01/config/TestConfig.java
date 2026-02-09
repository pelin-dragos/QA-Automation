package com.qa.automation.project01.config;

/**
 * Centralised test configuration for PROJECT_01 (Login & Logout).
 * <p>
 * All URLs and credentials are read from environment variables or system properties.
 * No secrets are hardcoded; use .env.example as a template and set values in the
 * environment or a local .env (not committed).
 */
public final class TestConfig {

    /** Key for application base URL (system property or env var SAUCEDEMO_BASE_URL). */
    public static final String KEY_BASE_URL = "SAUCEDEMO_BASE_URL";

    /** Default base URL for Sauce Demo (public demo site). Used when no env/config is set. */
    private static final String DEFAULT_BASE_URL = "https://www.saucedemo.com/";

    /** Optional: override username via SAUCEDEMO_USERNAME (tests may use test data instead). */
    public static final String KEY_USERNAME = "SAUCEDEMO_USERNAME";

    /** Optional: override password via SAUCEDEMO_PASSWORD. */
    public static final String KEY_PASSWORD = "SAUCEDEMO_PASSWORD";

    /** Timeout in seconds for page loads and WebDriverWait; keeps waits consistent and configurable. */
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {
        // Prevent instantiation; this class only exposes static configuration.
    }

    /**
     * Returns the base URL for the application under test.
     * Resolution order: system property → environment variable → default (Sauce Demo).
     * Trailing slash is normalised so callers can append paths directly.
     */
    public static String getBaseUrl() {
        String value = System.getProperty(KEY_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        value = System.getenv(KEY_BASE_URL);
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return DEFAULT_BASE_URL;
    }

    /**
     * Returns the timeout in seconds used for explicit waits (WebDriverWait) across page objects.
     */
    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
