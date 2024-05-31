Feature: Testing the book functionalities in the api

  @Books
  Scenario: Get all books
    When User sends a GET request to "/BookStore/v1/Books"
    Then the response status code should be 200
    And the response should contain a list of books

  @Books1
  Scenario: Get a specific book
    Given A token is generated
    When User gets a book with ISBN "9781449325862"
    Then the response status code should be 200
    And the book details should be as follows:
      | key      | value                     |
      | isbn     | 9781449325862             |
      | title    | Git Pocket Guide          |
      | author   | Richard E. Silverman      |
      | publisher| O'Reilly Media            |
      | pages    | 234                       |
