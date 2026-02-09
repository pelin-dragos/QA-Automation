package com.qa.automation.project06.tests;

import com.qa.automation.project06.base.BaseTest;
import com.qa.automation.project06.config.TestConfig;
import com.qa.automation.project06.pages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * E-Commerce Shopping Cart Flow tests. Complete flow, cart management, checkout, browse.
 */
class ShoppingCartFlowTest extends BaseTest {

    private ProductsPage loginAndGetProductsPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(TestConfig.getSaucedemoUsername(), TestConfig.getSaucedemoPassword());
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.waitForPageLoad();
        return productsPage;
    }

    @Nested
    class CompletePurchaseFlow {

        @Test
        void shouldCompleteFullPurchaseFlow() {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login(TestConfig.getSaucedemoUsername(), TestConfig.getSaucedemoPassword());

            ProductsPage productsPage = new ProductsPage(driver);
            productsPage.waitForPageLoad();
            Assertions.assertTrue(productsPage.isLoaded(), "Products page should be loaded");

            int productsCount = productsPage.getProductsCount();
            Assertions.assertTrue(productsCount > 0, "Should have at least one product");

            String product1 = productsPage.addProductToCart(0);
            String product2 = productsPage.addProductToCart(1);
            Assertions.assertEquals(2, productsPage.getCartItemsCount(), "Cart should contain 2 items");

            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            Assertions.assertTrue(cartPage.isLoaded(), "Cart page should be loaded");

            List<String> cartItems = cartPage.getAllCartItemsNames();
            Assertions.assertTrue(cartItems.contains(product1), "Cart should contain first product");
            Assertions.assertTrue(cartItems.contains(product2), "Cart should contain second product");

            double totalPrice = cartPage.calculateTotalPrice();
            Assertions.assertTrue(totalPrice > 0, "Cart total should be positive");

            cartPage.clickCheckout();
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.waitForPageLoad();
            Assertions.assertTrue(checkoutPage.isLoaded(), "Checkout form should be loaded");

            checkoutPage.fillCheckoutForm("John", "Doe", "12345");
            checkoutPage.clickContinue();

            CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
            overviewPage.waitForPageLoad();
            Assertions.assertTrue(overviewPage.isLoaded(), "Overview page should be loaded");

            double subtotal = overviewPage.getSubtotal();
            double tax = overviewPage.getTax();
            double total = overviewPage.getTotal();
            double expectedTotal = overviewPage.calculateExpectedTotal();
            Assertions.assertTrue(Math.abs(total - expectedTotal) < 0.01,
                    "Total should equal subtotal + tax");

            overviewPage.clickFinish();

            CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
            completePage.waitForPageLoad();
            Assertions.assertTrue(completePage.isLoaded(), "Complete page should be loaded");

            String successMessage = completePage.getSuccessMessage();
            Assertions.assertTrue(successMessage.contains("Thank you for your order"),
                    "Success message should contain 'Thank you for your order'");
        }
    }

    @Nested
    class CartManagement {

        @Test
        void shouldAddSingleProductToCart() {
            ProductsPage productsPage = loginAndGetProductsPage();
            String productName = productsPage.addProductToCart(0);
            Assertions.assertNotNull(productName, "Added product name should not be null");
            Assertions.assertEquals(1, productsPage.getCartItemsCount(), "Cart count should be 1");

            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            List<String> cartItems = cartPage.getAllCartItemsNames();
            Assertions.assertTrue(cartItems.contains(productName), "Cart should contain added product");
        }

        @Test
        void shouldAddMultipleProductsToCart() {
            ProductsPage productsPage = loginAndGetProductsPage();
            List<String> added = productsPage.addMultipleProductsToCart(new int[]{0, 1, 2});
            Assertions.assertEquals(3, added.size(), "Should add 3 products");
            Assertions.assertEquals(3, productsPage.getCartItemsCount(), "Cart count should be 3");

            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            List<String> cartItems = cartPage.getAllCartItemsNames();
            Assertions.assertEquals(3, cartItems.size(), "Cart should have 3 items");
            for (String product : added) {
                Assertions.assertTrue(cartItems.contains(product), "Cart should contain: " + product);
            }
        }

        @Test
        void shouldRemoveItemFromCart() {
            ProductsPage productsPage = loginAndGetProductsPage();
            String product1 = productsPage.addProductToCart(0);
            productsPage.addProductToCart(1);
            productsPage.clickCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            Assertions.assertEquals(2, cartPage.getCartItemsCount(), "Cart should have 2 items");

            String removed = cartPage.removeItemFromCart(0);
            Assertions.assertEquals(product1, removed, "Removed item should match first product");

            Assertions.assertEquals(1, cartPage.getCartItemsCount(), "Cart should have 1 item after remove");
            List<String> remaining = cartPage.getAllCartItemsNames();
            Assertions.assertFalse(remaining.contains(product1), "Removed product should not be in cart");
        }

        @Test
        void shouldCalculateCartTotalCorrectly() {
            ProductsPage productsPage = loginAndGetProductsPage();
            ProductsPage.ProductInfo info1 = productsPage.getProductInfo(0);
            ProductsPage.ProductInfo info2 = productsPage.getProductInfo(1);
            Assertions.assertNotNull(info1);
            Assertions.assertNotNull(info2);
            double expectedTotal = info1.price + info2.price;

            productsPage.addProductToCart(0);
            productsPage.addProductToCart(1);
            productsPage.clickCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            double actualTotal = cartPage.calculateTotalPrice();
            Assertions.assertTrue(Math.abs(actualTotal - expectedTotal) < 0.01,
                    "Cart total should match sum of product prices");
        }
    }

    @Nested
    class CheckoutProcess {

        @Test
        void shouldValidateCheckoutForm() {
            ProductsPage productsPage = loginAndGetProductsPage();
            productsPage.addProductToCart(0);
            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.waitForPageLoad();
            checkoutPage.clickContinue();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String errorMessage = checkoutPage.getErrorMessage();
            Assertions.assertNotNull(errorMessage, "Error message should be shown for empty form");
            Assertions.assertTrue(errorMessage.toLowerCase().contains("first name") || errorMessage.toLowerCase().contains("required"),
                    "Error should mention first name or required");
        }

        @Test
        void shouldCompleteCheckoutForm() {
            ProductsPage productsPage = loginAndGetProductsPage();
            productsPage.addProductToCart(0);
            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.waitForPageLoad();
            checkoutPage.fillCheckoutForm("John", "Doe", "12345");
            checkoutPage.clickContinue();

            CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
            overviewPage.waitForPageLoad();
            Assertions.assertTrue(overviewPage.isLoaded(), "Overview should load after valid form");
        }

        @Test
        void shouldCalculateCheckoutOverviewCorrectly() {
            ProductsPage productsPage = loginAndGetProductsPage();
            ProductsPage.ProductInfo info1 = productsPage.getProductInfo(0);
            ProductsPage.ProductInfo info2 = productsPage.getProductInfo(1);
            Assertions.assertNotNull(info1);
            Assertions.assertNotNull(info2);
            double expectedSubtotal = info1.price + info2.price;

            productsPage.addProductToCart(0);
            productsPage.addProductToCart(1);
            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.waitForPageLoad();
            checkoutPage.fillCheckoutForm("John", "Doe", "12345");
            checkoutPage.clickContinue();

            CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
            overviewPage.waitForPageLoad();
            double subtotal = overviewPage.getSubtotal();
            Assertions.assertTrue(Math.abs(subtotal - expectedSubtotal) < 0.01, "Subtotal should match");
            double total = overviewPage.getTotal();
            double expectedTotal = overviewPage.calculateExpectedTotal();
            Assertions.assertTrue(Math.abs(total - expectedTotal) < 0.01, "Total should equal subtotal + tax");
        }

        @Test
        void shouldCompleteCheckout() {
            ProductsPage productsPage = loginAndGetProductsPage();
            productsPage.addProductToCart(0);
            productsPage.clickCart();
            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.waitForPageLoad();
            checkoutPage.fillCheckoutForm("John", "Doe", "12345");
            checkoutPage.clickContinue();

            CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
            overviewPage.waitForPageLoad();
            overviewPage.clickFinish();

            CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
            completePage.waitForPageLoad();
            Assertions.assertTrue(completePage.isLoaded(), "Complete page should be loaded");
            String successMessage = completePage.getSuccessMessage();
            Assertions.assertTrue(successMessage.contains("Thank you for your order"),
                    "Success message should contain 'Thank you for your order'");
        }
    }

    @Nested
    class BrowseAndNavigation {

        @Test
        void shouldLoginAndNavigateToProducts() {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.navigateTo();
            loginPage.login(TestConfig.getSaucedemoUsername(), TestConfig.getSaucedemoPassword());

            ProductsPage productsPage = new ProductsPage(driver);
            productsPage.waitForPageLoad();
            Assertions.assertTrue(productsPage.isLoaded(), "Products page should be loaded");
            Assertions.assertTrue(productsPage.getCurrentUrl().contains("inventory"), "URL should contain inventory");
            Assertions.assertTrue(productsPage.getProductsCount() > 0, "Should have products");
        }

        @Test
        void shouldViewProductDetails() {
            ProductsPage productsPage = loginAndGetProductsPage();
            Assertions.assertTrue(productsPage.getProductsCount() > 0, "Should have products");
            ProductsPage.ProductInfo info = productsPage.getProductInfo(0);
            Assertions.assertNotNull(info, "Product info should not be null");
            Assertions.assertNotNull(info.name, "Product name should not be null");
            Assertions.assertTrue(info.price > 0, "Product price should be positive");
        }

        @Test
        void shouldContinueShoppingFromCart() {
            ProductsPage productsPage = loginAndGetProductsPage();
            productsPage.addProductToCart(0);
            productsPage.clickCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.waitForPageLoad();
            cartPage.clickContinueShopping();

            productsPage.waitForPageLoad();
            Assertions.assertTrue(productsPage.isLoaded(), "Should return to products page");
            Assertions.assertTrue(productsPage.getCurrentUrl().contains("inventory"), "URL should be inventory");
        }
    }
}
