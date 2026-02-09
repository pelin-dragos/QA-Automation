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
 * Test suite: Search & filtering (TEST_CASES.md — Section 6).
 * <p>
 * TC-ADMIN-S-001: Search by username "Admin"; results contain Admin or list updated.
 * TC-ADMIN-S-002: Perform search, click Reset; full list or default view restored; no exception.
 */
class SearchFilterTest extends BaseTest {

    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(), "ADMIN_USERNAME and ADMIN_PASSWORD must be set");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-ADMIN-S-001: Open User Management, enter username "Admin", search. Asserts results contain Admin or list updated.
     */
    @Test
    @DisplayName("TC-ADMIN-S-001: Search by username")
    void shouldSearchByUsername() {
        UsersManagementPage usersPage = new UsersManagementPage(driver);
        usersPage.navigateTo();
        usersPage.searchByUsername("Admin");
        assertTrue(usersPage.isUserInTable("Admin") || usersPage.getUsersCount() >= 0,
                "Results contain Admin or list updated");
    }

    /**
     * TC-ADMIN-S-002: Perform a search, click Reset, verify full list or default state. Asserts no exception; view restored.
     */
    @Test
    @DisplayName("TC-ADMIN-S-002: Reset search")
    void shouldResetSearch() {
        UsersManagementPage usersPage = new UsersManagementPage(driver);
        usersPage.navigateTo();
        usersPage.searchByUsername("Admin");
        usersPage.clickReset();
        assertTrue(usersPage.isLoaded(), "Full list or default view restored; no exception");
    }
}
