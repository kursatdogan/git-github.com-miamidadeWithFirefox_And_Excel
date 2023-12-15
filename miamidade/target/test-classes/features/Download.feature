Feature: User should be able to download pdf files

  Scenario Outline: Downloading court documents
    Given user is on the login page
    When user click login button
    When user enters valid "sukruozen" and "Creature1*"
    Then the user should be able to login
    When I click StandardSearchButton
    When I click localCaseNumberTab
    When I enter "<year>" "<caseNumber>" "<caseCode>"
    When I click searchButton
    Then the title should be "CIVIL, FAMILY AND PROBATE COURTS ONLINE SYSTEM"
    When I click dockets
    When I download the pdf files to my folder

    Examples:
      | year | caseNumber   |          caseCode        |
      | 2020 | 010318       |    CA - Circuit Civil    |
      | 2020 | 009036       |    CA - Circuit Civil    |
