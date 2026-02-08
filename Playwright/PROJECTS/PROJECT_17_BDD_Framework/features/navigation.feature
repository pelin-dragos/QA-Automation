Feature: Navigation Functionality
  Background:
    Given I am logged in with username "standard_user" and password "secret_sauce"

  Scenario: Navigate to products page
    When I navigate to the products page
    Then I should see the product list
    And I should see at least one product

  Scenario: Navigate to shopping cart
    When I click the cart icon
    Then I should be redirected to the cart page
    And the URL should contain "cart"

  Scenario: Navigate back from cart
    Given I am on the cart page
    When I click the "Continue Shopping" button in navigation
    Then I should return to the products page

  @smoke
  Scenario: Menu navigation verification
    When I navigate through the application
    Then all menu links should be accessible
