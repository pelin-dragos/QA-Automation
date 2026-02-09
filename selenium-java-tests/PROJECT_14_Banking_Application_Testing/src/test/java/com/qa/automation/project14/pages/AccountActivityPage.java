package com.qa.automation.project14.pages;

import com.qa.automation.project14.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for account activity / statements (e.g. ParaBank Account Activity).
 * <p>
 * Used to verify that statements or transaction list are visible after navigating
 * from the dashboard. Uses explicit waits. Do not log full account numbers in reports.
 */
public class AccountActivityPage {

    /** Main content panel. */
    private static final By RIGHT_PANEL = By.id("rightPanel");
    /** Transaction table (ParaBank: id transactionTable or similar). */
    private static final By TRANSACTION_TABLE = By.id("transactionTable");
    /** Fallback: any table in the panel for transaction/statement data. */
    private static final By ANY_TABLE = By.cssSelector("#rightPanel table");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AccountActivityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /**
     * Returns true if the page is loaded (right panel visible). Does not require a table
     * so that "no transactions" view is still considered loaded.
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(RIGHT_PANEL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if a transaction/statement table is present and visible.
     * Used by TC-BANK-STMT-001 to verify statements or transaction list is available.
     */
    public boolean hasTransactionOrStatementTable() {
        try {
            List<WebElement> tables = driver.findElements(TRANSACTION_TABLE);
            if (tables.isEmpty()) {
                tables = driver.findElements(ANY_TABLE);
            }
            return tables.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns visible table content text (for assertions that list is present; avoid logging full content). */
    public String getPanelText() {
        try {
            WebElement panel = driver.findElement(RIGHT_PANEL);
            return panel != null ? panel.getText().trim() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
