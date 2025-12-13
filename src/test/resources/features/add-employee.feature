# Story: ORNG-ST-009 - Add new employee
# Epic: ORNG-EP-002 - Employee Management
# As an HR admin
# I want to click `Add` to open the Add Employee flow
# So I can register a new employee in the system

@employee @pim
Feature: Add Employee
  As an HR administrator
  I want to add a new employee to the system
  So that employee records are maintained accurately

  Background:
    Given I am logged in as an admin user
    And I navigate to the PIM module


@smoke
  @ORNG-ST-009 @positive
  Scenario: Successfully add a new employee with all required fields
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | First Name   | John          |
      | Middle Name  | Michael       |
      | Last Name    | Doe           |
      | Employee Id  | AUTO          |
    And I click on the Save button
    Then the employee should be created successfully
    And I should see a success confirmation message
    And the new employee should appear in the Employee List

  @ORNG-ST-009 @positive
  Scenario: Successfully add a new employee with minimum required fields
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | First Name   | Jane          |
      | Last Name    | Smith         |
    And I click on the Save button
    Then the employee should be created successfully
    And I should see a success confirmation message

  @ORNG-ST-009 @positive
  Scenario: Add employee with custom Employee ID
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | First Name   | Robert        |
      | Last Name    | Johnson       |
      | Employee Id  | EMP-12345     |
    And I click on the Save button
    Then the employee should be created successfully
    And the employee ID should be "EMP-12345"

  @ORNG-ST-009 @negative
  Scenario: Validation - Cannot submit without First Name
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | Last Name    | Williams      |
    And I click on the Save button
    Then I should see a validation error for "First Name"
    And the employee should not be created

  @ORNG-ST-009 @negative
  Scenario: Validation - Cannot submit without Last Name
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | First Name   | Sarah         |
    And I click on the Save button
    Then I should see a validation error for "Last Name"
    And the employee should not be created

  @ORNG-ST-009 @cancel
  Scenario: Cancel adding employee should not create record
    When I click on the Add button
    Then the Add Employee form should be displayed
    When I enter the following employee details:
      | Field        | Value         |
      | First Name   | Cancel        |
      | Last Name    | Test          |
    And I click on the Cancel button
    Then I should be navigated back to the Employee List
    And the employee "Cancel Test" should not appear in the list
