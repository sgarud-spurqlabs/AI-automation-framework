package com.spurqlabs.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG Runner for Cucumber tests
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.spurqlabs.steps"},
    tags = "@smoke",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
