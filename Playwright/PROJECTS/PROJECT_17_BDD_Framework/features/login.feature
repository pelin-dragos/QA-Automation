Feature: Login Functionality
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be logged in successfully
    And I should see the products page

  Scenario: Failed login with invalid credentials
    Given I am on the login page
    When I enter username "invalid_user"
    And I enter password "invalid_password"
    And I click the login button
    Then I should see an error message
    And I should not be logged in

  Scenario: Login with empty fields
    Given I am on the login page
    When I click the login button without entering credentials
    Then I should see an error message
    And I should not be logged in

  @smoke
  Scenario Outline: Login with different user types
    Given I am on the login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see "<expected_result>"

    Examples:
      | username              | password      | expected_result    |
      | standard_user         | secret_sauce  | products page      |
      | locked_out_user       | secret_sauce  | error message     |
      | problem_user          | secret_sauce  | products page      |
