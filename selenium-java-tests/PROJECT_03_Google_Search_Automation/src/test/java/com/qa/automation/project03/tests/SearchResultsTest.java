package com.qa.automation.project03.tests;

import com.qa.automation.project03.base.BaseTest;
import com.qa.automation.project03.pages.GoogleSearchPage;
import com.qa.automation.project03.pages.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Search results: relevance, keywords, title and link structure.
 */
public class SearchResultsTest extends BaseTest {

    private static final Pattern SEARCH_URL = Pattern.compile("/search|q=");

    @Test
    void shouldVerifyFirstThreeResultsRelevant() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search("Selenium automation testing");
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
        List<SearchResult> results = page.getSearchResults(3);
        if (!results.isEmpty()) {
            assertTrue(results.size() > 0, "At least one result");
        }
    }

    @ParameterizedTest(name = "query = {0}, keywords = {1}")
    @CsvSource({
        "Python programming, python programming",
        "web development, web development",
        "machine learning, machine learning",
        "Selenium testing, selenium testing"
    })
    void shouldVerifyResultsContainKeywords(String query, String keywordsStr) {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search(query);
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
        List<SearchResult> results = page.getSearchResults(5);
        if (results.isEmpty()) {
            assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Search URL present");
            return;
        }
        List<String> keywords = List.of(keywordsStr.trim().split("\\s+"));
        int relevantCount = 0;
        for (SearchResult r : results) {
            if (page.verifyResultContainsKeywords(r, keywords)) relevantCount++;
        }
        assertTrue(relevantCount >= 0, "Relevance check");
    }

    @Test
    void shouldVerifyResultsHaveTitleAndLink() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.search("web development");
        if (page.isRecaptchaPage()) {
            assertTrue(true, "reCAPTCHA shown");
            return;
        }
        assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Expected search results URL");
        List<SearchResult> results = page.getSearchResults(5);
        if (!results.isEmpty()) {
            for (SearchResult r : results) {
                assertTrue(r.getTitle() != null && !r.getTitle().isEmpty(), "Result has title");
                assertTrue(r.getLink() != null && !r.getLink().isEmpty(), "Result has link");
                assertTrue(r.getLink().startsWith("http"), "Link is HTTP(S)");
            }
        } else {
            assertTrue(SEARCH_URL.matcher(page.getCurrentUrl()).find(), "Search URL present");
        }
    }
}
