package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpreadsheetCellTest {

	private SpreadsheetCell spreadsheetCell = SpreadsheetCell.EMPTY_SPREADSHEET_CELL;

	@Test public void thatEmptyEvaluatesToTrueIfThereIsNoCellValue() throws Exception {
		assertTrue(spreadsheetCell.empty());
	}

	@Test public void thatEmptyEvaluatesToFalseWhenThereIsADataValueAndHeader() throws Exception {
		createSpreadSheetCellForTest("Header", "Value");
		assertFalse(spreadsheetCell.empty());
	}

	@Test public void thatColumnDataTypeHeaderCanExistWithBlankData() {
		createSpreadSheetCellForTest("Header", "");
		assertTrue("Header".equalsIgnoreCase(spreadsheetCell.getColumnDataTypeHeader()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void thatDataCannotExistWithoutColumnDataTypeHeaderInformation() {
		createSpreadSheetCellForTest("", "Value");
	}

	private void createSpreadSheetCellForTest(String columnDataTypeHeader, String value) {
		spreadsheetCell = new SpreadsheetCell(columnDataTypeHeader, value);
	}

}