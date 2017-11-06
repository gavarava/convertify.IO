package com.convertify.data.reader;

import com.convertify.data.DataCell;
import com.convertify.data.MSExcelDataSet;
import com.convertify.data.DataRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

public class MSExcelReader implements DatasetReader {

	private static final int HEADER_ROW_INDEX                      = 0;
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

	MSExcelDataSet<DataRow> collectDatarowsIntoDataSet() {
		return null;
	}

	private void collectMetaData() throws IOException {
		readExcelWorksheet();
		if (!worksheetHasData()) {
			throw new UnsupportedOperationException("Cannot initialize Dataset without any data");
		}
		Row excelHeader = excelWorkSheet.getRow(HEADER_ROW_INDEX);
		setExcelHeader(excelHeader);
	}

	private void readExcelWorksheet() throws IOException {
		Sheet activeSheet = getFirstActiveSheetExcelFile();
		setExcelWorkSheet(activeSheet);
	}

	Sheet getFirstActiveSheetExcelFile() throws IOException {
		InputStream inputStream = new FileInputStream(source);
		Workbook workbook = new HSSFWorkbook((inputStream));
		int workbookActiveSheetIndex = workbook.getActiveSheetIndex();
		return workbook.getSheetAt(workbookActiveSheetIndex);
	}

	private boolean worksheetHasData() {
		return excelWorkSheet.getPhysicalNumberOfRows() >= EXCEL_SHEET_ROWCOUNT_INCLUDING_HEADER;
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

	public DataCell createDataCellFromCellHeaderAndCellValue(String cellHeader, Cell cell) {
		return new DataCell(cellHeader, cell.getStringCellValue());
	}

	public DataRow createDataRowFromHeaderRowAndExcelRow(Row headerRow, Row dataRow) {
		return new DataRow();
	}
}
