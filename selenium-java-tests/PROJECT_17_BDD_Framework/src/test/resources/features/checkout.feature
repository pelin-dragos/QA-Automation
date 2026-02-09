@checkout @smoke
Feature: Checkout
  As a user I want to complete checkout.

  Background:
    Given I am logged in.
    And I am on the products page.
    And I click Add to cart for first product.
    And I go to the cart page.

  Scenario: TC-BDD-CHK-001 Complete checkout process
    When I click Checkout.
    And I fill First Name "Test" and Last Name "User" and Postal Code "12345".
    And I click Continue.
    And I click Finish.
    Then I should see the message "Thank you for your order".

  Scenario: TC-BDD-CHK-002 Checkout form validation – empty fields
    When I click Checkout.
    And I click Continue without filling the form.
    Then I should see a checkout error such as "First Name is required".

  Scenario: TC-BDD-CHK-003 Checkout form validation – missing first name
    When I click Checkout.
    And I fill Last Name "User" and Postal Code "12345" only.
    And I click Continue.
    Then I should see a checkout error.

  Scenario: TC-BDD-CHK-004 Correct total price on overview
    When I click Checkout.
    And I fill First Name "A" and Last Name "B" and Postal Code "1".
    And I click Continue.
    Then the checkout overview shows a correct subtotal and total.
