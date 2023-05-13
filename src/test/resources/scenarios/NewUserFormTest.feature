Feature: positive/negative testing user registration form

  Background: Background: log in page is opened
    Given open url "https://demoqa.com/login"

  Scenario: successful filling all fields and clicking register button
    Given registration form
    When type first name - "testFName", last name - "testLName", user name - "testUName", password - "testP1243"
    And click register button
    Then check error reCaptcha massage "Please verify reCaptcha to register!"

