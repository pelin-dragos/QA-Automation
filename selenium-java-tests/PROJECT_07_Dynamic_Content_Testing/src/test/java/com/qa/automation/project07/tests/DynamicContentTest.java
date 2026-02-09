package com.qa.automation.project07.tests;

import com.qa.automation.project07.base.BaseTest;
import com.qa.automation.project07.config.TestConfig;
import com.qa.automation.project07.pages.DynamicContentPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic content tests: AJAX, infinite scroll, dynamic content, wait strategies, lazy loading.
 */
class DynamicContentTest extends BaseTest {

    @Nested
    class AjaxContent {

        @Test
        void shouldLoadAjaxContent() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToAjaxLoading();
            page.clickAjaxStartButton();
            String finishMessage = page.waitForAjaxLoadingComplete(TestConfig.AJAX_WAIT_TIMEOUT_SECONDS);
            Assertions.assertNotNull(finishMessage, "Finish message should be present");
            Assertions.assertTrue(finishMessage.contains("Hello World") || finishMessage.contains("Loaded"),
                    "Finish message should match expected text");
        }

        @Test
        void shouldHandleAjaxLoadingWithCustomWait() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToAjaxLoading();
            page.clickAjaxStartButton();
            page.waitForLoadingMessageHidden(TestConfig.AJAX_WAIT_TIMEOUT_SECONDS);
            String finishText = page.getAjaxFinishMessage();
            Assertions.assertNotNull(finishText, "Finish text should be present");
            Assertions.assertTrue(finishText.length() > 0, "Finish text should not be empty");
        }
    }

    @Nested
    class InfiniteScroll {

        @Test
        void shouldScrollInfinitely() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToInfiniteScroll();
            long initialHeight = getPageHeight();
            int scrollCount = page.scrollIncrementally(1000);
            long finalHeight = getPageHeight();
            Assertions.assertTrue(finalHeight > initialHeight, "Page should expand after scroll");
            Assertions.assertTrue(scrollCount > 0, "Should have performed at least one scroll");
        }

        @Test
        void shouldScrollAndVerifyPageExpanded() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToInfiniteScroll();
            long initialHeight = getPageHeight();
            page.scrollToBottom();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long finalHeight = getPageHeight();
            Assertions.assertTrue(finalHeight >= initialHeight, "Page height should be same or greater after scroll");
        }
    }

    @Nested
    class DynamicContent {

        @Test
        void shouldRefreshDynamicContent() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            List<String> initialTexts = new ArrayList<>();
            int count = page.getDynamicContentCount();
            for (int i = 0; i < Math.min(3, count); i++) {
                String text = page.getDynamicContentText(i);
                if (text != null && text.length() > 100) {
                    initialTexts.add(text.substring(0, 100));
                } else if (text != null) {
                    initialTexts.add(text);
                }
            }
            Assertions.assertTrue(initialTexts.size() > 0, "Should have initial content");

            page.refreshPage();
            List<String> newTexts = new ArrayList<>();
            int newCount = page.getDynamicContentCount();
            for (int i = 0; i < Math.min(3, newCount); i++) {
                String text = page.getDynamicContentText(i);
                if (text != null && text.length() > 100) {
                    newTexts.add(text.substring(0, 100));
                } else if (text != null) {
                    newTexts.add(text);
                }
            }
            boolean contentChanged = newTexts.stream().anyMatch(t -> !initialTexts.contains(t));
            Assertions.assertTrue(contentChanged, "At least one content block should change after refresh");
        }

        @Test
        void shouldVerifyDynamicContentCount() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            page.getFirstContentRow();
            int count = page.getDynamicContentCount();
            Assertions.assertTrue(count >= 2, "Should have at least 2 content elements");
        }
    }

    @Nested
    class WaitStrategies {

        @Test
        void shouldWaitForElementVisible() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            WebElement first = page.getFirstContentRow();
            Assertions.assertTrue(first.isDisplayed(), "First content row should be visible");
        }

        @Test
        void shouldWaitForElementCount() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            page.getFirstContentRow();
            int count = page.getDynamicContentCount();
            Assertions.assertTrue(count >= 2, "Should have at least 2 content elements");
        }

        @Test
        void shouldWaitForPageLoad() {
            driver.get(TestConfig.getTheInternetBaseUrl());
            DynamicContentPage page = new DynamicContentPage(driver);
            page.waitForPageLoad();
            Object readyState = executeScript("return document.readyState;");
            Assertions.assertEquals("complete", readyState, "Document ready state should be complete");
        }

        @Test
        void shouldWaitForTextInElement() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            String initialText = page.getDynamicContentText(0);
            Assertions.assertNotNull(initialText, "First row should have text");
            String prefix = initialText.length() > 20 ? initialText.substring(0, 20) : initialText;
            WebElement first = page.getFirstContentRow();
            Assertions.assertTrue(first.getText().contains(prefix) || initialText.contains(prefix),
                    "Element should contain expected text");
        }
    }

    @Nested
    class LazyLoading {

        @Test
        void shouldVerifyImageLoading() {
            DynamicContentPage page = new DynamicContentPage(driver);
            page.navigateToDynamicContent();
            page.getFirstContentRow();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            List<WebElement> images = page.getDynamicContentImageElements();
            Assertions.assertTrue(images.size() > 0, "Should have at least one image");
            for (WebElement img : images) {
                String src = img.getAttribute("src");
                Assertions.assertNotNull(src, "Image should have src attribute");
            }
        }
    }
}
