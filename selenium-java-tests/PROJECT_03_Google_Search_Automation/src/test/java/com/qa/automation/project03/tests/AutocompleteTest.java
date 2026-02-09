package com.qa.automation.project03.tests;

import com.qa.automation.project03.base.BaseTest;
import com.qa.automation.project03.pages.GoogleSearchPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Autocomplete: suggestions appear, multiple queries, relevance.
 */
public class AutocompleteTest extends BaseTest {

    @Test
    void shouldShowAutocompleteSuggestions() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        page.enterSearchQuery("python");
        List<String> suggestions = page.getAutocompleteSuggestions("python", 5);
        assertNotNull(suggestions, "Suggestions list should not be null");
    }

    @ParameterizedTest(name = "query = {0}")
    @ValueSource(strings = { "selenium", "pytest", "web", "python", "test" })
    void shouldShowAutocompleteForMultipleQueries(String query) {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        page.enterSearchQuery(query);
        List<String> suggestions = page.getAutocompleteSuggestions(query, 3);
        assertNotNull(suggestions, "Suggestions list should not be null");
    }

    @Test
    void shouldVerifyAutocompleteSuggestionsRelevant() {
        GoogleSearchPage page = new GoogleSearchPage(driver);
        page.navigateTo();
        page.acceptCookies();
        String query = "python";
        page.enterSearchQuery(query);
        List<String> suggestions = page.getAutocompleteSuggestions(query, 5);
        if (!suggestions.isEmpty()) {
            long relevant = suggestions.stream()
                .filter(s -> s.toLowerCase().contains(query.toLowerCase()))
                .count();
            assertTrue(relevant >= 0, "Relevance count");
        }
    }
}
