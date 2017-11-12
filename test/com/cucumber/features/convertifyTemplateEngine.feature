Feature: Generate an HTML File according to a given template & dataset sourced from an Excel Sheet
  As a user who needs to auto-generate HTML according to a Template
  I should be able to generate the HTML files which follow the template initialized from the TemplateProcessor

  Background:
    Given : An Excel Sheet <TestSpreadSheet.xls>
    Given : An HTML Template as <TestTemplate.html>
    Given : An output directory as <test/resources/>
    Given : TemplateProcessor is instantiated with above information

  @CleanupFilesAfterRun
  Scenario: TemplateProcessor should generate the destination HTML in the destination directory
    When : TemplateProcessor is instantiated with all necessary information
    And  : generate method is invoked
    Then : HTML file is generated successfully in the destination directory