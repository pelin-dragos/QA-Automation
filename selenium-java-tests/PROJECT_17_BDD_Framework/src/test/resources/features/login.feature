@smoke @login
Feature: Login
  As a user I want to log in so that I can use the store.

  Scenario: TC-BDD-LOGIN-001 Successful login with valid credentials
    Given I am on the login page.
    When I enter username "standard_user" and password "secret_sauce" and click login.
    Then I should be logged in and see the products page.

  Scenario: TC-BDD-LOGIN-002 Failed login with invalid credentials
    Given I am on the login page.
    When I enter username "invalid_user" and password "invalid_password" and click login.
    Then I should see an error message and not be logged in.

  Scenario: TC-BDD-LOGIN-003 Login with empty fields
    Given I am on the login page.
    When I click login without entering credentials.
    Then I should see an error and not be logged in.

  Scenario Outline: TC-BDD-LOGIN-004 Login with different user types
    Given I am on the login page.
    When I enter username "<username>" and password "secret_sauce" and click login.
    Then I should <expected_result>.
    Examples:
      | username          | expected_result        |
      | standard_user     | see the products page  |
      | locked_out_user   | see an error message   |
      | problem_user      | see the products page  |
