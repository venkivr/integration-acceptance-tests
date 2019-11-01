package com.weatherapp.acceptance.helpers;

import org.springframework.stereotype.Component;

@Component
public class URLProperties {

    private TestProperties testProperties;

    public URLProperties(TestProperties testProperties) {
        this.testProperties = testProperties;
    }


    public String getBaseUrl() {
        return testProperties.getAppBaseUrl();
    }

}