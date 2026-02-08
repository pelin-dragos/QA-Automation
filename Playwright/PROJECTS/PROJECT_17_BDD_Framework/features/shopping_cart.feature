Feature: Shopping Cart Functionality
  Background:
    Given I am logged in with username "standard_user" and password "secret_sauce"

  Scenario: Add product to cart
    Given I am on the products page
    When I click the "Add to cart" button for the first product
    Then the product should be added to the cart
    And the cart badge should display "1"

  Scenario: Add multiple products to cart
    Given I am on the products page
    When I add the first product to the cart
    And I add the second product to the cart
    Then the cart badge should display "2"
    And both products should be in the cart

  Scenario: Remove product from cart
    Given I have added a product to the cart
    When I access the cart page
    And I click the "Remove" button
    Then the product should be removed from the cart
    And the cart should be empty

  Scenario: Verify total price in cart
    Given I have added products to the cart
    When I access the cart page
    Then I should see the total price calculated correctly

  @smoke
  Scenario: Empty cart
    Given I am on the cart page
    When the cart is empty
    Then I should see the message that the cart is empty
    And the checkout button should be disabled
