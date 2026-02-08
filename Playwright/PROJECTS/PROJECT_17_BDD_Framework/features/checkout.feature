Feature: Checkout Process
  Background:
    Given I am logged in with username "standard_user" and password "secret_sauce"
    And I have added a product to the cart

  Scenario: Complete checkout process
    Given I am on the cart page
    When I click the "Checkout" button at checkout
    Then I should be on the checkout page
    When I fill the form with first name "John", last name "Doe" and postal code "12345"
    And I click the "Continue" button at checkout
    Then I should see the overview page
    And I should see the product in the order list
    When I click the "Finish" button at checkout
    Then I should see the success message "Thank you for your order"
    And the order should be completed

  Scenario: Checkout form validation - empty fields
    Given I am on the checkout page
    When I click the "Continue" button without filling the form
    Then I should see an error message "Error: First Name is required"

  Scenario: Checkout form validation - missing name
    Given I am on the checkout page
    When I fill the form with last name "Doe" and postal code "12345" but without first name
    And I click the "Continue" button at checkout
    Then I should see an error message at checkout

  Scenario: Correct total price calculation
    Given I have products in the cart with total price of "$50.00"
    When I access the checkout overview page
    Then the subtotal should be "$50.00"
    And the tax should be calculated correctly
    And the final total should be correct
