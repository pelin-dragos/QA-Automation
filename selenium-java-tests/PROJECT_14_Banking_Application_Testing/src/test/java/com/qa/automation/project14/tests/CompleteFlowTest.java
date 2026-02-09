package com.qa.automation.project14.tests;

import com.qa.automation.project14.base.BaseTest;
import com.qa.automation.project14.config.TestConfig;
import com.qa.automation.project14.pages.DashboardPage;
import com.qa.automation.project14.pages.LoginPage;
import com.qa.automation.project14.pages.TransferFundsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Complete banking flow (TEST_CASES.md — Section 5).
 * <p>
 * TC-BANK-FLOW-001: Full flow — login, verify dashboard, get balance (not null), get account
 * numbers (count > 0), navigate to Transfer Funds (link works). All steps must pass; transfer
 * page loads when link is clicked. Skipped when credentials are not set.
 */
class CompleteFlowTest extends BaseTest {

    /**
     * TC-BANK-FLOW-001: Execute full banking flow: login → dashboard loaded → balance not null
     * → account numbers count > 0 → click Transfer Funds and verify transfer page loads.
     */
    @Test
    @DisplayName("TC-BANK-FLOW-001: Full banking flow")
    void shouldCompleteFullBankingFlow() {
        assumeTrue(TestConfig.isLoginConfigured(),
                "BANKING_USERNAME and BANKING_PASSWORD must be set to run flow test");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());

        DashboardPage dashboard = new DashboardPage(driver);
        assertTrue(dashboard.isLoaded(), "Dashboard should load after login");
        assertTrue(dashboard.isLoggedIn(), "User should be logged in");

        String balance = dashboard.getFirstAccountBalance();
        assertNotNull(balance, "Balance should not be null");

        List<String> accountNumbers = dashboard.getAccountNumbers();
        assertTrue(accountNumbers != null && accountNumbers.size() > 0,
                "Expected at least one account (count > 0)");

        dashboard.clickTransferFunds();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        assertTrue(transferPage.isLoaded(), "Transfer Funds page should load when link is clicked (link works)");
    }
}
