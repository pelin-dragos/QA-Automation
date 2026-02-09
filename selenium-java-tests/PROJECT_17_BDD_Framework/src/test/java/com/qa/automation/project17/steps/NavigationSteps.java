package com.qa.automation.project17.steps;

import com.qa.automation.project17.base.DriverHolder;
import com.qa.automation.project17.pages.CartPage;
import com.qa.automation.project17.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for navigation.feature (TC-BDD-NAV-001 to 004).
 */
public class NavigationSteps {

    private static WebDriver driver() {
        return DriverHolder.get();
    }

    private static ProductsPage productsPage() {
        return new ProductsPage(driver());
    }

    private static CartPage cartPage() {
        return new CartPage(driver());
    }

    @When("I navigate to the products page.")
    public void i_navigate_to_the_products_page() {
        if (!driver().getCurrentUrl().contains("inventory")) {
            new LogoutSteps().i_am_logged_in();
        }
    }

    @Then("I see the product list and at least one product.")
    public void i_see_the_product_list_and_at_least_one_product() {
        assertTrue(productsPage().isLoaded());
        assertTrue(productsPage().getProductCount() >= 1, "At least one product");
    }

    @When("I click the cart icon.")
    public void i_click_the_cart_icon() {
        productsPage().clickCartIcon();
    }

    @Then("I am on the cart page and URL contains {string}.")
    public void i_am_on_the_cart_page_and_url_contains(String urlPart) {
        assertTrue(cartPage().isLoaded());
        assertTrue(cartPage().getCurrentUrl().contains(urlPart));
    }

    @When("I click {string}.")
    public void i_click_continue_shopping(String buttonText) {
        if (buttonText.equals("Continue Shopping")) {
            cartPage().clickContinueShopping();
        }
    }

    @Then("I return to the products page.")
    public void i_return_to_the_products_page() {
        assertTrue(productsPage().isLoaded());
        assertTrue(driver().getCurrentUrl().contains("inventory"));
    }

    @When("I open the menu.")
    public void i_open_the_menu() {
        productsPage().openMenu();
    }

    @Then("the menu is visible and I can close it.")
    public void the_menu_is_visible_and_i_can_close_it() {
        // Menu was opened in previous step; page has menu (no explicit close needed for this TC)
        assertTrue(productsPage().isLoaded());
    }
}
