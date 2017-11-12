package com.convertify.data.reader;

import com.convertify.data.DataCell;
import com.convertify.data.InvalidSpreadsheetCell;
import com.convertify.data.MSExcelDataSet;
import com.convertify.data.DataRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MSExcelReader implements DatasetReader {

	private static final int HEADER_ROW_INDEX                      = 0;
	private static final int ROW_AFTER_HEADER                      = 1;
	private static final int EXCEL_SHEET_ROWCOUNT_INCLUDING_HEADER = 2;

	private File  source;
	private Sheet excelWorkSheet;
	private Row   excelHeader;

	public MSExcelReader() {
		// For testing
	}

	public MSExcelReader(File sourceFile) throws FileNotFoundException {
		if (fileIsInvalid(sourceFile)) {
			throw new FileNotFoundException();
		}
		this.source = sourceFile;
	}

	boolean fileIsInvalid(File sourceFile) {
		return !sourceFile.exists();
	}

	@Override
	public MSExcelDataSet<DataRow> read() throws IOException {
		collectMetaData();
		return collectDatarowsIntoDataSet();
	}

	void collectMetaData() throws IOException {
		readExcelWorksheet();
		if (!worksheetHasData()) {
			throw new UnsupportedOperationException("Cannot initialize Dataset without any data");
		}
		Row header = excelWorkSheet.getRow(HEADER_ROW_INDEX);
		setExcelHeader(header);
	}

	private void readExcelWorksheet() throws IOException {
		Sheet activeSheet = getFirstActiveSheetExcelFile();
		setExcelWorkSheet(activeSheet);
	}

	private boolean worksheetHasData() {
		return excelWorkSheet.getPhysicalNumberOfRows() >= EXCEL_SHEET_ROWCOUNT_INCLUDING_HEADER;
	}

	MSExcelDataSet<DataRow> collectDatarowsIntoDataSet() {
		MSExcelDataSet<DataRow> dataSet = new MSExcelDataSet<>();
		int totalRowsInExcelSheet = excelWorkSheet.getPhysicalNumberOfRows();
		for (int rowIndex = ROW_AFTER_HEADER; rowIndex < totalRowsInExcelSheet; rowIndex++) {
			Row rowFromExcelSheetAtIndex = excelWorkSheet.getRow(rowIndex);
			dataSet.add(createDataRowFromHeaderRowAndExcelRow(excelHeader, rowFromExcelSheetAtIndex));
		}
		return dataSet;
	}

	Sheet getFirstActiveSheetExcelFile() throws IOException {
		Workbook workbook;
		int workbookActiveSheetIndex;
		try (InputStream inputStream = new FileInputStream(source)) {
			workbook = new HSSFWorkbook((inputStream));
			workbookActiveSheetIndex = workbook.getActiveSheetIndex();
		}
		return workbook.getSheetAt(workbookActiveSheetIndex);
	}

	public void setExcelWorkSheet(Sheet excelWorkSheet) {
		this.excelWorkSheet = excelWorkSheet;
	}

	public void setExcelHeader(Row excelHeader) {
		this.excelHeader = excelHeader;
	}

	void setSource(File source) {
		this.source = source;
	}

	DataRow createDataRowFromHeaderRowAndExcelRow(Row headerRow, Row dataRow) {
		List<DataCell> cellsForDataRow = new ArrayList<>();
		int numberOfCells = headerRow.getLastCellNum();
		for (int cellIndex = 0; cellIndex < numberOfCells; cellIndex++) {
			try {
				DataCell cellAtIndex = createDataCellFromHSSFRows(headerRow, dataRow, cellIndex);
				cellsForDataRow.add(cellAtIndex);
			} catch (InvalidSpreadsheetCell e) {
				System.err.println("Skipping cell " + cellIndex + " at row " + dataRow.getRowNum());
			}
		}
		return new DataRow(cellsForDataRow);
	}

	private DataCell createDataCellFromHSSFRows(Row headerRow, Row dataRow, int cellIndex)
			throws InvalidSpreadsheetCell {
		Cell dataTypeFromHeader = headerRow.getCell(cellIndex);
		Cell dataRowCell = dataRow.getCell(cellIndex);
		return createDataCellFromCellHeaderAndCellValue(dataTypeFromHeader.getStringCellValue(), dataRowCell);
	}

	DataCell createDataCellFromCellHeaderAndCellValue(String cellHeader, Cell cell)
			throws InvalidSpreadsheetCell {
		return new DataCell(cellHeader, cell.getStringCellValue());
	}
}
