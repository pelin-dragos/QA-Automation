package com.qa.automation.project15.pages;

import com.qa.automation.project15.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for Admin → User Management → Users (e.g. OrangeHRM).
 * <p>
 * Provides navigation to Users list, Add User, search, reset, table row count, user info,
 * pagination check, and delete flow. Uses explicit waits. Test data (usernames) must be
 * generated per test; do not share mutable state between tests.
 */
public class UsersManagementPage {

    /** OrangeHRM 5: direct URL to Users list (avoids menu structure differences). */
    private static final String USERS_PAGE_PATH = "index.php/admin/viewSystemUsers";
    /** Add User button (OrangeHRM 5: button containing 'Add' text or secondary class). */
    private static final By ADD_BUTTON = By.xpath("//button[contains(.,'Add')] | //button[contains(@class,'oxd-button--secondary')]");
    /** Search: Username input (OrangeHRM 5 filter form; label may be 'Username' or contain it). */
    private static final By SEARCH_USERNAME = By.xpath("//label[contains(.,'Username')]/../..//input | (//form[contains(@class,'oxd-form')]//input)[1]");
    private static final By SEARCH_BUTTON = By.xpath("//button[normalize-space()='Search'] | //button[contains(@type,'submit') or contains(.,'Search')]");
    private static final By RESET_BUTTON = By.xpath("//button[normalize-space()='Reset'] | //button[contains(.,'Reset')]");
    /** Table body rows (user rows). */
    private static final By TABLE_ROWS = By.cssSelector(".oxd-table-card, .oxd-table-body .oxd-table-row");
    /** Add User form: User Role, Employee Name, Username, Password, Confirm, Status; Save. */
    private static final By ROLE_DROPDOWN = By.xpath("(//label[text()='User Role']/../..//div[contains(@class,'oxd-select')])[1]");
    private static final By EMPLOYEE_NAME_INPUT = By.xpath("//label[text()='Employee Name']/../..//input");
    private static final By USERNAME_INPUT = By.xpath("(//label[text()='Username']/../..//input)[last()]");
    private static final By PASSWORD_INPUT = By.xpath("//label[text()='Password']/../..//input");
    private static final By CONFIRM_PASSWORD_INPUT = By.xpath("//label[text()='Confirm Password']/../..//input");
    private static final By STATUS_DROPDOWN = By.xpath("(//label[text()='Status']/../..//div[contains(@class,'oxd-select')])[1]");
    private static final By SAVE_BUTTON = By.xpath("//button[normalize-space()='Save']");
    /** Pagination container (optional). */
    private static final By PAGINATION = By.cssSelector(".oxd-pagination, [class*='pagination']");
    /** Dropdown options: OrangeHRM 5 uses .oxd-select-dropdown-option or div[role='listbox'] > div. */
    private static final By DROPDOWN_OPTIONS = By.cssSelector(".oxd-select-dropdown-option, div[role='listbox'] div.oxd-select-option");

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String baseUrl;

