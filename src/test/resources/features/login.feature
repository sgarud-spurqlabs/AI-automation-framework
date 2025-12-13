Feature: OrangeHRM Login Functionality
  As a user of OrangeHRM
  I want to be able to login to the application
  So that I can access the dashboard

  Background:
    Given I navigate to the OrangeHRM login page

 @login
  Scenario: Successful login with valid credentials
    When I enter valid username and password
    And I click on the login button
    Then I should be redirected to the dashboard
    And I should see the Dashboard heading
