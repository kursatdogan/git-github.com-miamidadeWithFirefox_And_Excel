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
    When I download the pdf files to my folder with "<caseNumber>"
    #Given there are PDF files in the source folder "<caseNumber>"
    #When I move PDF files from the source folder to the destination folder
    #Then the PDF files should be moved successfully to the destination folder


    Examples:
      |order| year | caseNumber   |          caseCode        |
      |  1  | 2019 | 028901       |    CA - Circuit Civil    |
      |  2  | 2018 | 013392       |    CA - Circuit Civil    |
      |  3  | 2021 | 022543       |    CA - Circuit Civil    |
      |  4  | 2022 | 008422       |    CA - Circuit Civil    |
      |  5  | 2021 | 013536       |    CA - Circuit Civil    |
      |  6  | 2023 | 030994       |    CA - Circuit Civil    |
      |  7  | 2020 | 008134       |    CA - Circuit Civil    |
      |  8  | 2021 | 004571       |    CA - Circuit Civil    |
      |  9  | 2019 | 029418       |    CA - Circuit Civil    |
      |  10 | 2017 | 014622       |    CA - Circuit Civil    |
      |  11 | 2016 | 028254       |    CA - Circuit Civil    |
      |  12 | 2019 | 021293       |    CA - Circuit Civil    |
      |  13 | 2018 | 027988       |    CA - Circuit Civil    |
      |  14 | 2018 | 027560       |    CA - Circuit Civil    |
      |  15 | 2019 | 021717       |    CA - Circuit Civil    |
      |  16 | 2020 | 014438       |    CA - Circuit Civil    |
      |  17 | 2020 | 022938       |    CA - Circuit Civil    |
      |  18 | 2018 | 021054       |    CA - Circuit Civil    |
      |  19 | 2020 | 012996       |    CA - Circuit Civil    |
      |  20 | 2021 | 010854       |    CA - Circuit Civil    |



