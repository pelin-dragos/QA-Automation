package com.qa.automation.project17.config;

/**
 * Centralised test configuration for PROJECT_17 (BDD / Cucumber).
 * <p>
 * Base URL and timeouts from environment or system properties; no secrets in code.
 * Use .env.example as template; set values in env or local .env (not committed).
 */
public final class TestConfig {

    /** Key for Sauce Demo base URL (system property or env SAUCEDEMO_BASE_URL). */
    public static final String KEY_BASE_URL = "SAUCEDEMO_BASE_URL";

    private static final String DEFAULT_BASE_URL = "https://www.saucedemo.com/";

    /** Timeout in seconds for explicit waits and page load. */
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {}

    /** Base URL for the application; trailing slash normalised. */
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

    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
