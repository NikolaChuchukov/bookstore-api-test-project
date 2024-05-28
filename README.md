## Description

This is a project for testing the main functionalities of bookstore api. It includes tests for User authorization, managing user's book collection and validating book details.

## Project structure

This is a maven project build in java 11, using TestNG framework and tools such as Cucumber.

Feature files with the test scenarios: _src/test/resources/features/_

Step definitions with implemented steps: _src/test/java/stepDefs_

Common directory containing different helper classes: _src/test/java/common_

Cucumber runner: _src/test/java/runner_


## Steps to build

### Run from console
1) If you don't have Maven installed, you can install it following the steps described here: https://phoenixnap.com/kb/install-maven-windows
2) Checkout project from Git
3) Run "_mvn clean install_" in the terminal from the source root of the project
4) Run mvn test

### Run from IDE
1) Checkout project from Git
2) Run the Cucumber test Runner under src/test/java/runner/

## Generating a report

The framework is creating an Allure report after each test run. To install Allure please follow the steps described in the Allure documentation - https://allurereport.org/docs/install/

Results of the tests are stored in the _target/allure-results_ directory. To generate report, after the test run, execute the following command in the terminal "_allure generate target/allure-results --clean_".

If you want to see the report immidiately you can run "_allure serve target/allure-results --clean_" and the report will be opened in the default browser.

There is a report example in the source of this github repo under allure-report. To see it you can open the index.html file in a browser.

