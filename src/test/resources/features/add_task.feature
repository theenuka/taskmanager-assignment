Feature: Add new tasks to the Task Manager

  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Successfully add a valid task
    Given the application is running
    When I send a POST request to "/tasks" with the title "Buy groceries"
    Then the response status should be 201
    And the response should contain a task with the title "Buy groceries"

  Scenario: Attempt to add a task with an empty title
    Given the application is running
    When I send a POST request to "/tasks" with an empty title
    Then the response status should be 400