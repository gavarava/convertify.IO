# convertify.IO
A Simple Spreadsheet to HTML Converter that can be used to auto generate repeatable HTML pages using a given template.
It converts XLS or XLSX files to an HTML template provided in a path.

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