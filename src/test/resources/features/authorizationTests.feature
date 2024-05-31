Feature: Testing the Authorization in the api

  @Auth
  Scenario: Successfully authorize a user and get a token
    Given a user with correct username and password
    When the user sends the request to "/Account/v1/GenerateToken"
    Then Response status code should be 200
    And the response should contain a "token"

  @Auth
  Scenario: Successfully authorize a user
    Given a user with correct username and password
    When the user sends the request to "Account/v1/Authorized"
    Then Response status code should be 200

  @Auth
  Scenario: Create and delete a user
    Given a user with username "TestAccount1111" and password "Password123!"
    And the user sends the request to "/Account/v1/User"
    And Response status code should be 201
    When A request sent to "/Account/v1/User/" to delete user "TestAccount1111" with password "Password123!"
    Then Response status code should be 204

  @Auth @Negative
  Scenario: Verify the api returns User exists exception
    Given a user with correct username and password
    When the user sends the request to "/Account/v1/User"
    Then Response status code should be 406
    And Response contains "message" with value "User exists!"

  @Auth @Negative
  Scenario Outline: Create account with password not matching the criteria
    Given a user with username <username> and password <password>
    When the user sends the request to "/Account/v1/User"
    Then Response status code should be 400

    Examples:
      | username  | password    |
      | "nikolac" | "Test1234"  |
      | "nikolac" | "Test123"   |
      | "nikolac" | "test!1234" |