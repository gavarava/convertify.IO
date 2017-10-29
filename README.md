# convertify.IO
Template processor which can support creation of HTML pages with input from a Spreadsheet.
It should be able to support multiple Spreadsheet types in the future, now it supports only Microsoft Excel Sheets.

# Create Runnable jar with the following
mvn clean compile assembly:single

#Usage
java -jar <JAR_NAME> <Path of the Excel Sheet> <OutputDestination>

# BDD
Check Cucumber feature. Run using mvn test.

# Use mvn assembly:single to generate Jar File With Dependancies

# Intention
    > Basic Maven Configuration
    > Behaviour Driven Development using Cucumber
    > Refactor this code
    > Start a Spring Boot Microservice.


# Example POM for SPOCK, another BDD
https://github.com/spockframework/spock-example

# Update October 2017
On using the 5 whys technique, I discovered (within 2 whys) that this project is not a File Converter.
It does not convert files from Excel to HTML. Because in that case my outpur should have been an HTML file with a HTML Table.
This is a template processor.
