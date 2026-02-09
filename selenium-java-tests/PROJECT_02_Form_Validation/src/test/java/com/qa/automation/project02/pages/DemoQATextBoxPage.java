package com.qa.automation.project02.pages;

import com.qa.automation.project02.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for DemoQA Text Box page.
 * <p>
 * Used for email validation tests. Locators: #userName, #userEmail, #currentAddress, #submit, #output.
 * Submit is performed via JavaScript (scroll into view + click) to avoid overlay/iframe issues.
 */
public class DemoQATextBoxPage {

    private static final By FULL_NAME = By.id("userName");
    private static final By EMAIL = By.id("userEmail");
    private static final By CURRENT_ADDRESS = By.id("currentAddress");
    private static final By SUBMIT = By.id("submit");
    private static final By OUTPUT = By.id("output");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DemoQATextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /** Navigates to the Text Box URL and waits until the submit button is visible. */
    public void navigateTo() {
        driver.get(TestConfig.getTextBoxUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT));
    }

    /** Clears and fills the Full Name field; no-op if name is null or empty. */
    public void enterFullName(String name) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(FULL_NAME));
        el.clear();
        if (name != null && !name.isEmpty()) el.sendKeys(name);
    }

    /** Clears and fills the Email field; no-op if email is null or empty. */
    public void enterEmail(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
        el.clear();
        if (email != null && !email.isEmpty()) el.sendKeys(email);
    }

    /** Clears and fills the Current Address field; no-op if address is null or empty. */
    public void enterCurrentAddress(String address) {
        WebElement el = driver.findElement(CURRENT_ADDRESS);
        el.clear();
        if (address != null && !address.isEmpty()) el.sendKeys(address);
    }

    /** Scrolls the submit button into view and clicks it via JavaScript (avoids overlay/ads). */
    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btn);
    }

    /** Returns true if the email field fails HTML5 validity (validity.valid is false). */
    public boolean isEmailInvalid() {
        WebElement el = driver.findElement(EMAIL);
        Object valid = ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].validity.valid;", el);
        return !Boolean.TRUE.equals(valid);
    }

    /** Returns the email input element for attribute or DOM checks. */
    public WebElement getEmailField() {
        return driver.findElement(EMAIL);
    }

    /** Returns true if the element at the given locator has the HTML5 required attribute. */
    public boolean isFieldRequired(By locator) {
        WebElement el = driver.findElement(locator);
        String req = el.getAttribute("required");
        return req != null && !req.isEmpty();
    }
}
