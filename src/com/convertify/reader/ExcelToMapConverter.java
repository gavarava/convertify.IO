package com.convertify.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelToMapConverter implements SpreadsheetToMapConverter {

	private static final int HEADER_ROW_INDEX = 0;

	private File excelSheet;

	public ExcelToMapConverter(File excelSheet) {
		if (isValid(excelSheet)) {
			this.excelSheet = excelSheet;
		} else {
			throw new RuntimeException("Excel Sheet was invalid");
		}
	}

	private boolean isValid(File excelSheet) {
		return (excelSheet != null && excelSheet.exists() && !excelSheet
				.isDirectory());
	}

	public Map<String, List<Map<String, String>>> convertExcelSheetToMap(
			File excelSheet) {

		if (isValid(excelSheet)) {
			Map<String, List<Map<String, String>>> fileTemplateMap = new TreeMap<String, List<Map<String, String>>>();
			try {
				InputStream excelInpStream = new FileInputStream(excelSheet);
				Workbook workBook = new HSSFWorkbook(excelInpStream);
				Sheet sheet = workBook.getSheetAt(0);
				Row headRow = sheet.getRow(HEADER_ROW_INDEX);

				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row dataRow = sheet.getRow(i);
					Map<String, String> headerRowMap = new TreeMap<String, String>();
					List<Map<String, String>> commonTemplateDataList = null;
					if (dataRow != null && headRow != null) {
						if (processExcelSheetHeaders(headRow, dataRow,
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
	private String cellAtIndex(Row row, int fileColumnIndex) {
		String cellString = null;
		if (row != null) {
			Cell rowCell = row.getCell(fileColumnIndex);
			if (rowCell != null) {
				cellString = rowCell.toString();
			}
		}
		return cellString;
	}

	private boolean processExcelSheetHeaders(Row headerRow, Row dataRow,
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

}
