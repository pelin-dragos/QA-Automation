package com.qa.automation.project14.pages;

import com.qa.automation.project14.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Transfer Funds page (e.g. ParaBank).
 * <p>
 * Provides methods to select from-account, to-account, enter amount, submit transfer,
 * and read success or error message. Uses explicit waits. Do not log amounts or account
 * numbers in reports if masking is required.
 */
public class TransferFundsPage {

    private static final By FROM_ACCOUNT = By.id("fromAccountId");
    private static final By TO_ACCOUNT = By.id("toAccountId");
    private static final By AMOUNT_INPUT = By.id("amount");
    private static final By TRANSFER_BUTTON = By.xpath("//input[@value='Transfer']");
    /** Success or error message after transfer (ParaBank: in .title or #rightPanel). */
    private static final By MESSAGE_AREA = By.id("rightPanel");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /** Waits until the transfer form is visible (from account dropdown). Used after navigating to Transfer Funds. */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(FROM_ACCOUNT));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Selects the "from" account by visible text (e.g. account number or "Account #12345"). */
    public void selectFromAccount(String visibleText) {
        WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(FROM_ACCOUNT));
        new Select(selectEl).selectByVisibleText(visibleText);
    }

    /** Selects the "to" account by visible text. */
    public void selectToAccount(String visibleText) {
        WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(TO_ACCOUNT));
        new Select(selectEl).selectByVisibleText(visibleText);
    }

    /** Selects the "from" account by option index (0-based). Use when option text varies. */
    public void selectFromAccountByIndex(int index) {
        WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(FROM_ACCOUNT));
        new Select(selectEl).selectByIndex(index);
    }

    /** Selects the "to" account by option index (0-based). Use when option text varies. */
    public void selectToAccountByIndex(int index) {
        WebElement selectEl = wait.until(ExpectedConditions.visibilityOfElementLocated(TO_ACCOUNT));
        new Select(selectEl).selectByIndex(index);
    }

    /** Clears and enters the transfer amount. Pass a string (e.g. "10.00") for negative tests use "0" or "-1". */
    public void enterAmount(String amount) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(AMOUNT_INPUT));
        field.clear();
        if (amount != null && !amount.isEmpty()) {
            field.sendKeys(amount);
        }
    }

    /** Clicks the Transfer button and waits for the result (message area updated). */
    public void clickTransfer() {
        wait.until(ExpectedConditions.elementToBeClickable(TRANSFER_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE_AREA));
    }

    /**
     * Returns the visible message text from the right panel after transfer (success or error).
     * Used to assert transfer completed or validation/error message shown. Do not log if it contains account/amount details.
     */
    public String getMessageText() {
        try {
            WebElement panel = driver.findElement(MESSAGE_AREA);
            return panel != null ? panel.getText().trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns true if the message area contains text suggesting an error (e.g. "error", "invalid", "insufficient"). */
    public boolean isErrorMessageShown() {
        String msg = getMessageText();
        if (msg == null) return false;
        String lower = msg.toLowerCase();
        return lower.contains("error") || lower.contains("invalid") || lower.contains("insufficient") || lower.contains("required");
    }
}
