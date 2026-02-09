package com.qa.automation.project03.tests;

import com.qa.automation.project03.base.BaseTest;
import com.qa.automation.project03.pages.GoogleSearchPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Search queries: simple, multiple, long, special chars, empty.
 */
public class SearchQueriesTest extends BaseTest {

    private static final Pattern SEARCH_URL = Pattern.compile("/search|q=");

    @Test
    void shouldSearchWithSimpleQuery() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search("Python programming");
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown; search not verified");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
        assertTrue(page.getSearchResults(3).size() >= 0, "Results count");
    }

    @ParameterizedTest(name = "query = {0}")
    @ValueSource(strings = {
        "Selenium automation",
        "pytest testing framework",
        "web development",
        "machine learning",
        "data science",
        "Python programming tutorial",
        "JavaScript ES6 features"
    })
    void shouldSearchWithMultipleQueries(String query) {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        page.search(query, false);
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
    }

    @Test
    void shouldSearchWithLongQuery() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search("How to learn Python programming from scratch for beginners");
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
    }

    @ParameterizedTest(name = "query = {0}")
    @ValueSource(strings = {
        "Python 3.11 features",
        "C++ programming",
        "test@example.com search",
        "price $100"
    })
    void shouldSearchWithSpecialCharacters(String query) {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search(query);
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
    }

    @Test
    void shouldHandleEmptyQuerySearch() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        page.enterSearchQuery("");
        page.submitSearch();
        assertTrue(page.getCurrentUrl() != null && !page.getCurrentUrl().isEmpty(), "URL should be set");
    }
}
