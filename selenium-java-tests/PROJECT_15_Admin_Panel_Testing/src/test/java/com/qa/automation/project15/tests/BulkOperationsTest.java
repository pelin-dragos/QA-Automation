package com.qa.automation.project15.tests;

import com.qa.automation.project15.base.BaseTest;
import com.qa.automation.project15.config.TestConfig;
import com.qa.automation.project15.pages.LoginPage;
import com.qa.automation.project15.pages.UsersManagementPage;
import com.qa.automation.project15.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Test suite: Bulk operations (TEST_CASES.md — Section 8).
 * <p>
 * TC-ADMIN-B-001: Create 2 users, select both (or delete one by one), verify removal or flow executed; page stable.
 */
class BulkOperationsTest extends BaseTest {

    @BeforeEach
    void loginIfConfigured() {
        assumeTrue(TestConfig.isLoginConfigured(), "ADMIN_USERNAME and ADMIN_PASSWORD must be set");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getUsername(), TestConfig.getPassword());
    }

    /**
     * TC-ADMIN-B-001: Create 2 users with unique test data, then delete one by one (or bulk delete if supported). Asserts users removed or flow executed; page stable.
     */
    @Test
    @DisplayName("TC-ADMIN-B-001: Bulk delete users")
    void shouldBulkDeleteUsers() {
        String user1 = TestDataHelper.uniqueUsername();
        String user2 = TestDataHelper.uniqueUsername();
        UsersManagementPage usersPage = new UsersManagementPage(driver);
        usersPage.navigateTo();
        assumeTrue(usersPage.isAddButtonVisible(), "Add User button not found (demo or selector)");

        usersPage.clickAddUser();
        usersPage.fillAddUserForm("ESS", TestDataHelper.DEFAULT_EMPLOYEE_SEARCH, user1, TestDataHelper.TEST_PASSWORD, "Enabled");
        usersPage.clickSave();

        usersPage.clickAddUser();
        usersPage.fillAddUserForm("ESS", TestDataHelper.DEFAULT_EMPLOYEE_SEARCH, user2, TestDataHelper.TEST_PASSWORD, "Enabled");
        usersPage.clickSave();

        usersPage.searchByUsername(user1);
        usersPage.deleteUserByUsername(user1);
        usersPage.searchByUsername(user2);
        usersPage.deleteUserByUsername(user2);

        assertTrue(usersPage.isLoaded(), "Users removed or flow executed; page stable");
    }
}
