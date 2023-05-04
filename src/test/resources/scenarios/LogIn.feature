Feature: tests for log in/register form

  Background: log in page is opened
    Given open url "https://demoqa.com/login"

  Scenario: Type username, password and click logIn Button. Check log in error massage.
    Given log in "testName", "testPassword1234"
    When click logIn button
    Then check error massage is visible
    And massage have text - "Invalid username or password!"

  Scenario: Check username and password fields validation
    Given log in "", ""
    When click logIn button
    Then check logIn field validation
    And check password field validation
    And refresh page
    Given log in "", "testPassword3424"
    When click logIn button
    Then check logIn field validation
    And refresh page
    Given log in "testName", ""
    When click logIn button
    Then check password field validation


