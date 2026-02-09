package com.qa.automation.project04.pages;

import com.qa.automation.project04.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for The Internet (the-internet.herokuapp.com) — cross-browser navigation.
 */
public class TheInternetPage {

    private final WebDriver driver;
    private final String baseUrl;
    private final WebDriverWait wait;

    public TheInternetPage(WebDriver driver) {
        this(driver, TestConfig.getTheInternetBaseUrl());
    }

    public TheInternetPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void navigateTo(String path) {
        String url = baseUrl + (path != null && !path.isEmpty() ? path : "");
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1, h2, h3")));
                return true;
            } catch (Exception e) {
                return driver.findElement(By.tagName("body")).isDisplayed();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public List<String> getAllLinks() {
        return driver.findElements(By.cssSelector("a")).stream()
            .map(WebElement::getText)
            .filter(t -> t != null && !t.isBlank())
            .map(String::trim)
            .collect(Collectors.toList());
    }

    public boolean isElementVisible(String selector) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
