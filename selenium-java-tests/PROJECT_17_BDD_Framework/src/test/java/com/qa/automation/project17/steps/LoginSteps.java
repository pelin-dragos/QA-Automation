package com.qa.automation.project17.steps;

import com.qa.automation.project17.base.DriverHolder;
import com.qa.automation.project17.pages.LoginPage;
import com.qa.automation.project17.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for login.feature (TC-BDD-LOGIN-001 to 004).
 */
public class LoginSteps {

    private static LoginPage loginPage() {
        WebDriver driver = DriverHolder.get();
        return new LoginPage(driver);
    }

    @Given("I am on the login page.")
    public void i_am_on_the_login_page() {
        loginPage().navigateTo();
    }

    @When("I enter username {string} and password {string} and click login.")
    public void i_enter_username_and_password_and_click_login(String username, String password) {
        loginPage().enterUsername(username);
        loginPage().enterPassword(password);
        loginPage().clickLogin();
    }

    @When("I click login without entering credentials.")
    public void i_click_login_without_entering_credentials() {
        loginPage().clickLogin();
    }

    @Then("I should be logged in and see the products page.")
    public void i_should_be_logged_in_and_see_the_products_page() {
        ProductsPage products = new ProductsPage(DriverHolder.get());
        assertTrue(products.isLoaded(), "Products page should be visible");
        assertTrue(products.getCurrentUrl().contains("inventory"), "URL should contain inventory");
    }

    @Then("I should see an error message and not be logged in.")
    public void i_should_see_an_error_message_and_not_be_logged_in() {
        String error = loginPage().getErrorMessage();
        assertTrue(error != null && !error.isBlank(), "Error message should be visible");
        assertFalse(DriverHolder.get().getCurrentUrl().contains("inventory"), "Should not be on inventory");
    }

    @Then("I should see an error and not be logged in.")
    public void i_should_see_an_error_and_not_be_logged_in() {
        i_should_see_an_error_message_and_not_be_logged_in();
    }

    @Then("I should see the products page.")
    public void i_should_see_the_products_page() {
        i_should_be_logged_in_and_see_the_products_page();
    }

    @Then("I should see an error message.")
    public void i_should_see_an_error_message() {
        String error = loginPage().getErrorMessage();
        assertTrue(error != null && !error.isBlank(), "Error message should be visible");
    }
}
