package com.converter.xlshtml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.converter.SpreadsheetConverter;
import com.html.template.HTMLTemplate;
import com.spreadsheet.reader.ExcelToJavaMapper;

/**
 * Creates the base for converting Excel Sheet into an HTML file
 * according to an HTML template
 * @author gaurav.edekar
 */
public class ExcelToHtmlConverter implements SpreadsheetConverter {

	private static final String PLACE_HOLDER_PREFIX = "k:";

	private static final String PLACE_HOLDER_SUFFIX = ":k";

	private File excelSheet;

	private String destinationDirectory;

	public ExcelToHtmlConverter(File pXLSSheet, String pDestinationDir) {
		this.destinationDirectory = pDestinationDir;
		this.excelSheet = pXLSSheet;
	}

	public File getSpreadsheet() {
		return excelSheet;
	}

	public String getDestinationDirectory() {
		return destinationDirectory;
	}

	/**
	 * Convert an Excel Sheet to an HTML conforming to the template
	 */
	public void convert() throws IOException {

		File spreadsheet = getSpreadsheet();
		ExcelToJavaMapper excelDataMap = new ExcelToJavaMapper(spreadsheet);

		if (excelDataMap.validate()) {

			Map<String, List<Map<String, String>>> fileTemplateMap = excelDataMap
					.buildMapFromSpreadsheet(spreadsheet);

			if (fileTemplateMap != null && !fileTemplateMap.isEmpty()) {
				String templateFile = "";
				for (Entry<String, List<Map<String, String>>> dataMapEntry : fileTemplateMap
						.entrySet()) {
					templateFile = dataMapEntry.getKey();             

					HTMLTemplate htmlTemplate = new HTMLTemplate(new File(
							spreadsheet.getParent()+"\\"+templateFile+"\\"));

					if (htmlTemplate.isValid()) {

						// Convert the HTML Template into a String Format
						String htmlTemplateString = htmlTemplate.toString();

						if (!isBlank(htmlTemplateString)) {
							// Get the Placeholder-Data Map from the Template
							// Map generated from Excel Sheet
							List<Map<String, String>> htmlDataMapList = fileTemplateMap
									.get(templateFile);

							int rowCount = 1;
							for (Map<String, String> htmlDataMap : htmlDataMapList) {
								if (htmlDataMap != null
										&& htmlDataMap.size() > 0) {
									String convertedTemplate = imposeDataOnTemplate(
											htmlDataMap, htmlTemplateString);

									try {
										FileUtils
												.writeStringToFile(
														new File(
																getDestinationDirectory()+"\\"+
																		+ rowCount
																		+ "_"
																		+ htmlTemplate
																				.getName()),
																		convertedTemplate);
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									throw new RuntimeException("ERROR fetching data from Excel Sheet.");
								}
								rowCount++;
							}
							System.out.println(rowCount-1 + " row/s for " +templateFile+ " have been processed successfully !!");

						} else {
							throw new RuntimeException("HTML Template <" + templateFile
									+ "> is blank.");
						}

					} else {
						throw new RuntimeException("HTML Template <" + templateFile
								+ "> is invalid OR it does not exist.");
					}
				}
			}
		} else {
			throw new RuntimeException("Not able to detect Excel Sheet.");
		}
	}

	/**
	 * Fill HTML Template with Data in the pDataMap
	 * 
	 * @param pDataMap
	 * @param pTemplateString
	 */
	private String imposeDataOnTemplate(Map<String, String> pDataMap,
			String pTemplateString) {

		for (Entry<String, String> dataEntry : pDataMap.entrySet()) {
			StringBuffer plcHolderBuffer = new StringBuffer();
			plcHolderBuffer = plcHolderBuffer.append(PLACE_HOLDER_PREFIX)
					.append(dataEntry.getKey()).append(PLACE_HOLDER_SUFFIX);
			String placeHolder = plcHolderBuffer.toString();
			pTemplateString = pTemplateString.replaceAll(
					Pattern.quote(placeHolder),
					Matcher.quoteReplacement(dataEntry.getValue()));
		}

		return pTemplateString;

	}

	public boolean isBlank(String pCheckString) {
		return (pCheckString == null || "".equalsIgnoreCase(pCheckString) || pCheckString
				.trim().length() == 0);
	}

}