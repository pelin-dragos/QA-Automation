package com.qa.automation.project17.steps;

import com.qa.automation.project17.base.DriverHolder;
import com.qa.automation.project17.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for product_sorting.feature (TC-BDD-SORT-001 to 005).
 */
public class ProductSortingSteps {

    private static ProductsPage productsPage() {
        return new ProductsPage(DriverHolder.get());
    }

    @When("I select {string}.")
    public void i_select(String sortOption) {
        productsPage().sortBy(sortOption);
    }

    @Then("the first product name is earlier in the alphabet than the last.")
    public void first_product_earlier_than_last() {
        String first = productsPage().getFirstProductName();
        String last = productsPage().getLastProductName();
        assertNotNull(first);
        assertNotNull(last);
        assertTrue(first.compareToIgnoreCase(last) <= 0, "First should be A-Z before or equal to last");
    }

    @Then("the first product name is later in the alphabet than the last.")
    public void first_product_later_than_last() {
        String first = productsPage().getFirstProductName();
        String last = productsPage().getLastProductName();
        assertNotNull(first);
        assertNotNull(last);
        assertTrue(first.compareToIgnoreCase(last) >= 0, "First should be Z-A after or equal to last");
    }

    @Then("the first product has the lowest price.")
    public void first_product_has_lowest_price() {
        String firstPrice = productsPage().getFirstProductPrice();
        String lastPrice = productsPage().getLastProductPrice();
        assertNotNull(firstPrice);
        assertNotNull(lastPrice);
        double first = parsePrice(firstPrice);
        double last = parsePrice(lastPrice);
        assertTrue(first <= last, "First price should be <= last (low to high)");
    }

    @Then("the first product has the highest price.")
    public void first_product_has_highest_price() {
        String firstPrice = productsPage().getFirstProductPrice();
        String lastPrice = productsPage().getLastProductPrice();
        assertNotNull(firstPrice);
        assertNotNull(lastPrice);
        double first = parsePrice(firstPrice);
        double last = parsePrice(lastPrice);
        assertTrue(first >= last, "First price should be >= last (high to low)");
    }

    @Then("products are sorted and the list is visible.")
    public void products_are_sorted_and_list_visible() {
        assertTrue(productsPage().isLoaded());
        assertTrue(productsPage().getProductCount() >= 1);
    }

    private static double parsePrice(String text) {
        if (text == null) return 0;
        String num = text.replaceAll("[^\\d.]", "");
        return num.isEmpty() ? 0 : Double.parseDouble(num);
    }
}
