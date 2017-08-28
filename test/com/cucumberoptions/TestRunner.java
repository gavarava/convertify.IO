package com.cucumberoptions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "test/com/features",
        glue = {"com.stepdefinitions"},
        format = {"pretty", "html:target/Destination"}
        //tags = { "~@wip","~@notImplemented","@sanity" }
)
public class TestRunner {
    // Use this class to run all the Cucumber Features at once
    // When you do mvn test this will run the sam as well
}