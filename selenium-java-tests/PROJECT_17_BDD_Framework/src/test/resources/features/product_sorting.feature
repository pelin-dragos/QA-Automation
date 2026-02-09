@sorting
Feature: Product Sorting
  As a user I want to sort products.

  Background:
    Given I am logged in.

  Scenario: TC-BDD-SORT-001 Sort by name A–Z
    Given I am on the products page.
    When I select "Name (A to Z)".
    Then the first product name is earlier in the alphabet than the last.

  Scenario: TC-BDD-SORT-002 Sort by name Z–A
    Given I am on the products page.
    When I select "Name (Z to A)".
    Then the first product name is later in the alphabet than the last.

  Scenario: TC-BDD-SORT-003 Sort by price low to high
    Given I am on the products page.
    When I select "Price (low to high)".
    Then the first product has the lowest price.

  Scenario: TC-BDD-SORT-004 Sort by price high to low
    Given I am on the products page.
    When I select "Price (high to low)".
    Then the first product has the highest price.

  Scenario Outline: TC-BDD-SORT-005 Sort with different options
    Given I am on the products page.
    When I select "<sort_option>".
    Then products are sorted and the list is visible.
    Examples:
      | sort_option          |
      | Name (A to Z)        |
      | Name (Z to A)        |
      | Price (low to high)  |
      | Price (high to low)  |
