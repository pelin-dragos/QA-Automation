package com.qa.automation.project02.config;

/**
 * Central test configuration for PROJECT_02 (Form Validation).
 * <p>
 * Provides base URLs for DemoQA (Text Box, Practice Form) and The Internet (Form Authentication).
 * All URLs and timeouts are read from environment variables or system properties first; defaults
 * are used when not set. No secrets or credentials are stored here.
 */
public final class TestConfig {

    /** Environment/system property key for DemoQA base URL. */
    public static final String KEY_DEMOQA_BASE = "DEMOQA_BASE_URL";
    /** Environment/system property key for The Internet base URL. */
    public static final String KEY_THE_INTERNET_BASE = "THE_INTERNET_BASE_URL";

    private static final String DEFAULT_DEMOQA = "https://demoqa.com/";
    private static final String DEFAULT_THE_INTERNET = "https://the-internet.herokuapp.com/";

    /** Default explicit wait and page load timeout in seconds. */
    public static final int DEFAULT_TIMEOUT_SECONDS = 15;

    private TestConfig() {}

    /**
     * Returns DemoQA base URL (with trailing slash). Checks system property then env var.
     */
    public static String getDemoQABaseUrl() {
        String v = System.getProperty(KEY_DEMOQA_BASE);
        if (v == null || v.isBlank()) v = System.getenv(KEY_DEMOQA_BASE);
        return (v != null && !v.isBlank()) ? (v.endsWith("/") ? v : v + "/") : DEFAULT_DEMOQA;
    }

    /** Returns full URL for DemoQA Automation Practice Form page. */
    public static String getPracticeFormUrl() {
        return getDemoQABaseUrl() + "automation-practice-form";
    }

    /** Returns full URL for DemoQA Text Box page (used for email validation tests). */
    public static String getTextBoxUrl() {
        return getDemoQABaseUrl() + "text-box";
    }

    /**
     * Returns The Internet Form Authentication login page URL. Base URL from property/env.
     */
    public static String getTheInternetLoginUrl() {
        String v = System.getProperty(KEY_THE_INTERNET_BASE);
        if (v == null || v.isBlank()) v = System.getenv(KEY_THE_INTERNET_BASE);
        String base = (v != null && !v.isBlank()) ? (v.endsWith("/") ? v : v + "/") : DEFAULT_THE_INTERNET;
        return base + "login";
    }

    /** Returns the timeout in seconds used for explicit waits and page load. */
    public static int getTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }
}
