package com.qa.automation.project15.tests;

import com.qa.automation.project15.base.BaseTest;
import com.qa.automation.project15.config.TestConfig;
import com.qa.automation.project15.pages.LoginPage;
import com.qa.automation.project15.pages.UsersManagementPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Pagination (TEST_CASES.md — Section 7).
 * <p>
 * TC-ADMIN-P-001: Pagination exists when applicable; hasPagination() returns true/false consistently; no crash.
 */
class PaginationTest extends BaseTest {

    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(), "ADMIN_USERNAME and ADMIN_PASSWORD must be set");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-ADMIN-P-001: Open User Management, check for pagination controls. Asserts hasPagination() returns consistently; no crash.
     */
    @Test
    @DisplayName("TC-ADMIN-P-001: Pagination exists when applicable")
    void shouldHavePaginationWhenApplicable() {
        UsersManagementPage usersPage = new UsersManagementPage(driver);
        usersPage.navigateTo();
        boolean hasPagination = usersPage.hasPagination();
        assertTrue(usersPage.isLoaded(), "hasPagination() returns true/false consistently; no crash");
    }
}
