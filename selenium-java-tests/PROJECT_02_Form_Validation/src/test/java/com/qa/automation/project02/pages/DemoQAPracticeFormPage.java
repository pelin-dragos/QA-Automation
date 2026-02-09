package com.qa.automation.project02.pages;

import com.qa.automation.project02.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for DemoQA Automation Practice Form.
 * <p>
 * Used for required-field and phone validation tests. Locators: #firstName, #lastName, #userEmail,
 * #userNumber, gender radio, #submit. Submit is performed via JavaScript to avoid overlay issues.
 */
public class DemoQAPracticeFormPage {

    private static final By FIRST_NAME = By.id("firstName");
    private static final By LAST_NAME = By.id("lastName");
    private static final By EMAIL = By.id("userEmail");
    private static final By GENDER_MALE = By.cssSelector("input#gender-radio-1");
    private static final By MOBILE = By.id("userNumber");
    private static final By SUBMIT = By.id("submit");
    private static final By VALIDATION_ERROR = By.cssSelector(".invalid-feedback, .text-danger");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DemoQAPracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /** Navigates to the Practice Form URL and waits until the submit button is visible. */
    public void navigateTo() {
        driver.get(TestConfig.getPracticeFormUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT));
    }

    /** Clears and fills First Name; no-op if string is null or empty. */
    public void enterFirstName(String s) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Clears and fills Last Name; no-op if string is null or empty. */
    public void enterLastName(String s) {
        WebElement el = driver.findElement(LAST_NAME);
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Clears and fills Email; no-op if string is null or empty. */
    public void enterEmail(String s) {
        WebElement el = driver.findElement(EMAIL);
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Selects the Male gender radio via JavaScript (avoids interception by other elements). */
    public void selectGender() {
        WebElement radio = driver.findElement(GENDER_MALE);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
    }

    /** Clears and fills Mobile (User Number); no-op if string is null or empty. */
    public void enterMobile(String s) {
        WebElement el = driver.findElement(MOBILE);
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Scrolls submit into view and clicks via JavaScript (avoids overlay/ads). */
    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", btn);
    }

    /** Returns true if the element at the given locator has the HTML5 required attribute. */
    public boolean isFieldRequired(By locator) {
        WebElement el = driver.findElement(locator);
        String req = el.getAttribute("required");
        return req != null && !req.isEmpty();
    }

    /** Returns true if the element at the given locator passes HTML5 validity (validity.valid). */
    public boolean isFieldValid(By locator) {
        WebElement el = driver.findElement(locator);
        Object valid = ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].validity.valid;", el);
        return Boolean.TRUE.equals(valid);
    }

    /** Returns visible validation error messages (invalid-feedback or text-danger). */
    public List<String> getValidationErrors() {
        List<WebElement> errors = driver.findElements(VALIDATION_ERROR);
        List<String> list = new ArrayList<>();
        for (WebElement e : errors) {
            if (e.isDisplayed()) {
                String t = e.getText();
                if (t != null && !t.trim().isEmpty()) list.add(t.trim());
            }
        }
        return list;
    }

    public WebElement getFirstNameField() { return driver.findElement(FIRST_NAME); }
    public WebElement getLastNameField() { return driver.findElement(LAST_NAME); }
    public WebElement getEmailField() { return driver.findElement(EMAIL); }
    public WebElement getMobileField() { return driver.findElement(MOBILE); }
}
