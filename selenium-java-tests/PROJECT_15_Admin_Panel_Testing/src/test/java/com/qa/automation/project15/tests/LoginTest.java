package com.qa.automation.project15.tests;

import com.qa.automation.project15.base.BaseTest;
import com.qa.automation.project15.config.TestConfig;
import com.qa.automation.project15.pages.LoginPage;
import com.qa.automation.project15.pages.UsersManagementPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Admin login (TEST_CASES.md — Section 1).
 * <p>
 * TC-ADMIN-LOGIN-001: Successful login as admin; credentials from config/env; skipped when not set.
 */
class LoginTest extends BaseTest {

    /**
     * TC-ADMIN-LOGIN-001: Navigate to login, enter admin username and password from config,
     * click Login. Asserts login succeeds: dashboard accessible (Users management can be opened).
     */
    @Test
    @DisplayName("TC-ADMIN-LOGIN-001: Successful login as admin")
    void shouldLoginAsAdmin() {
        assumeTrue(TestConfig.isLoginConfigured(),
                "ADMIN_USERNAME and ADMIN_PASSWORD must be set to run this test");
        String username = TestConfig.getUsername();
        String password = TestConfig.getPassword();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(username, password);

        UsersManagementPage usersPage = new UsersManagementPage(driver);
        usersPage.navigateTo();
        assertTrue(usersPage.isLoaded(), "Dashboard accessible: User Management / Users list loaded");
    }
}
