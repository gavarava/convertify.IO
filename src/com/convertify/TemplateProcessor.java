package com.convertify;

import java.io.File;
import java.io.IOException;

import com.convertify.converter.xlshtml.ExcelToHtmlFileConverter;
public class TemplateProcessor
{
    /**
     * @author GaVaRaVa (https://www.linkedin.com/in/gaurav-edekar) First Argument = Path of the Excel Sheet Second
     */
    public static void main(String[] args) {

        // The Spreadsheet is created in such a way that it contains the
        // Template Path in the First Column (args[0])
        // args[1] is the destination
        // Default Names of Output File will be TemplateName_01.html and so on
        // if the name of template is TemplateName

        File spreadsheet = new File(args[0]);
        ExcelToHtmlFileConverter converter = new ExcelToHtmlFileConverter(spreadsheet, args[1]);
        if (converter.isBlank(args[0]) || converter.isBlank(args[1])) {
            throw new RuntimeException("Invalid paths to Excel Sheet or Destination");
        }

        try {
            System.out.println("Converting spreadsheet ..." + spreadsheet.getName());
            converter.convert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
