@navigation
Feature: Navigation
  As a user I want to navigate the app.

  Background:
    Given I am logged in.

  Scenario: TC-BDD-NAV-001 Navigate to products page
    When I navigate to the products page.
    Then I see the product list and at least one product.

  Scenario: TC-BDD-NAV-002 Navigate to shopping cart
    When I click the cart icon.
    Then I am on the cart page and URL contains "cart".

  Scenario: TC-BDD-NAV-003 Navigate back from cart
    Given I am on the products page.
    When I go to the cart page.
    And I click "Continue Shopping".
    Then I return to the products page.

  Scenario: TC-BDD-NAV-004 Menu navigation
    When I open the menu.
    Then the menu is visible and I can close it.
