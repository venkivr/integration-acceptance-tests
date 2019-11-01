package com.weatherapp.acceptance.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("TestProperties")
public class TestProperties {
    private Environment environment;        //Environment properties

    public TestProperties(Environment environment) {
        this.environment = environment;
    }

    @Value("${base.url}")
    private String baseUrl;


    public String getAppBaseUrl() {
        return baseUrl;
    }
}