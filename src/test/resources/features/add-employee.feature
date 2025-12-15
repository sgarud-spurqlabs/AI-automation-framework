Feature: Add Employee to OrangeHRM PIM
  As an HR administrator
  I want to add new employees to the system
  So that employee records are properly maintained

  Background:
    Given I am logged in as an HR administrator
    And I navigate to PIM Employee List

  @smoke @ORAN-TC-002
  Scenario: Create employee with all fields
    When I click the Add button
    And I enter the following employee details:
      | field      | value      |
      | firstName  | John       |
      | middleName | Michael    |
      | lastName   | Doe        |
    And I click Save
    Then I should see the success message
    And the employee should appear in the employee list

  @smoke @ORAN-TC-003
  Scenario: Create employee with minimum required fields
    When I click the Add button
    And I enter the following employee details:
      | field     | value |
      | firstName | Jane  |
      | lastName  | Smith |
    And I click Save
    Then I should see the success message
    And the employee should appear in the employee list

  @positive @ORAN-TC-004
  Scenario: Create employee with custom Employee ID
    When I click the Add button
    And I clear the Employee ID field
    And I enter the following employee details:
      | field      | value     |
      | firstName  | Robert    |
      | middleName | Edward    |
      | lastName   | Johnson   |
      | employeeId | EMP-12345 |
    And I click Save
    Then I should see the success message
    And the employee details should show Employee ID as "EMP-12345"

  @negative @ORAN-TC-005
  Scenario: Validation error for missing First Name
    When I click the Add button
    And I enter the following employee details:
      | field    | value    |
      | lastName | Williams |
    And I click Save
    Then I should see error message "Required" for First Name field
    And the employee should not be created

  @negative @ORAN-TC-006
  Scenario: Validation error for missing Last Name
    When I click the Add button
    And I enter the following employee details:
      | field     | value |
      | firstName | Sarah |
    And I click Save
    Then I should see error message "Required" for Last Name field
    And the employee should not be created

  @negative @ORAN-TC-007
  Scenario: Validation error for invalid characters in name fields
    When I click the Add button
    And I enter the following employee details:
      | field     | value    |
      | firstName | John123  |
      | lastName  | Smith@#  |
    And I click Save
    Then I should see validation error for invalid characters

  @negative @ORAN-TC-008
  Scenario: Validation error for exceeding max length
    When I click the Add button
    And I enter the following employee details:
      | field     | value                            |
      | firstName | Verylongnamethatexceedsthirtych |
      | lastName  | Smith                            |
    And I click Save
    Then I should see error message "Should not exceed 30 characters"

  @cancel @ORAN-TC-010
  Scenario: Cancel employee creation
    When I click the Add button
    And I enter the following employee details:
      | field     | value  |
      | firstName | Cancel |
      | lastName  | Test   |
    And I click Cancel
    Then I should be on the Employee List page
    And the employee "Cancel Test" should not appear in the list
