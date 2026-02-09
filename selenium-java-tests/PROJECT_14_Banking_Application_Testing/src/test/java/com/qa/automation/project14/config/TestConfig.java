package com.qa.automation.project14.config;

/**
 * Central test configuration for PROJECT_14 (Banking Application Testing).
 * <p>
 * Base URL and credentials are read only from environment variables or system properties.
 * No secrets are hardcoded; use .env.example as a template and set values in the
 * environment or a local .env file (not committed). Tests that require login should
 * skip gracefully when credentials are not set.
 */
public final class TestConfig {

    /** Environment or system property key for the banking application base URL. */
    public static final String KEY_BASE_URL = "BANKING_BASE_URL";
    /** Environment or system property key for the banking test user username. */
    public static final String KEY_USERNAME = "BANKING_USERNAME";
    /** Environment or system property key for the banking test user password. */
    public static final String KEY_PASSWORD = "BANKING_PASSWORD";

    /** Default base URL for ParaBank (public demo). Used when KEY_BASE_URL is not set. */
    private static final String DEFAULT_BASE_URL = "https://parabank.parasoft.com/parabank/";

    /** Timeout in seconds for page loads and explicit waits (WebDriverWait). */
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {}

    /**
     * Returns the base URL for the banking application (with trailing slash).
     * Resolution order: system property → environment variable → default (ParaBank).
     */
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

    /**
     * Returns the username for test login, or null if not set.
     * Used to skip login-dependent tests when credentials are missing.
     */
    public static String getUsername() {
        String value = System.getProperty(KEY_USERNAME);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_USERNAME);
        }
        return (value != null && !value.isBlank()) ? value.trim() : null;
    }

    /**
     * Returns the password for test login, or null if not set.
     * Never log or assert on the raw value; use only for login actions.
     */
    public static String getPassword() {
        String value = System.getProperty(KEY_PASSWORD);
        if (value == null || value.isBlank()) {
            value = System.getenv(KEY_PASSWORD);
        }
        return (value != null && !value.isBlank()) ? value : null;
    }

    /**
     * Returns true if both username and password are set, so login-dependent tests can run.
     * When false, tests should skip with a clear message (e.g. "BANKING_USERNAME and BANKING_PASSWORD not set").
     */
    public static boolean isLoginConfigured() {
        return getUsername() != null && getPassword() != null;
    }

    /** Returns the timeout in seconds used for explicit waits and page load. */
    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
