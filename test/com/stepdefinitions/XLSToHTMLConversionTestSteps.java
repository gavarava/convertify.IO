package com.stepdefinitions;

import com.converter.xlshtml.ExcelToHtmlConverter;
import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.TestFailure;

import java.io.File;
import java.io.FileNotFoundException;

@CucumberOptions(plugin = {"pretty", "html:target/CucumberTestReports"})
public class XLSToHTMLConversionTestSteps {

    private ExcelToHtmlConverter converter;
    private File spreadSheet;
    private File destinationDirectory;

    @Given("^: An Excel Sheet with path to a template <(.*)> in the SampleFile column as <TestTemplate\\.html>$")
    public void setTestInputExcelSheet(String testInputExcelSheet) throws Throwable {
        spreadSheet = new File("test/resources/" + testInputExcelSheet);
        if (!spreadSheet.exists()) {
            throw new FileNotFoundException("Input Excel Sheet not found");
        }
    }

    @Given("^: A destination directory in the file system <(.*)>$")
    public void a_destination_directory_in_the_file_system(String outputPath) throws Throwable {
        destinationDirectory = new File(outputPath);
        if (!destinationDirectory.exists() || !destinationDirectory.isDirectory()) {
            throw new FileNotFoundException("Output path was not correct");
        }
    }

    @When("^: The ExcelToHtmlConverter is instantiated$")
    public void the_ExcelToHtmlConverter_is_instantiated() throws Throwable {
        converter = new ExcelToHtmlConverter(spreadSheet, destinationDirectory.getAbsolutePath());
    }

    @Then("^: ExcelToHtmlConverter should be instantiated if parameters are valid")
    public void it_should_validate_that_the_Excel_Sheet_exists() throws Throwable {
        if (converter == null) {
            throw new Exception("Could Not Instantiate ExcelToHtmlConverter");
        }
    }

    @And("^: convert method is invoked$")
    public void convert_method_is_invoked() throws Throwable {
        converter.convert();
    }

    @Then("^: The expected HTML file is generated in the destination directory$")
    public void generate_the_expected_HTML_file_in_the_destination_directory() throws Throwable {
        assert destinationDirectory.listFiles().length > 2;
    }

    @After("@CleanupFilesAfrerRun")
    public void cleanup_generated_files() {
        cleanupFilesStartingWithNumber();
    }

    private void cleanupFilesStartingWithNumber() {
        File[] files = destinationDirectory.listFiles();
        for (File file : files) {
            if (file.getName().startsWith("1")) {
                file.delete();
            }
        }
    }

}
