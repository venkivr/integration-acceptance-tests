package com.weatherapp.acceptance.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan({
        "com.weatherapp.acceptance"
})
public class CucumberConfig {

    /**
     * static PropertySourcesPlaceholderConfigurer is required for the @Value annotations to work. Must be static
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
