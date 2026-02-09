package com.qa.automation.project17.steps;

import com.qa.automation.project17.base.DriverHolder;
import com.qa.automation.project17.config.TestConfig;
import com.qa.automation.project17.pages.LoginPage;
import com.qa.automation.project17.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for logout.feature (TC-BDD-LOGOUT-001 to 003).
 */
public class LogoutSteps {

    private static WebDriver driver() {
        return DriverHolder.get();
    }

    private static ProductsPage productsPage() {
        return new ProductsPage(driver());
    }

    private static LoginPage loginPage() {
        return new LoginPage(driver());
    }

    @Given("I am logged in.")
    public void i_am_logged_in() {
        loginPage().navigateTo();
        loginPage().login("standard_user", "secret_sauce");
        assertTrue(productsPage().isLoaded(), "Should be on products page");
    }

    @Given("I am on the products page.")
    public void i_am_on_the_products_page() {
        if (!driver().getCurrentUrl().contains("inventory")) {
            i_am_logged_in();
        }
        assertTrue(productsPage().isLoaded());
    }

    @When("I click logout.")
    public void i_click_logout() {
        productsPage().logout();
    }

    @Then("I should be logged out and redirected to login; URL contains {string}.")
    public void i_should_be_logged_out_and_redirected_to_login_url_contains(String urlPart) {
        assertTrue(loginPage().isLoaded(), "Login page should be visible");
        String url = driver().getCurrentUrl();
        assertTrue(url.contains(urlPart) || url.endsWith("/") || url.replaceFirst("/$", "").endsWith("saucedemo.com"),
                "URL should contain " + urlPart + " or be login page (current: " + url + ")");
    }

    @When("I open the menu and click {string}.")
    public void i_open_the_menu_and_click(String linkText) {
        productsPage().openMenu();
        productsPage().logout();
    }

    @Then("I should be logged out.")
    public void i_should_be_logged_out() {
        assertTrue(loginPage().isLoaded());
    }

    @Given("I have logged out.")
    public void i_have_logged_out() {
        loginPage().navigateTo();
        loginPage().login("standard_user", "secret_sauce");
        productsPage().logout();
        assertTrue(loginPage().isLoaded());
    }

    @When("I try to access the products page directly.")
    public void i_try_to_access_the_products_page_directly() {
        driver().get(TestConfig.getBaseUrl() + "inventory.html");
    }

    @Then("I should be redirected to login.")
    public void i_should_be_redirected_to_login() {
        assertTrue(driver().getCurrentUrl().contains("index") || loginPage().isLoaded(),
                "Should be on login page");
    }
}
