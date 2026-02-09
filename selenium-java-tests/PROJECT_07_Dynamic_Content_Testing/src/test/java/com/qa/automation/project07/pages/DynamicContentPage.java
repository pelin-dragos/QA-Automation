package com.qa.automation.project07.pages;

import com.qa.automation.project07.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for The Internet: Dynamic Content, AJAX Loading, Infinite Scroll.
 */
public class DynamicContentPage {

    private static final By LINK_DYNAMIC_CONTENT = By.linkText("Dynamic Content");
    private static final By LINK_DYNAMIC_LOADING = By.linkText("Dynamic Loading");
    private static final By LINK_EXAMPLE_1 = By.linkText("Example 1: Element on page that is hidden");
    private static final By LINK_INFINITE_SCROLL = By.linkText("Infinite Scroll");
    private static final By CONTENT_ROWS = By.cssSelector(".row .large-10.columns");
    private static final By CONTENT_IMAGES = By.cssSelector(".row .large-10.columns img");
    private static final By AJAX_START_BUTTON = By.cssSelector("#start button");
    private static final By AJAX_LOADING = By.id("loading");
    private static final By AJAX_FINISH = By.id("finish");

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String baseUrl;

    public DynamicContentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        this.baseUrl = TestConfig.getTheInternetBaseUrl();
    }

    public void navigateToDynamicContent() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.elementToBeClickable(LINK_DYNAMIC_CONTENT)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(CONTENT_ROWS));
    }

    public void navigateToAjaxLoading() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.elementToBeClickable(LINK_DYNAMIC_LOADING)).click();
        wait.until(ExpectedConditions.elementToBeClickable(LINK_EXAMPLE_1)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AJAX_START_BUTTON));
    }

    public void navigateToInfiniteScroll() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.elementToBeClickable(LINK_INFINITE_SCROLL)).click();
        wait.until(ExpectedConditions.urlContains("infinite_scroll"));
        waitForPageLoad();
    }

    public void waitForPageLoad() {
        wait.until(webDriver ->
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public List<WebElement> getDynamicContentRowElements() {
        return new ArrayList<>(driver.findElements(CONTENT_ROWS));
    }

    public int getDynamicContentCount() {
        return driver.findElements(CONTENT_ROWS).size();
    }

    public String getDynamicContentText(int index) {
        List<WebElement> rows = driver.findElements(CONTENT_ROWS);
        if (index < 0 || index >= rows.size()) return null;
        String text = rows.get(index).getText();
        return text != null ? text.trim() : null;
    }

    public List<WebElement> getDynamicContentImageElements() {
        return new ArrayList<>(driver.findElements(CONTENT_IMAGES));
    }

    public void clickAjaxStartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(AJAX_START_BUTTON)).click();
    }

    /**
     * Wait for AJAX loading to complete: loading message hidden, finish message visible.
     */
    public String waitForAjaxLoadingComplete(int timeoutSeconds) {
        WebDriverWait ajaxWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        ajaxWait.until(ExpectedConditions.invisibilityOfElementLocated(AJAX_LOADING));
        WebElement finish = ajaxWait.until(ExpectedConditions.visibilityOfElementLocated(AJAX_FINISH));
        return finish.getText();
    }

    public String getAjaxFinishMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement finish = shortWait.until(ExpectedConditions.visibilityOfElementLocated(AJAX_FINISH));
            return finish != null ? finish.getText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void scrollToBottom() {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scroll incrementally until height stops changing or max 10 scrolls.
     */
    public int scrollIncrementally(int scrollPauseMs) {
        int scrollCount = 0;
        long previousHeight = getPageHeight();
        long currentHeight = previousHeight;
        final int maxScrolls = 10;

        while (scrollCount < maxScrolls) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(scrollPauseMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            currentHeight = getPageHeight();
            scrollCount++;
            if (currentHeight == previousHeight) break;
            previousHeight = currentHeight;
        }
        return scrollCount;
    }

    private long getPageHeight() {
        Object h = ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return document.body.scrollHeight;");
        return h != null ? ((Number) h).longValue() : 0;
    }

    public void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(CONTENT_ROWS));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement getFirstContentRow() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CONTENT_ROWS));
    }

    public boolean isLoadingMessageVisible() {
        try {
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
            return w.until(ExpectedConditions.visibilityOfElementLocated(AJAX_LOADING)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForLoadingMessageHidden(int timeoutSeconds) {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        w.until(ExpectedConditions.invisibilityOfElementLocated(AJAX_LOADING));
    }
}