    public UsersManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        this.baseUrl = TestConfig.getBaseUrl();
    }

    /**
     * Navigates to the Users list page (Admin → User Management → Users) via direct URL
     * so menu structure differences do not break tests. Waits for table or Add button.
     */
    public void navigateTo() {
        driver.get(baseUrl + USERS_PAGE_PATH);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON),
                ExpectedConditions.visibilityOfElementLocated(TABLE_ROWS)
        ));
    }

    /** Returns true when the Users list or Add button is visible. */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON),
                    ExpectedConditions.visibilityOfElementLocated(TABLE_ROWS)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Returns true if the Add User button is visible (for tests that need to create users). */
    public boolean isAddButtonVisible() {
        try {
            return driver.findElement(ADD_BUTTON).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Clicks Add User and waits for the form (Username or Save button visible). */
    public void clickAddUser() {
        wait.until(ExpectedConditions.elementToBeClickable(ADD_BUTTON)).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT),
                ExpectedConditions.visibilityOfElementLocated(SAVE_BUTTON)
        ));
    }

    /**
     * Fills the Add User form: role (e.g. "ESS"), employee name (partial match), username, password, status ("Enabled").
     * OrangeHRM uses autocomplete for Employee Name; we type and wait for dropdown then select first option if present.
     */
    public void fillAddUserForm(String role, String employeeName, String username, String password, String status) {
        if (role != null && !role.isEmpty()) {
            clickDropdownAndSelectOption(ROLE_DROPDOWN, role);
        }
        if (employeeName != null && !employeeName.isEmpty()) {
            WebElement emp = wait.until(ExpectedConditions.visibilityOfElementLocated(EMPLOYEE_NAME_INPUT));
            emp.clear();
            emp.sendKeys(employeeName);
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                shortWait.until(ExpectedConditions.visibilityOfElementLocated(DROPDOWN_OPTIONS));
                driver.findElements(DROPDOWN_OPTIONS).stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
            } catch (Exception ignored) {
                // Autocomplete may not appear or has different structure
            }
        }
        if (username != null && !username.isEmpty()) {
            WebElement u = driver.findElement(USERNAME_INPUT);
            u.clear();
            u.sendKeys(username);
        }
        if (password != null && !password.isEmpty()) {
            WebElement p = driver.findElement(PASSWORD_INPUT);
            p.clear();
            p.sendKeys(password);
        }
        if (password != null && !password.isEmpty()) {
            WebElement c = driver.findElement(CONFIRM_PASSWORD_INPUT);
            c.clear();
            c.sendKeys(password);
        }
        if (status != null && !status.isEmpty()) {
            clickDropdownAndSelectOption(STATUS_DROPDOWN, status);
        }
    }

    private void clickDropdownAndSelectOption(By dropdown, String optionText) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(DROPDOWN_OPTIONS));
            driver.findElements(DROPDOWN_OPTIONS).stream()
                    .filter(el -> el.isDisplayed() && (el.getText().trim().equalsIgnoreCase(optionText) || el.getText().contains(optionText)))
                    .findFirst()
                    .ifPresent(WebElement::click);
        } catch (Exception ignored) {
            // Dropdown may not open or options differ; continue with rest of form
        }
    }

    /** Clicks Save and waits for the list or success (table/Add visible again). */
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON)).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON),
                ExpectedConditions.visibilityOfElementLocated(TABLE_ROWS)
        ));
    }

    /** Enters the given username in the search form and clicks Search. */
    public void searchByUsername(String username) {
        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_USERNAME));
            input.clear();
            if (username != null && !username.isEmpty()) {
                input.sendKeys(username);
            }
            wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON)).click();
        } catch (Exception e) {
            // Search form may have different structure
        }
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(TABLE_ROWS),
                ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON)
        ));
    }

    /** Clicks Reset and waits for the form to reset (list may reload). */
    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(RESET_BUTTON)).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(TABLE_ROWS),
                ExpectedConditions.visibilityOfElementLocated(ADD_BUTTON)
        ));
    }

    /** Returns the number of user rows in the table (current page). */
    public int getUsersCount() {
        try {
            List<WebElement> rows = driver.findElements(TABLE_ROWS);
            return (int) rows.stream().filter(WebElement::isDisplayed).count();
        } catch (Exception e) {
            return 0;
        }
    }

    /** Returns true if the table contains a row with the given username (text in row). */
    public boolean isUserInTable(String username) {
        if (username == null || username.isEmpty()) return false;
        try {
            List<WebElement> rows = driver.findElements(TABLE_ROWS);
            for (WebElement row : rows) {
                if (row.isDisplayed() && row.getText().contains(username)) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    /** Returns visible text of the first row (for "get user info"); or null. */
    public String getFirstRowText() {
        try {
            List<WebElement> rows = driver.findElements(TABLE_ROWS);
            return rows.stream().filter(WebElement::isDisplayed).findFirst().map(WebElement::getText).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns true if pagination controls are present and visible. */
    public boolean hasPagination() {
        try {
            List<WebElement> el = driver.findElements(PAGINATION);
            return el.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes the first user that matches the given username: finds row, clicks delete icon, confirms.
     * OrangeHRM: trash icon in row; confirm dialog. Returns true if delete flow was triggered.
     */
    public boolean deleteUserByUsername(String username) {
        try {
            List<WebElement> rows = driver.findElements(TABLE_ROWS);
            for (WebElement row : rows) {
                if (!row.isDisplayed() || !row.getText().contains(username)) continue;
                List<WebElement> deleteBtns = row.findElements(By.cssSelector(".oxd-icon-button[title='Delete'], [class*='delete'], button[class*='trash']"));
                for (WebElement btn : deleteBtns) {
                    if (btn.isDisplayed()) {
                        btn.click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Yes, Delete']"))).click();
                        return true;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
