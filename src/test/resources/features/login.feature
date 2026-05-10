@login
Feature: OrangeHRM Login Page
  As an HR user
  I want to log in to OrangeHRM
  So that I can access the HR dashboard

  Background:
    Given the user is on the OrangeHRM login page

  @TestCase01
  Scenario: Login page should display OrangeHRM logo
    Then the OrangeHRM logo should be visible
    And the login form should be displayed


  @TestCase02
  Scenario: Admin should login with valid credentials
    When the user enters username "Admin" and password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the dashboard header "Dashboard" should be visible


  @TestCase03
  Scenario: Login with wrong password should show error
    When the user enters username "Admin" and password "wrongPass"
    And the user clicks the login button
    Then the error message "Invalid credentials" should be displayed


  @TestCase04
  Scenario: Login with wrong username should show error
    When the user enters username "wrongUser" and password "admin123"
    And the user clicks the login button
    Then the error message "Invalid credentials" should be displayed

  @TestCase05
  Scenario: Empty username should show required error
    When the user clears the username field
    And the user enters password "admin123"
    And the user clicks the login button
    Then the field error "Required" should be displayed under username

  @TestCase06
  Scenario: Empty password should show required error
    When the user enters username "Admin"
    And the user clears the password field
    And the user clicks the login button
    Then the field error "Required" should be displayed under password

  @TestCase07
  Scenario: Both fields empty should show required errors
    When the user clears the username field
    And the user clears the password field
    And the user clicks the login button
    Then the field error "Required" should be displayed under username
    And the field error "Required" should be displayed under password


  @TestCase08
  Scenario: Password field should be masked by default
    Then the password field should be of type "password"


  @TestCase09
  Scenario: Forgot password link should navigate to reset page
    When the user clicks the "Forgot your password?" link
    Then the user should be on the forgot password page
    And the heading "Reset Password" should be visible


  @TestCase10
  Scenario: Logged in user should be able to logout
    When the user enters username "Admin" and password "admin123"
    And the user clicks the login button
    And the user clicks on the user dropdown
    And the user clicks the logout option
    Then the user should be redirected to the login page


  @TestCase11
  Scenario Outline: Multiple invalid login attempts
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the error message "<error>" should be displayed

    Examples:
      | username  | password   | error               |
      | Admin     | wrongPass  | Invalid credentials |
      | wrongUser | admin123   | Invalid credentials |
      | wrongUser | wrongPass  | Invalid credentials |