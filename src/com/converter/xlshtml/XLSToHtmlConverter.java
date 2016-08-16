package com.converter.xlshtml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.converter.SpreadsheetToTemplateConverter;
import com.html.template.HTMLTemplate;
import com.spreadsheet.reader.XLSToJavaMapper;

/**
 * Creates the base for converting Excel Sheet Details into an HTML file
 * according to a template
 * 
 * @author gaurav.edekar
 * 
 */
public class XLSToHtmlConverter implements SpreadsheetToTemplateConverter {

	private static final String PLC_HOLDR_PREFIX = "k:";

	private static final String PLC_HOLDR_SUFFIX = ":k";

	private File xLSSheet;

	private String destinationDir;

	public XLSToHtmlConverter(File pXLSSheet, String pDestinationDir) {
		this.destinationDir = pDestinationDir;
		this.xLSSheet = pXLSSheet;
	}

	public File getXLSSheet() {
		return xLSSheet;
	}

	public void setXLSSheet(File mXLSSheet) {
		this.xLSSheet = mXLSSheet;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String mDestinationDir) {
		this.destinationDir = mDestinationDir;
	}

	/**
	 * Convert an Excel Sheet to an HTML conforming to the template
	 */
	@Override
	public void convertSpreadsheetToTemplate() {

		XLSToJavaMapper excelDataMap = new XLSToJavaMapper(getXLSSheet());

		if (excelDataMap.isSheetValid()) {

			Map<String, List<Map<String, String>>> fileTemplateMap = excelDataMap
					.buildDataMapFromSpreadsheet(getXLSSheet());

			if (fileTemplateMap != null && !fileTemplateMap.isEmpty()) {
				String templateFile = "";
				for (Entry<String, List<Map<String, String>>> dataMapEntry : fileTemplateMap
						.entrySet()) {
					templateFile = dataMapEntry.getKey();             

					HTMLTemplate htmlTemplate = new HTMLTemplate(new File(
							templateFile));

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
																getDestinationDir()
																		+ rowCount
																		+ "_"
																		+ htmlTemplate
																				.getTemplateName()),
																		convertedTemplate);
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									System.err
											.println("ERROR fetching data from Excel Sheet.");
								}
								rowCount++;
							}
							System.out.println(rowCount-1 + " row/s for " +templateFile+ " have been processed successfully !!");

						} else {
							System.err.println("HTML Template <" + templateFile
									+ "> is blank.");
						}

					} else {
						System.err.println("HTML Template <" + templateFile
								+ "> is invalid OR it does not exist.");
					}
				}
			}
		} else {
			System.err.println("Not able to detect Excel Sheet.");
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
			plcHolderBuffer = plcHolderBuffer.append(PLC_HOLDR_PREFIX)
					.append(dataEntry.getKey()).append(PLC_HOLDR_SUFFIX);
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
