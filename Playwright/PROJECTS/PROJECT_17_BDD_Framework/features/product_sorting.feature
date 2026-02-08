Feature: Product Sorting
  Background:
    Given I am logged in with username "standard_user" and password "secret_sauce"
    And I am on the products page

  Scenario: Sort products by name A-Z
    When I select the sort option "Name (A to Z)"
    Then the products should be sorted alphabetically A-Z
    And the first product should start with an earlier letter in the alphabet

  Scenario: Sort products by name Z-A
    When I select the sort option "Name (Z to A)"
    Then the products should be sorted alphabetically Z-A
    And the first product should start with a later letter in the alphabet

  Scenario: Sort products by price Low to High
    When I select the sort option "Price (low to high)"
    Then the products should be sorted by price ascending
    And the first product should have the lowest price

  Scenario: Sort products by price High to Low
    When I select the sort option "Price (high to low)"
    Then the products should be sorted by price descending
    And the first product should have the highest price

  @smoke
  Scenario Outline: Sort with different options
    When I select the sort option "<sort_option>"
    Then the products should be sorted according to "<sort_option>"

    Examples:
      | sort_option            |
      | Name (A to Z)          |
      | Name (Z to A)          |
      | Price (low to high)    |
      | Price (high to low)    |
