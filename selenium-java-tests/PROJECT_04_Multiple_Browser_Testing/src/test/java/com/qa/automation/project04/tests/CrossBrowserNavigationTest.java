package com.qa.automation.project04.tests;

import com.qa.automation.project04.base.BaseTest;
import com.qa.automation.project04.base.Browser;
import com.qa.automation.project04.pages.TheInternetPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Cross-browser navigation tests (The Internet). Each test runs on Firefox, Chrome, and Edge.
 */
public class CrossBrowserNavigationTest extends BaseTest {

    @ParameterizedTest(name = "{0}")
    @EnumSource(Browser.class)
    void shouldNavigateToHomepage(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("");
            assertTrue(page.isPageLoaded(), "Page should be loaded");
            assertTrue(page.getCurrentUrl().contains("the-internet.herokuapp.com"), "URL should contain the-internet");
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0} - path: {1}")
    @EnumSource(Browser.class)
    void shouldNavigateToPage_homepage(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("");
            assertTrue(page.isPageLoaded());
            assertTrue(page.getCurrentUrl().contains("the-internet.herokuapp.com"));
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0} - login")
    @EnumSource(Browser.class)
    void shouldNavigateToPage_login(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("login");
            assertTrue(page.isPageLoaded());
            assertTrue(page.getCurrentUrl().contains("login"));
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0} - checkboxes")
    @EnumSource(Browser.class)
    void shouldNavigateToPage_checkboxes(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("checkboxes");
            assertTrue(page.isPageLoaded());
            assertTrue(page.getCurrentUrl().contains("checkboxes"));
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0} - dropdown")
    @EnumSource(Browser.class)
    void shouldNavigateToPage_dropdown(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("dropdown");
            assertTrue(page.isPageLoaded());
            assertTrue(page.getCurrentUrl().contains("dropdown"));
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Browser.class)
    void shouldHaveVisiblePageElements(Browser browser) {
        WebDriver driver = createDriver(browser);
        try {
            TheInternetPage page = new TheInternetPage(driver);
            page.navigateTo("");
            assertTrue(page.isPageLoaded());
            assertTrue(page.isElementVisible("body"), "Body should be visible");
            assertFalse(page.getAllLinks().isEmpty(), "Page should have links");
        } finally {
            driver.quit();
        }
    }
}
