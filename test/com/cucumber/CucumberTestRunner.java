package com.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "test/com/cucumber/features",
        glue = {"com.cucumber.stepdefinitions"},
        format = {"pretty", "html:target/cucumber-reports"}
        //tags = { "~@wip","~@notImplemented","@sanity" }
)
public class CucumberTestRunner {
    // Use this class to run all the Cucumber Features at once
    // When you do mvn test this will run the sam as well
}