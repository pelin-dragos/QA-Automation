package com.qa.automation.project16.ui;

import com.qa.automation.project16.config.TestConfig;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page abstraction for viewing JSON in the browser (e.g. JSONPlaceholder URL).
 * <p>
 * Uses Selenium to navigate to the API URL, wait for load, and read the page body as JSON.
 * The "page" is the raw JSON response displayed in the browser. Do not log full response if it
 * contains sensitive data.
 */
public class JsonPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public JsonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getUiTimeoutSeconds()));
    }

    /**
     * Navigates to the given path (e.g. "posts/1") under the configured base URL and waits
     * for the body to be present.
     */
    public void navigateTo(String path) {
        String url = TestConfig.getApiBaseUrl() + (path.startsWith("/") ? path.substring(1) : path);
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    /**
     * Returns the text content suitable for JSON parsing. Tries body text, &lt;pre&gt;, then
     * extracts first { ... } or [ ... ] from page source (Firefox JSON viewer may wrap in HTML).
     */
    public String getBodyText() {
        String body = driver.findElement(By.tagName("body")).getText().trim();
        if (body.startsWith("{") || body.startsWith("[")) {
            return body;
        }
        try {
            var pre = driver.findElements(By.tagName("pre"));
            for (var el : pre) {
                if (el.isDisplayed()) {
                    String t = el.getText().trim();
                    if (t.startsWith("{") || t.startsWith("[")) return t;
                }
            }
        } catch (Exception ignored) {
        }
        try {
            Object jsResult = ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("return document.body.innerText || document.body.textContent || ''");
            if (jsResult != null) {
                String t = jsResult.toString().trim();
                if (t.startsWith("{") || t.startsWith("[")) return t;
            }
        } catch (Exception ignored) {
        }
        String source = driver.getPageSource();
        for (int tryStart = 0; tryStart < source.length(); tryStart++) {
            int start = source.indexOf('{', tryStart);
            if (start < 0) start = source.indexOf('[', tryStart);
            if (start < 0) break;
            char open = source.charAt(start);
            char close = open == '{' ? '}' : ']';
            int depth = 0;
            boolean inString = false;
            char stringChar = 0;
            int end = start;
            for (int i = start; i < source.length(); i++) {
                char c = source.charAt(i);
                if (!inString) {
                    if (c == '"' || c == '\'') {
                        inString = true;
                        stringChar = c;
                    } else if (c == open) depth++;
                    else if (c == close) {
                        depth--;
                        if (depth == 0) {
                            end = i + 1;
                            break;
                        }
                    }
                } else if (c == '\\' && i + 1 < source.length()) {
                    i++;
                } else if (c == stringChar) {
                    inString = false;
                }
            }
            if (end > start) {
                String extracted = source.substring(start, end)
                        .replace("\\u0022", "\"")
                        .replace("&quot;", "\"");
                if (extracted.contains("\"id\"") || extracted.contains("\"title\"") || extracted.contains("id") && extracted.contains("title")) {
                    try {
                        JsonPath.from(extracted);
                        return extracted;
                    } catch (Exception ignored) {
                    }
                }
            }
            tryStart = start;
        }
        return body;
    }

    /**
     * Parses the current page body as JSON and returns a JsonPath for assertions. Returns null
     * if the body is not valid JSON.
     */
    public JsonPath getBodyAsJsonPath() {
        try {
            String body = getBodyText();
            if (body == null || body.isEmpty()) return null;
            return JsonPath.from(body);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns true if the page body contains valid JSON with the given key, or the key name
     * appears in the page text (e.g. Firefox JSON viewer renders keys visibly).
     */
    public boolean hasJsonKey(String key) {
        JsonPath jp = getBodyAsJsonPath();
        if (jp != null) {
            try {
                Object val = jp.get(key);
                return val != null;
            } catch (Exception ignored) {
            }
        }
        String text = driver.findElement(By.tagName("body")).getText();
        return text != null && (text.contains("\"" + key + "\"") || text.contains(key));
    }
}
