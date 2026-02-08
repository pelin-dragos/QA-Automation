Feature: Logout Functionality
  Background:
    Given I am logged in with username "standard_user" and password "secret_sauce"

  Scenario: Successful logout
    Given I am logged in
    When I click the logout button
    Then I should be logged out
    And I should be redirected to the login page
    And the URL should contain "index.html"

  Scenario: Logout verification - restricted access
    Given I have logged out
    When I try to access the products page directly
    Then I should be redirected to the login page
    And I should not be able to access the products page without login

  @smoke
  Scenario: Logout from menu
    Given I am on the products page
    When I open the menu
    And I click the "Logout" option
    Then I should be logged out successfully
