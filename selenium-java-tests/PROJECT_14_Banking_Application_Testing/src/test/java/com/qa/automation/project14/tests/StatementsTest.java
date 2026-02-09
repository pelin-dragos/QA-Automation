package com.qa.automation.project14.tests;

import com.qa.automation.project14.base.BaseTest;
import com.qa.automation.project14.config.TestConfig;
import com.qa.automation.project14.pages.AccountActivityPage;
import com.qa.automation.project14.pages.DashboardPage;
import com.qa.automation.project14.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Balance & Statements (TEST_CASES.md — Section 3).
 * <p>
 * TC-BANK-STMT-001: View account statements (navigate to account/activity, verify list or table).
 * TC-BANK-STMT-002: Verify account balance is displayed correctly (not null, format consistent).
 * Both tests require configured credentials and are skipped when not set.
 */
class StatementsTest extends BaseTest {

    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(),
                "BANKING_USERNAME and BANKING_PASSWORD must be set to run statement tests");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-BANK-STMT-001: Login, navigate to account/statements (first account activity).
     * Asserts statements or transaction list is available (table or content present); no crash.
     */
    @Test
    @DisplayName("TC-BANK-STMT-001: View account statements")
    void shouldViewAccountStatements() {
        DashboardPage dashboard = new DashboardPage(driver);
        boolean opened = dashboard.clickFirstAccountLinkToActivity();
        AccountActivityPage activityPage = new AccountActivityPage(driver);
        assertTrue(activityPage.isLoaded(), "Account/statements page should load without crash");
        if (opened) {
            assertTrue(activityPage.hasTransactionOrStatementTable() || activityPage.getPanelText() != null,
                    "Statements or transaction list should be available or panel content present");
        }
    }

    /**
     * TC-BANK-STMT-002: Get balance from dashboard and verify it is displayed correctly:
     * not null and format consistent (non-empty string after trim).
     */
    @Test
    @DisplayName("TC-BANK-STMT-002: Verify account balance displayed")
    void shouldVerifyBalanceDisplayed() {
        DashboardPage dashboard = new DashboardPage(driver);
        String balance = dashboard.getFirstAccountBalance();
        assertNotNull(balance, "Balance should not be null");
        assertTrue(!balance.isBlank(), "Balance should be displayed (format consistent, non-empty)");
    }
}
