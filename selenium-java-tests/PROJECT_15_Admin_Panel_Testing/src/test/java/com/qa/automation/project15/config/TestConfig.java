package com.qa.automation.project15.config;

/**
 * Central test configuration for PROJECT_15 (Admin Panel Testing).
 * <p>
 * Base URL and admin credentials are read only from environment variables or system properties.
 * No secrets in code; use .env.example as a template. Tests that require login skip gracefully
 * when credentials are not set ({@link #isLoginConfigured()}).
 */
public final class TestConfig {

    /** Environment or system property key for the admin panel base URL. */
    public static final String KEY_BASE_URL = "ADMIN_BASE_URL";
    /** Environment or system property key for the admin username. */
    public static final String KEY_USERNAME = "ADMIN_USERNAME";
    /** Environment or system property key for the admin password. */
    public static final String KEY_PASSWORD = "ADMIN_PASSWORD";

    /** Default base URL for OrangeHRM demo. Used when KEY_BASE_URL is not set. */
    private static final String DEFAULT_BASE_URL = "https://opensource-demo.orangehrmlive.com/web/";

    /** Timeout in seconds for page loads and explicit waits. */
    public static final int DEFAULT_TIMEOUT_SECONDS = 20;

    private TestConfig() {}

    /** Returns the base URL (with trailing slash). Resolution: system property → env → default. */
    public static String getBaseUrl() {
        String value = System.getProperty(KEY_BASE_URL);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_BASE_URL);
        }
        if (value != null && !value.isBlank()) {
            return value.endsWith("/") ? value : value + "/";
        }
        return DEFAULT_BASE_URL;
    }

    /** Returns the admin username, or null if not set. */
    public static String getUsername() {
        String value = System.getProperty(KEY_USERNAME);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_USERNAME);
        }
        return (value != null && !value.isBlank()) ? value.trim() : null;
    }

    /** Returns the admin password, or null if not set. Never log this value. */
    public static String getPassword() {
        String value = System.getProperty(KEY_PASSWORD);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_PASSWORD);
        }
        return (value != null && !value.isBlank()) ? value : null;
    }

    /** Returns true if both username and password are set; login-dependent tests use this to skip when false. */
    public static boolean isLoginConfigured() {
        return getUsername() != null && getPassword() != null;
    }

    /** Returns the timeout in seconds for explicit waits and page load. */
    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
