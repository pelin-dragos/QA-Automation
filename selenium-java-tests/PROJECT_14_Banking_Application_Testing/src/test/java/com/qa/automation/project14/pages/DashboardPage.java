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
import java.util.regex.Pattern;

/**
 * Page Object for the banking dashboard (e.g. ParaBank Accounts Overview).
 * <p>
 * Provides methods to verify dashboard load, welcome message, account numbers, balance,
 * navigation to Accounts Overview and Transfer Funds, and logout. Uses explicit waits.
 */
public class DashboardPage {

    /** Left panel link to Accounts Overview (ParaBank). */
    private static final By ACCOUNTS_OVERVIEW_LINK = By.linkText("Accounts Overview");
    /** Left panel link to Transfer Funds. */
    private static final By TRANSFER_FUNDS_LINK = By.linkText("Transfer Funds");
    /** Left panel Log Out link. */
    private static final By LOGOUT_LINK = By.linkText("Log Out");
    /** Main content heading (e.g. "Accounts Overview") or welcome text. */
    private static final By PAGE_HEADING = By.id("rightPanel");
    /** Account table (ParaBank: id accountTable). Rows contain account id and balance. */
    private static final By ACCOUNT_TABLE = By.id("accountTable");
    /** Table body rows (each row: account type, balance, available; account id in link). */
    private static final By TABLE_BODY_ROWS = By.cssSelector("#accountTable tbody tr");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /**
     * Returns true when the dashboard/overview is loaded: URL contains "overview" and
     * the main content area or account table is visible.
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("overview"),
                    ExpectedConditions.urlContains("index.htm")
            ));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(PAGE_HEADING),
                    ExpectedConditions.visibilityOfElementLocated(ACCOUNT_TABLE)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns true if the user is logged in: we are on overview and page is loaded. */
    public boolean isLoggedIn() {
        String url = driver.getCurrentUrl();
        return url != null && url.contains("overview") && isLoaded();
    }

    /** Returns the text of the main right panel (welcome/heading). May be used to assert welcome message. */
    public String getWelcomeOrHeadingText() {
        try {
            WebElement panel = driver.findElement(PAGE_HEADING);
            return panel != null ? panel.getText().trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Clicks "Accounts Overview" in the left panel and waits for the overview content. */
    public void clickAccountsOverview() {
        wait.until(ExpectedConditions.elementToBeClickable(ACCOUNTS_OVERVIEW_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_HEADING));
    }

    /**
     * Returns a list of account numbers (IDs) from the account table.
     * ParaBank: account id is often in the first column or in a link; we collect visible numbers from the table.
     */
    public List<String> getAccountNumbers() {
        List<String> ids = new ArrayList<>();
        try {
            List<WebElement> rows = driver.findElements(TABLE_BODY_ROWS);
            Pattern digits = Pattern.compile("\\d+");
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 1) {
                    String cellText = cells.get(0).getText().trim();
                    if (!cellText.isEmpty() && digits.matcher(cellText).find()) {
                        ids.add(cellText);
                    }
                    // Some versions use a link with account id
                    List<WebElement> links = row.findElements(By.tagName("a"));
                    for (WebElement link : links) {
                        String href = link.getAttribute("href");
                        if (href != null && href.contains("id=")) {
                            String id = href.replaceFirst(".*[?&]id=(\\d+).*", "$1");
                            if (!id.equals(href) && !ids.contains(id)) {
                                ids.add(id);
                            }
                        }
                    }
                }
            }
            if (ids.isEmpty() && !rows.isEmpty()) {
                String firstCell = rows.get(0).findElements(By.tagName("td")).get(0).getText().trim();
                if (!firstCell.isEmpty()) {
                    ids.add(firstCell);
                }
            }
        } catch (Exception ignored) {
            // Table may not be present if no accounts.
        }
        return ids;
    }

    /**
     * Returns the balance of the first account from the table, or null if not found.
     * Balance is typically in the second column (index 1). Do not log full balance in production reports if masking is required.
     */
    public String getFirstAccountBalance() {
        try {
            List<WebElement> rows = driver.findElements(TABLE_BODY_ROWS);
            if (rows.isEmpty()) return null;
            List<WebElement> cells = rows.get(0).findElements(By.tagName("td"));
            if (cells.size() >= 2) {
                return cells.get(1).getText().trim();
            }
            if (cells.size() == 1) {
                return cells.get(0).getText().trim();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /** Clicks Log Out and waits until the login page is visible (username field). Returns LoginPage for further assertions. */
    public LoginPage logout() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        return new LoginPage(driver);
    }

    /** Clicks "Transfer Funds" in the left panel. Caller should then use TransferFundsPage. */
    public void clickTransferFunds() {
        wait.until(ExpectedConditions.elementToBeClickable(TRANSFER_FUNDS_LINK)).click();
    }

    /**
     * Clicks the first account link in the table (if any) to open account activity/statements.
     * Used by TC-BANK-STMT-001. Returns true if a link was clicked, false if no account link found.
     */
    public boolean clickFirstAccountLinkToActivity() {
        try {
            List<WebElement> rows = driver.findElements(TABLE_BODY_ROWS);
            for (WebElement row : rows) {
                List<WebElement> links = row.findElements(By.tagName("a"));
                for (WebElement link : links) {
                    String href = link.getAttribute("href");
                    if (href != null && (href.contains("activity") || href.contains("id="))) {
                        link.click();
                        return true;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
