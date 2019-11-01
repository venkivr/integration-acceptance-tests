package com.weatherapp.acceptance.webdriver;

import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebDriverConfiguration {

    private Environment environment;

    public WebDriverConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Scope("thread")
    public CucumberWebDriver cucumberWebDriver() {

        return DriverFactory.getInstance(environment);
    }
}