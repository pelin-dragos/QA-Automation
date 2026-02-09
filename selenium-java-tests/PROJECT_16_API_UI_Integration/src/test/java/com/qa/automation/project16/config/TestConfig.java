package com.qa.automation.project16.config;

/**
 * Central test configuration for PROJECT_16 (API + UI Integration).
 * <p>
 * API base URL is read from environment or system properties only; no hardcoded hosts.
 * Used by both RestAssured (API) and Selenium (UI navigation to same base). Timeouts
 * are configurable for full flow tests (e.g. 60s).
 */
public final class TestConfig {

    /** Environment or system property key for the API (and UI) base URL. */
    public static final String KEY_API_BASE_URL = "API_BASE_URL";

    /** Default base URL for JSONPlaceholder (public demo API; does not persist POST/PUT/DELETE). */
    private static final String DEFAULT_API_BASE_URL = "https://jsonplaceholder.typicode.com/";

    /** Timeout in seconds for Selenium page load and explicit waits. */
    public static final int DEFAULT_UI_TIMEOUT_SECONDS = 15;
    /** Timeout in seconds for full flow / integration tests. */
    public static final int DEFAULT_FLOW_TIMEOUT_SECONDS = 60;

    private TestConfig() {}

    /**
     * Returns the API base URL (with trailing slash). Resolution: system property → env → default.
     * Same URL is used for UI navigation (e.g. open /posts/1 in browser).
     */
    public static String getApiBaseUrl() {
        String value = System.getProperty(KEY_API_BASE_URL);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_API_BASE_URL);
        }
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return DEFAULT_API_BASE_URL;
    }

    /** Returns the timeout in seconds for UI (Selenium) waits and page load. */
    public static int getUiTimeoutSeconds() {
        return DEFAULT_UI_TIMEOUT_SECONDS;
    }

    /** Returns the timeout in seconds for full flow / integration tests. */
    public static int getFlowTimeoutSeconds() {
        return DEFAULT_FLOW_TIMEOUT_SECONDS;
    }
}
