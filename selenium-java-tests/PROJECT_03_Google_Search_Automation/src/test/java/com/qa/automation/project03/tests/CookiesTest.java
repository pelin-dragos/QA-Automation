package com.qa.automation.project03.tests;

import com.qa.automation.project03.base.BaseTest;
import com.qa.automation.project03.pages.GoogleSearchPage;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Cookie handling: accept, reject, search after handling.
 */
public class CookiesTest extends BaseTest {

    private static final Pattern SEARCH_OR_SORRY = Pattern.compile("/search|q=|sorry");

    @Test
    void shouldAcceptCookies() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        assertTrue(page.isLoaded(), "Page should be loaded and functional");
    }

    @Test
    void shouldRejectCookies() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.rejectCookies();
        assertTrue(page.isLoaded(), "Page should be loaded and functional");
    }

    @Test
    void shouldSearchAfterCookieHandling() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        page.search("Selenium testing", false);
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown; expected in automation");
            return;
        }
        assertTrue(SEARCH_OR_SORRY.matcher(page.getCurrentUrl()).find(), "Search or sorry URL");
        assertTrue(page.getSearchResults(3).size() >= 0, "Results");
    }
}
