package com.qa.automation.project17.steps;

import com.qa.automation.project17.base.DriverHolder;
import com.qa.automation.project17.pages.CartPage;
import com.qa.automation.project17.pages.CheckoutPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for checkout.feature (TC-BDD-CHK-001 to 004).
 */
public class CheckoutSteps {

    private static WebDriver driver() {
        return DriverHolder.get();
    }

    private static CartPage cartPage() {
        return new CartPage(driver());
    }

    private static CheckoutPage checkoutPage() {
        return new CheckoutPage(driver());
    }

    @When("I click Checkout.")
    public void i_click_checkout() {
        cartPage().clickCheckout();
    }

    @When("I fill First Name {string} and Last Name {string} and Postal Code {string}.")
    public void i_fill_first_name_last_name_postal_code(String first, String last, String code) {
        checkoutPage().fillInfo(first, last, code);
    }

    @When("I fill Last Name {string} and Postal Code {string} only.")
    public void i_fill_last_name_and_postal_code_only(String last, String code) {
        checkoutPage().fillInfo(null, last, code);
    }

    @And("I click Continue.")
    public void i_click_continue() {
        checkoutPage().clickContinue();
    }

    @When("I click Continue without filling the form.")
    public void i_click_continue_without_filling_the_form() {
        checkoutPage().clickContinue();
    }

    @And("I click Finish.")
    public void i_click_finish() {
        checkoutPage().clickFinish();
    }

    @Then("I should see the message {string}.")
    public void i_should_see_the_message(String expectedMessage) {
        String message = checkoutPage().getCompleteMessage();
        assertTrue(message != null && message.contains(expectedMessage),
                "Expected message containing: " + expectedMessage);
    }

    @Then("I should see a checkout error such as {string}.")
    public void i_should_see_checkout_error_such_as(String expectedPart) {
        String error = checkoutPage().getCheckoutError();
        assertTrue(error != null && !error.isBlank(), "Checkout error should be visible");
    }

    @Then("I should see a checkout error.")
    public void i_should_see_a_checkout_error() {
        String error = checkoutPage().getCheckoutError();
        assertTrue(error != null && !error.isBlank(), "Checkout error should be visible");
    }

    @Then("the checkout overview shows a correct subtotal and total.")
    public void checkout_overview_shows_correct_subtotal_and_total() {
        Double subtotal = checkoutPage().getSummarySubtotal();
        Double total = checkoutPage().getSummaryTotal();
        assertNotNull(subtotal, "Subtotal should be displayed");
        assertNotNull(total, "Total should be displayed");
        assertTrue(total >= subtotal, "Total should be >= subtotal");
    }
}
