Feature: Convert Excel sheet data to HTML according to a Template
  As a user who needs to auto-generate HTML according to a Template
  I should be able to generate the HTML files which follow the template mentioned in the Excel Sheet if it exists in the path

  Background:
    Given : An Excel Sheet with path to a template <TestSpreadSheet.xls> in the SampleFile column as <TestTemplate.html>
    Given : A destination directory in the file system <test/resources/>

  Scenario: ExcelToHtmlConverter is instantiated with valid Excel Sheet & Output Destination directory
    When : The ExcelToHtmlConverter is instantiated
    Then : ExcelToHtmlConverter should be instantiated if parameters are valid

  @CleanupFilesAfrerRun
  Scenario: If run with all valid arguments, it should generate the HTML in the destination directory
    When : The ExcelToHtmlConverter is instantiated
    And : convert method is invoked
    Then : The expected HTML file is generated in the destination directory