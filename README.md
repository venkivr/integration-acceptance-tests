The test project is built using:
Cucumber
Spring
WebDriver
Page Object Model and Page Factory.

Tests will run in Chrome browser by default. However, this can be parametrized by application.properties e.g. chrome, firefox.
Please note the project supports the latest two versions of Chrome i.e. 77/78.

The tests exists in a Feature file named "weatherForecastDaily.feature"

In order to run the tests:
- Clone test project repository https://github.com/buildit/acceptance-testing.git
- Install (_npm install_) and start (_npm run develop_) the node first (Starting node as part of test build is work in progress)
- In Terminal, please browse to weather-app-integration-tests folder and execute the following command to run the tests.
**mvn clean install -PrunIntegrationTests** (_runIntegrationTests is the profile to run tests_).
- If running tests directly through IDE e.g. IntelliJ please update application.properties in test/resources to project folder. (This is work in progress to pick directory dynamically)
- Please browse to WeatherAppTestRunner.java file, right-click on it, and select "Run WeatherAppTestRunner" option.
- The **baseDir in application.properties** needs to be amended to the project's base directory.
- Test report is available in weather-app/weather-app-integration-tests/target/cucumber-html-report/index.html

As city constant is hardcoded to "Glasgow" in index.js file, it is not possible to test scenarios for other valid and invalid city names.

There is a missing requirement for atmospheric pressure.

Assumption:
By 'Most dominant', I assumed 'most commonly occurring'.
Two tests for Dominant Weather Condition and 4 tests for Dominant Speed are failing because they are not correctly determined by the underlying code. It is a defect which needs to be investigated.

Time constraints:
If more time was given, I would perform the additional tasks below.
- Looking at "Accessibility" aspects of the Weather App.
- Cover additional scenarios, particularly, edge cases.
- Built a script that would start the server
- Dynamically pick up project base directory
- Tidying up the code, removing unused code, adding comments to methods.
