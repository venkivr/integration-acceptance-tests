package com.weatherapp.acceptance.webdriver.environments;

import com.weatherapp.acceptance.helpers.OSDetector;
import com.weatherapp.acceptance.webdriver.drivers.CucumberWebDriver;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RuntimeEnvironment {

    private static Logger LOG = LoggerFactory.getLogger(RuntimeEnvironment.class);

    private final String projectBaseDir;

    RuntimeEnvironment(Environment environment) {
        String projectBaseDir = System.getProperty("baseDir") != null ? System.getProperty("baseDir") : environment.getProperty("baseDir");
        this.projectBaseDir = projectBaseDir == null ? System.getProperty("user.dir") : projectBaseDir;
    }

    public abstract void initialise();

    public abstract CucumberWebDriver createWebDriver();

    public abstract String getDriverVersion();

    public Integer getLocallyInstalledVersion() {
        return null;
    }

    private String getProjectBaseDir() {
//        return projectBaseDir + "/weather-app-integration-tests";
        return projectBaseDir;
    }

    String getDriverPath(String browser, String driverName) {
        return Paths.get(getProjectBaseDir(), "drivers", OSDetector.getOSFamily(), browser, getDriverVersion(), getExecutableDriver(driverName)).toString();
    }

    private String getExecutableDriver(String driverName) {

        StringBuilder name = new StringBuilder(driverName);

        if (OSDetector.isWindows()) {
            name.append(".exe");
        }

        return name.toString();
    }

    synchronized Integer runCommandForResult(List<String> commandParams, String extractionPattern) {

        ProcessBuilder processBuilder = new ProcessBuilder(commandParams);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            String commandOutput = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());

            Pattern compile = Pattern.compile(extractionPattern);
            Matcher matcher = compile.matcher(commandOutput);

            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                throw new RuntimeException("Unable to find a match for extractionPattern '" + extractionPattern + "' run on command output '" + String.join(" ", commandParams) + "'");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error attempting to run command '" + String.join(" ", commandParams) + "'", e);
        }
    }
}
