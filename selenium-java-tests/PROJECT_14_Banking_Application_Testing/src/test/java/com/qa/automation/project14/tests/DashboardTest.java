package com.qa.automation.project14.tests;

import com.qa.automation.project14.base.BaseTest;
import com.qa.automation.project14.config.TestConfig;
import com.qa.automation.project14.pages.DashboardPage;
import com.qa.automation.project14.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Dashboard after login (TEST_CASES.md — Section 2).
 * <p>
 * TC-BANK-DASH-001 to TC-BANK-DASH-005: navigate to dashboard, view Accounts Overview,
 * get account numbers, get balance, logout. All tests require configured credentials;
 * they are skipped when BANKING_USERNAME/BANKING_PASSWORD are not set.
 */
class DashboardTest extends BaseTest {

    /** Logs in before each test so dashboard actions run in authenticated context. */
    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(),
                "BANKING_USERNAME and BANKING_PASSWORD must be set to run dashboard tests");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-BANK-DASH-001: After login, wait for dashboard load. Asserts dashboard is loaded,
     * isLoggedIn true, and welcome/heading message is present.
     */
    @Test
    @DisplayName("TC-BANK-DASH-001: Navigate to dashboard after login")
    void shouldLoadDashboardAfterLogin() {
        DashboardPage dashboard = new DashboardPage(driver);
        assertTrue(dashboard.isLoaded(), "Dashboard should be loaded after login");
        assertTrue(dashboard.isLoggedIn(), "User should be logged in");
        assertNotNull(dashboard.getWelcomeOrHeadingText(), "Welcome message or heading should be present");
    }

    /**
     * TC-BANK-DASH-002: From dashboard, click Accounts Overview. Asserts Accounts Overview
     * page loads without error (heading/panel visible).
     */
    @Test
    @DisplayName("TC-BANK-DASH-002: View Accounts Overview")
    void shouldViewAccountsOverview() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickAccountsOverview();
        assertTrue(dashboard.isLoaded(), "Accounts Overview page should load with no error");
        assertNotNull(dashboard.getWelcomeOrHeadingText(), "Content/heading should be present");
    }

    /**
     * TC-BANK-DASH-003: From dashboard, get list of account numbers. Asserts at least one
     * account number is returned (user has at least one account).
     */
    @Test
    @DisplayName("TC-BANK-DASH-003: Get account numbers")
    void shouldGetAccountNumbers() {
        DashboardPage dashboard = new DashboardPage(driver);
        List<String> accountNumbers = dashboard.getAccountNumbers();
        assertTrue(accountNumbers != null && accountNumbers.size() > 0,
                "Expected at least one account number after login");
    }

    /**
     * TC-BANK-DASH-004: From dashboard, get account balance (first account). Asserts
     * balance value is returned (may be 0); not null.
     */
    @Test
    @DisplayName("TC-BANK-DASH-004: Get account balance")
    void shouldGetAccountBalance() {
        DashboardPage dashboard = new DashboardPage(driver);
        String balance = dashboard.getFirstAccountBalance();
        assertNotNull(balance, "Balance value should be returned (may be 0); not null");
    }

    /**
     * TC-BANK-DASH-005: Click Logout from dashboard. Asserts logout succeeds and we are
     * redirected to login (login page visible).
     */
    @Test
    @DisplayName("TC-BANK-DASH-005: Logout from dashboard")
    void shouldLogoutFromDashboard() {
        DashboardPage dashboard = new DashboardPage(driver);
        LoginPage loginPage = dashboard.logout();
        assertTrue(loginPage.isLoaded(), "After logout, login page should be visible (redirected to login or home)");
    }
}
