@smoke @logout
Feature: Logout
  As a logged-in user I want to log out so that my session ends.

  Background:
    Given I am logged in.

  Scenario: TC-BDD-LOGOUT-001 Successful logout
    When I click logout.
    Then I should be logged out and redirected to login; URL contains "index.html".

  Scenario: TC-BDD-LOGOUT-003 Logout from menu
    Given I am on the products page.
    When I open the menu and click "Logout".
    Then I should be logged out.

@logout @security
  Scenario: TC-BDD-LOGOUT-002 Restricted access after logout
    Given I have logged out.
    When I try to access the products page directly.
    Then I should be redirected to login.
