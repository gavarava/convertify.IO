package com.spreadsheet.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToJavaMapper implements SpreadsheetToMapConverter {

	private static final int HEADER_ROW_INDEX = 0;

	private File excelSheet;

	public ExcelToJavaMapper(File excelSheet) {
		this.excelSheet = excelSheet;
	}

	public boolean validate() {
		return (this.excelSheet != null && this.excelSheet.exists() && !this.excelSheet
				.isDirectory());
	}

	/**
	 * Create a nested map with an entry for each File to be generated & its
	 * information
	 */
	@Override
	public Map<String, List<Map<String, String>>> buildMapFromSpreadsheet(
			File spreadsheet) {

		Map<String, List<Map<String, String>>> fileTemplateMap = new TreeMap<String, List<Map<String, String>>>();
		if (validate()) {
			try {
				InputStream excelInpStream = new FileInputStream(spreadsheet);
				Workbook workBook = WorkbookFactory.create(excelInpStream);
				Sheet sheet = workBook.getSheetAt(0);
				Row headRow = sheet.getRow(HEADER_ROW_INDEX);

				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row dataRow = sheet.getRow(i);
					Map<String, String> headerRowMap = new TreeMap<String, String>();
					List<Map<String, String>> commonTemplateDataList = null;
					if (dataRow != null && headRow != null) {
						if (buildHeaderDataRowMap(headRow, dataRow,
								headerRowMap)) {
							String headerRowMapTemplateName = headerRowMap
									.remove(cellAtIndex(headRow, 0));

							if (fileTemplateMap
									.containsKey(headerRowMapTemplateName)) {
								commonTemplateDataList = fileTemplateMap
										.get(headerRowMapTemplateName);
								// Update List of headerRowMap's
								commonTemplateDataList.add(headerRowMap);

							} else {
								// Create the list if this is the first time we
								// encounter the Sample File Name
								commonTemplateDataList = new ArrayList<Map<String, String>>();
								// Update List of headerRowMap's
								commonTemplateDataList.add(headerRowMap);
								// Update the Outer fileTemplateMap with the new
								// List of headerRowMap's
								fileTemplateMap.put(headerRowMapTemplateName,
										commonTemplateDataList);
							}

						}
					} else {
						System.err.println("Row number " + i + " is empty...");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			return fileTemplateMap;
		}
		return null;
	}

	/**
	 * Fetches a Cell at certain index from a Row
	 * 
	 * @param row
	 * @param fileColumnIndex
	 * @return cellString
	 */
	public static String cellAtIndex(Row row, int fileColumnIndex) {
		String cellString = null;
		if (row != null) {
			Cell rowCell = row.getCell(fileColumnIndex);
			if (rowCell != null) {
				cellString = rowCell.toString();
			}
		}
		return cellString;
	}

	/**
	 * Build a Map of Header values as the Key & the Corresponding Columns in
	 * subsequent rows as the value
	 * 
	 * @param headerRow
	 * @param dataRow
	 * @param headerRowMap
	 */
	public static boolean buildHeaderDataRowMap(Row headerRow, Row dataRow,
			Map<String, String> headerRowMap) {

		if (headerRow != null && dataRow != null) {
			if (headerRow.getPhysicalNumberOfCells() < dataRow
					.getPhysicalNumberOfCells()) {
				System.err
						.println("Header Row and Data Row cell numbers are different...");
				return false;
			} else {
				for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
					String headerKey = cellAtIndex(headerRow, i);
					String dataValue = cellAtIndex(dataRow, i);
					if (headerKey != null && dataValue != null) {
						headerRowMap.put(headerKey, dataValue);
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private static String replaceSpaces(String intialString) {
		if (intialString.contains(" ")) {
			intialString = intialString.replaceAll(" ", "&nbsp;");
		}
		return intialString;
	}
}
