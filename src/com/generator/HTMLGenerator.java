package com.generator;

import java.io.File;

import com.converter.xlshtml.ExcelToHtmlConverter;

public class HTMLGenerator {

	/**
	 * @author GaVaRaVa (https://www.linkedin.com/in/gaurav-edekar)
	 * First Argument = Path of the Excel Sheet
	 * Second Argument = Destination Directory of the Converter
	 */
	public static void main(String[] args) {

		// The Spreadsheet is created in such a way that it contains the
		// Template Path in the First Column
		// Default Names of Output File will be TemplateName_01.html and so on
		// if the name of template is TemplateName
		
		File spreadsheet = new File(args[0]);
		ExcelToHtmlConverter converter = new ExcelToHtmlConverter(spreadsheet,
				args[1]);
		
		if (converter.isBlank(args[0]) || converter.isBlank(args[1])) {
			System.err.println("Path of the Excel Sheet OR Destination of output file is not specified");
			System.exit(0);
		}
		
/*		File spreadsheet = new File("SomeFile.xls");
		ExcelToHtmlConverter converter = new ExcelToHtmlConverter(spreadsheet,
				"G:/DIR/AutoGenerated-HTML/");*/
		
		System.out.println("Converting...");
		converter.convert();
	}

}
