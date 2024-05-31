Feature: Managing user collection

  Background:
    Given A token is generated

  @Collection
  Scenario: Create and delete a book from the user's collection
    Given the user adds a new book with ISBN "9781449337711" and title "Designing Evolvable Web APIs with ASP.NET"
    And the response status code should be 201
    And the response should contain the book with ISBN "9781449337711" and title "Designing Evolvable Web APIs with ASP.NET"
    When the user deletes the book with ISBN "9781449337711" from the collection
    Then the response status code should be 204
    And the book with ISBN "9781449337711" should no longer be in the collection

  @Collection
  Scenario Outline: Update a specific book
    Given the user adds a new book with ISBN <oldIsbn> and title <title>
    And the response status code should be 201
    When User puts a book with ISBN <newIsbn> to replace <oldIsbn>
    Then the response status code should be 200
    And the response should contain the book with ISBN <newIsbn> and title <title>
    And the user deletes the book with ISBN <newIsbn> from the collection
    And the response status code should be 204

    Examples:
      | oldIsbn         | title                                 | newIsbn         |
      | "9781593275846" | "Eloquent JavaScript, Second Edition" | "9781593277574" |
