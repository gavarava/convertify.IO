package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataCellTest {

	private DataCell dataCell = DataCell.EMPTY_CELL;

	@Test public void thatEmptyEvaluatesToTrueIfThereIsNoCellValue() throws Exception {
		assertTrue(dataCell.empty());
	}

	@Test public void thatEmptyEvaluatesToFalseWhenThereIsADataValueAndHeader()
			throws Exception, InvalidSpreadsheetCellException {
		createDataCellForTest("Header", "Value");
		assertFalse(dataCell.empty());
	}

	@Test public void thatColumnDataTypeHeaderCanExistWithBlankData() throws InvalidSpreadsheetCellException {
		createDataCellForTest("Header", "");
		assertTrue("Header".equalsIgnoreCase(dataCell.getDataTypeHeader()));
	}

	@Test(expected = InvalidSpreadsheetCellException.class)
	public void thatDataCannotExistWithoutColumnDataTypeHeaderInformation() throws InvalidSpreadsheetCellException {
		createDataCellForTest("", "Value");
	}

	private void createDataCellForTest(String columnDataTypeHeader, String value)
			throws InvalidSpreadsheetCellException {
		dataCell = new DataCell(columnDataTypeHeader, value);
	}

}