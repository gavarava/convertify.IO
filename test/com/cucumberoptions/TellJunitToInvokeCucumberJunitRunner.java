package com.cucumberoptions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "test/com/features",
        glue = {"com.stepdefinitions"}
        //tags = { "~@wip","~@notImplemented","@sanity" }
)
public class TellJunitToInvokeCucumberJunitRunner {
}