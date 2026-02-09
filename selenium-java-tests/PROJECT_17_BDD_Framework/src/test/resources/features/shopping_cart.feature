@cart
Feature: Shopping Cart
  As a user I want to manage my cart.

  Background:
    Given I am logged in.

  Scenario: TC-BDD-CART-001 Add product to cart
    Given I am on the products page.
    When I click Add to cart for first product.
    Then the product is in the cart and cart badge shows "1".

  Scenario: TC-BDD-CART-002 Add multiple products
    Given I am on the products page.
    When I add the first and second product to cart.
    Then the cart badge shows "2" and both are in the cart.

  Scenario: TC-BDD-CART-003 Remove product from cart
    Given I am on the products page.
    And I click Add to cart for first product.
    And I go to the cart page.
    When I click Remove on the first item.
    Then the cart is empty or the badge is updated.

  Scenario: TC-BDD-CART-004 Verify total price in cart
    Given I am on the products page.
    And I add the first and second product to cart.
    When I go to the cart page.
    Then the total price is calculated correctly.

  Scenario: TC-BDD-CART-005 Empty cart state
    Given I am on the products page.
    When I go to the cart page.
    And the cart is empty.
    Then an empty message or cart list is shown and checkout may be disabled when cart is empty.
