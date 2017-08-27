package com.stepdefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.FileNotFoundException;

@CucumberOptions(plugin = {"pretty", "html:target/Destination"})
public class XLSToHTMLConversionTestSteps {

    @Given("^: An Excel Sheet with path to a template <(.*)> in the SampleFile column as <TestTemplate\\.html>$")
    public void setTestInputExcelSheet(String testInputExcelSheet) throws Throwable {
        File spreadsheet = new File(testInputExcelSheet);
        if (!spreadsheet.exists()) {
            throw new FileNotFoundException("Input Excel Sheet not found");
        }
    }

    @Given("^: A destination directory in the file system <(.*)>$")
    public void a_destination_directory_in_the_file_system(String outputPath) throws Throwable {
        File outputDirectory = new File(outputPath);
        if (!outputDirectory.exists() || !outputDirectory.isDirectory()) {
            throw new FileNotFoundException("Output path was not correct");
        }
    }

    @When("^: The ExcelToHtmlConverter is instantiated$")
    public void the_ExcelToHtmlConverter_is_instantiated() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^: It should validate that the Excel Sheet exists$")
    public void it_should_validate_that_the_Excel_Sheet_exists() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^:  that the destination directory exists on the file system$")
    public void that_the_destination_directory_exists_on_the_file_system() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^: convert method is invoked$")
    public void convert_method_is_invoked() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^: Generate the expected HTML file in the destination directory$")
    public void generate_the_expected_HTML_file_in_the_destination_directory() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
