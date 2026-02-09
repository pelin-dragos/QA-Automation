package com.qa.automation.project03.pages;

import com.qa.automation.project03.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Page Object for Google Search: search box, cookies, results, autocomplete.
 */
public class GoogleSearchPage {

    private static final By SEARCH_BOX = By.cssSelector("textarea[name='q'], input[name='q']");
    private static final By RESULT_BLOCKS = By.cssSelector("div.g");
    private static final By RESULT_TITLE = By.cssSelector("h3");
    private static final By RESULT_LINK = By.cssSelector("a[href^='http']");
    private static final By AUTOCOMPLETE_LIST = By.cssSelector("ul[role='listbox'] li[role='presentation'], ul[role='listbox'] li");

    private static final String[] ACCEPT_COOKIES_TEXTS = {
        "Accept all", "Accept All", "Acceptă tot", "Accept"
    };
    private static final String[] REJECT_COOKIES_TEXTS = {
        "Reject all", "Reject All", "Respinge tot", "Reject"
    };

    private final WebDriver driver;
    private final String baseUrl;
    private final WebDriverWait wait;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.baseUrl = TestConfig.getBaseUrl();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void navigateTo() {
        driver.get(baseUrl);
        waitForSearchBox();
    }

    private WebElement waitForSearchBox() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BOX));
    }

    /** Accept cookies if popup is present. Returns true if a button was clicked. */
    public boolean acceptCookies() {
        return clickCookieButton(ACCEPT_COOKIES_TEXTS);
    }

    /** Reject cookies if popup is present. Returns true if a button was clicked. */
    public boolean rejectCookies() {
        return clickCookieButton(REJECT_COOKIES_TEXTS);
    }

    private boolean clickCookieButton(String[] buttonTexts) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (String text : buttonTexts) {
            try {
                By by = By.xpath("//button[contains(., '" + text + "')]");
                List<WebElement> buttons = driver.findElements(by);
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed()) {
                        btn.click();
                        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                        return true;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public void enterSearchQuery(String query) {
        WebElement box = waitForSearchBox();
        box.clear();
        if (query != null) {
            box.sendKeys(query);
        }
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void submitSearch() {
        WebElement box = waitForSearchBox();
        box.sendKeys(Keys.ENTER);
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            return url.contains("/search") || url.contains("?q=");
        });
        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** Navigate, optionally accept cookies, enter query, submit. */
    public void search(String query, boolean acceptCookiesFirst) {
        navigateTo();
        if (acceptCookiesFirst) {
            acceptCookies();
        }
        enterSearchQuery(query);
        submitSearch();
    }

    public void search(String query) {
        search(query, true);
    }

    public List<String> getAutocompleteSuggestions(String query, int maxSuggestions) {
        enterSearchQuery(query);
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        List<String> out = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AUTOCOMPLETE_LIST));
            List<WebElement> items = driver.findElements(AUTOCOMPLETE_LIST);
            int limit = Math.min(items.size(), maxSuggestions);
            for (int i = 0; i < limit; i++) {
                String t = items.get(i).getText();
                if (t != null && !t.isBlank()) {
                    out.add(t.trim());
                }
            }
        } catch (Exception ignored) {
        }
        return out;
    }

    public boolean isRecaptchaPage() {
        String url = driver.getCurrentUrl();
        if (url.contains("/sorry") || url.contains("sorry/index")) {
            return true;
        }
        try {
            return driver.findElements(By.xpath("//*[contains(text(),\"I'm not a robot\") or contains(text(),'unusual traffic')]")).stream()
                .anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    public List<SearchResult> getSearchResults(int maxResults) {
        if (isRecaptchaPage()) {
            return List.of();
        }
        String url = driver.getCurrentUrl();
        if (!url.contains("/search") && !url.contains("?q=")) {
            return List.of();
        }
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(RESULT_BLOCKS));
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3")));
            } catch (Exception e2) {
                return List.of();
            }
        }
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        List<SearchResult> results = new ArrayList<>();
        List<WebElement> blocks = driver.findElements(RESULT_BLOCKS);
        for (WebElement block : blocks) {
            if (results.size() >= maxResults) break;
            try {
                List<WebElement> h3s = block.findElements(RESULT_TITLE);
                List<WebElement> links = block.findElements(RESULT_LINK);
                String title = h3s.isEmpty() ? "" : h3s.get(0).getText();
                String link = links.isEmpty() ? "" : links.get(0).getAttribute("href");
                if (title.isEmpty() && (link == null || link.isEmpty())) continue;
                String snippet = "";
                try {
                    List<WebElement> spans = block.findElements(By.cssSelector("span, div[data-sncf]"));
                    if (!spans.isEmpty()) snippet = spans.get(0).getText();
                } catch (Exception ignored) {}
                results.add(new SearchResult(title, link != null ? link : "", snippet));
            } catch (Exception ignored) {
            }
        }
        return results;
    }

    public boolean verifyResultContainsKeywords(SearchResult result, List<String> keywords) {
        String text = (result.getTitle() + " " + result.getSnippet()).toLowerCase(Locale.ROOT);
        for (String kw : keywords) {
            if (text.contains(kw.toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

    public boolean verifyResultsRelevant(String query, int maxResults) {
        List<SearchResult> results = getSearchResults(maxResults);
        if (results.isEmpty()) return false;
        List<String> keywords = List.of(query.toLowerCase(Locale.ROOT).split("\\s+"));
        long relevant = results.stream()
            .filter(r -> verifyResultContainsKeywords(r, keywords))
            .count();
        return (double) relevant / results.size() >= 0.7;
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BOX));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
