@dailyForecast
Feature: Display 5 day weather forecast for a city

  @forecastDays
  Scenario: Landing page should display 5 day forecast
    Given I launch weather app
    When city name "Glasgow" is displayed on header
    Then 5 days of forecast should be displayed

  @threeHourlyForecast
  Scenario Outline: Landing page should display 3 hourly forecast
    Given I launch weather app
    When I click forecast for day <day>
    Then 3 hourly forecast is displayed for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @dominantWeatherCondition
  Scenario Outline: Landing page should display correct most dominant weather condition
    Given I launch weather app
    When I click forecast for day <day>
    Then details section is expanded for day <day>
    And most dominant weather condition is displayed in summary for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @dominantWindspeed
  Scenario Outline: Landing page should display correct most dominant wind speed condition
    Given I launch weather app
    When I click forecast for day <day>
    Then details section is expanded for day <day>
    And most dominant wind speed is displayed in summary for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @dominantWindDirection
  Scenario Outline: Landing page should display correct most dominant wind direction
    Given I launch weather app
    When I click forecast for day <day>
    Then most dominant wind direction is displayed in summary for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @minTemperature
  Scenario Outline: Landing page should display correct minimum temperature
    Given I launch weather app
    When I click forecast for day <day>
    Then minimum temperature is displayed correctly in summary for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @maxTemperature
  Scenario Outline: Landing page should display correct maximum temperature
    Given I launch weather app
    When I click forecast for day <day>
    Then maximum temperature is displayed correctly in summary for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @aggregateRainfall
  Scenario Outline: Landing page should display correct aggregate rainfall
    Given I launch weather app
    When I click forecast for day <day>
    Then aggregate rainfall is displayed correctly for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |

  @detailsSection
  Scenario Outline: It should be possible to expand and close day's forecast
    Given I launch weather app
    When I click forecast for day <day>
    Then hourly forecast is displayed for day <day>
    When I click forecast for day <day>
    Then details section is closed for day <day>
    Examples:
      | day |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |