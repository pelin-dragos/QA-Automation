package com.qa.automation.project15.util;

/**
 * Generates unique test data for admin panel tests so that each test uses distinct
 * usernames and no shared mutable state. Prevents collisions and ensures test independence.
 */
public final class TestDataHelper {

    private TestDataHelper() {}

    /**
     * Returns a unique username prefix for the current test run (timestamp + random suffix).
     * Use for Create, Update, Delete tests so data does not clash between runs or tests.
     */
    public static String uniqueUsername() {
        return "testuser_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000);
    }

    /** Standard test password for created users (non-production; demo only). */
    public static final String TEST_PASSWORD = "TestPass123!";

    /** Employee name to use when adding users (OrangeHRM: must match existing employee; e.g. "a" to get first). */
    public static final String DEFAULT_EMPLOYEE_SEARCH = "a";
}
