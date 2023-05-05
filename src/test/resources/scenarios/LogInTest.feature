Feature: tests for log in form

  Background: log in page is opened
    Given open url "https://demoqa.com/login"

  Scenario: Type username, password and click logIn Button. Check log in error massage.
    When log in "testName", "testPassword1234"
    And click logIn button
    Then check error massage is visible
    And massage have text - "Invalid username or password!"

  Scenario: Check username and password fields validation
    When log in "", ""
    And click logIn button
    Then check logIn field validation
    And check password field validation
    And refresh page
    When log in "", "testPassword3424"
    And click logIn button
    Then check logIn field validation
    And refresh page
    When log in "testName", ""
    When click logIn button
    Then check password field validation

  Scenario Outline: Test with data table and examples.
  Type username, password and click logIn Button.
  Check log in error massage.
    Given log in <username>, <password>
    When click logIn button
    Then check error massage is visible

    Examples:
      | username    | password   |
      | "testUser1" | "pass1111" |
      | "testUser2" | "pass2222" |
      | "testUser3" | "pass3333" |

  Scenario: Test with data table. Type username, password and click logIn Button. Check log in error massage.
    Given log in 1:
      | username | password |
      | test0    | pss0     |
      | test1    | pss1     |
      | test2    | pss2     |
    And click logIn button
    Then check error massage is visible
    And refresh page





