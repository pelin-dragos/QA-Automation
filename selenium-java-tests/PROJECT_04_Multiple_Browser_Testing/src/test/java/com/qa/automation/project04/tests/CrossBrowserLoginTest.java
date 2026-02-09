package com.qa.automation.project04.tests;

import com.qa.automation.project04.base.BaseTest;
import com.qa.automation.project04.base.Browser;
import com.qa.automation.project04.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Cross-browser login tests (Sauce Demo). Each test runs on Firefox, Chrome, and Edge.
 */
public class CrossBrowserLoginTest extends BaseTest {

    private WebDriver driver;

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Browser.class)
    void shouldLoginWithValidCredentials(Browser browser) {
        driver = createDriver(browser);
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            assertTrue(loginPage.isLoaded(), "Login page should be loaded");
            loginPage.login("standard_user", "secret_sauce");
            assertTrue(loginPage.getCurrentUrl().contains("inventory"), "Should be on inventory page");
        } finally {
            if (driver != null) driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest(name = "{0} - {1}")
    @EnumSource(Browser.class)
    void shouldLoginWithUser_standard_user(Browser browser) {
        driver = createDriver(browser);
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("standard_user", "secret_sauce");
            assertTrue(loginPage.getCurrentUrl().contains("inventory"));
        } finally {
            if (driver != null) driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest(name = "{0} - problem_user")
    @EnumSource(Browser.class)
    void shouldLoginWithUser_problem_user(Browser browser) {
        driver = createDriver(browser);
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("problem_user", "secret_sauce");
            assertTrue(loginPage.getCurrentUrl().contains("inventory"));
        } finally {
            if (driver != null) driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest(name = "{0} - performance_glitch_user")
    @EnumSource(Browser.class)
    void shouldLoginWithUser_performance_glitch_user(Browser browser) {
        driver = createDriver(browser);
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("performance_glitch_user", "secret_sauce");
            assertTrue(loginPage.getCurrentUrl().contains("inventory"));
        } finally {
            if (driver != null) driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Browser.class)
    void shouldShowErrorMessageWithInvalidCredentials(Browser browser) {
        driver = createDriver(browser);
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login("invalid_user", "invalid_password");
            String errorMessage = loginPage.getErrorMessage();
            assertNotNull(errorMessage, "Error message should be displayed");
            assertTrue(errorMessage.length() > 0, "Error message should not be empty");
            String lower = errorMessage.toLowerCase();
            assertTrue(
                lower.contains("error") || lower.contains("invalid") || lower.contains("incorrect")
                    || lower.contains("do not match") || lower.contains("epic sadface") || lower.contains("username and password"),
                "Error message should contain expected keywords"
            );
        } finally {
            if (driver != null) driver.quit();
            driver = null;
        }
    }
}
