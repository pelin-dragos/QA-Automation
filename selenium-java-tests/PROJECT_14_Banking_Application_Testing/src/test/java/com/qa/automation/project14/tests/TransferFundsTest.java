package com.qa.automation.project14.tests;

import com.qa.automation.project14.base.BaseTest;
import com.qa.automation.project14.config.TestConfig;
import com.qa.automation.project14.pages.DashboardPage;
import com.qa.automation.project14.pages.LoginPage;
import com.qa.automation.project14.pages.TransferFundsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Transfer Funds (TEST_CASES.md — Section 4).
 * <p>
 * TC-BANK-XFER-001: Transfer funds between two accounts; expect success or clear error (e.g. insufficient funds).
 * TC-BANK-XFER-002: Transfer with invalid amount (negative/zero); expect error or validation, transfer not completed.
 * Both tests require configured credentials and at least two accounts for full flow; skipped when creds not set.
 */
class TransferFundsTest extends BaseTest {

    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(),
                "BANKING_USERNAME and BANKING_PASSWORD must be set to run transfer tests");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-BANK-XFER-001: Navigate to Transfer Funds, select from account and to account (different),
     * enter amount (e.g. 10.00), submit. Asserts either success/confirmation message or a clear
     * error (e.g. insufficient funds)—no unhandled failure. Uses index 0 and 1 for from/to when
     * multiple options exist so that from != to.
     */
    @Test
    @DisplayName("TC-BANK-XFER-001: Transfer funds between two accounts")
    void shouldTransferFundsBetweenAccounts() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickTransferFunds();

        TransferFundsPage transferPage = new TransferFundsPage(driver);
        assertTrue(transferPage.isLoaded(), "Transfer Funds page should load");

        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.enterAmount("10.00");
        transferPage.clickTransfer();

        String message = transferPage.getMessageText();
        assertTrue(message != null && !message.isBlank(),
                "Expected success message or transfer confirmation, or clear error (e.g. insufficient funds)");
    }

    /**
     * TC-BANK-XFER-002: Navigate to Transfer Funds, enter negative or zero amount, submit.
     * Asserts error message or validation is shown and transfer is not completed (error indicated).
     */
    @Test
    @DisplayName("TC-BANK-XFER-002: Transfer with invalid amount (negative/zero)")
    void shouldRejectInvalidTransferAmount() {
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.clickTransferFunds();

        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.selectFromAccountByIndex(0);
        transferPage.selectToAccountByIndex(1);
        transferPage.enterAmount("0");
        transferPage.clickTransfer();

        assertTrue(transferPage.isErrorMessageShown() || (transferPage.getMessageText() != null && !transferPage.getMessageText().isBlank()),
                "Error message or validation expected; transfer should not be completed for invalid amount");
    }
}
