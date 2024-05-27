## Description

This is a project for testing the main functionalities of bookstore api. It includes tests for User authorization, managing user's book collection and validating book details.

## Project structure

This is a maven project build in java 11, using TestNG framework and tools such as Cucumber.

## Steps to build

### Run from console
1) If you don't have Maven installed, you can install it following the steps described here: https://phoenixnap.com/kb/install-maven-windows
2) Checkout project from Git
3) Run "mvn clean install" in the terminal from the source root of the project
4) Run mvn test

### Run from IDE
1) Checkout project from Git
2) Run the Cucumber test Runner under src/test/java/runner/

## Generating a report

Results of the tests are stored in the target/allure-results directory. To generate report, after the test run, run the following command in terminal "allure generate target/allure-results".

If you want to see the report immidiately you can run "allure serve target/allure-results" and the report will be opened in the default browser.

There is a report example in the source of this github repo under allure-report. To see it you can open the index.html file in a browser.

