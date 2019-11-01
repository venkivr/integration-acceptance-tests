package com.weatherapp.acceptance.helpers;

import com.weatherapp.acceptance.state.RuntimeState;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class UtilMethods {

    @Autowired
    RuntimeState runtimeState;

    public String getCurrentURL() {
        return runtimeState.getWebDriver().getCurrentUrl();
    }

}
