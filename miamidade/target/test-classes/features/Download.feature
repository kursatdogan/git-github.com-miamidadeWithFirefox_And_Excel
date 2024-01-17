Feature: User should be able to download pdf files

  Scenario: Downloading court documents
    Given user is on the login page
    When user click login button
    When user enters valid "sukruozen" and "Creature1*"
    Then the user should be able to login
    When I click StandardSearchButton
    When I click localCaseNumberTab
    When I perform the end-to-end process using Excel data
    When I move documents to dropbox





