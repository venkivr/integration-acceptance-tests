package com.weatherapp.acceptance.steps.steps;


import com.weatherapp.acceptance.helpers.URLProperties;
import com.weatherapp.acceptance.helpers.UtilMethods;
import com.weatherapp.acceptance.state.RuntimeState;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class PageSteps {
    private static final Logger log = getLogger(PageSteps.class);

    @Autowired
    protected UtilMethods utilMethods;
    @Autowired
    protected RuntimeState runtimeState;
    @Autowired
    protected URLProperties urlProperties;


    public void assertEqualWithMessage(String expected, String found) {
        assertTrue("Expected " + expected + " but found " + found, expected.equalsIgnoreCase(found.replace("\n", "")));
    }

    public void assertNotEqualWithMessage(String expected, String found) {
        assertFalse("Expected " + expected + " to be not equal to " + found, expected.equalsIgnoreCase(found));
    }

    private String getCookieNamed(String cookieName) {
        try {
            return runtimeState.getWebDriver().manage().getCookieNamed(cookieName).getValue();
        } catch (Exception e) {
            return null;
        }
    }
}