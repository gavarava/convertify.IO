package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataCellTest {

	private DataCell dataCell = DataCell.EMPTY_CELL;

	@Test public void thatEmptyEvaluatesToTrueIfThereIsNoCellValue() throws Exception {
		assertTrue(dataCell.empty());
	}

	@Test public void thatEmptyEvaluatesToFalseWhenThereIsADataValueAndHeader()
			throws Exception, InvalidSpreadsheetCell {
		createDataCellForTest("Header", "Value");
		assertFalse(dataCell.empty());
	}

	@Test public void thatColumnDataTypeHeaderCanExistWithBlankData() throws InvalidSpreadsheetCell {
		createDataCellForTest("Header", "");
		assertTrue("Header".equalsIgnoreCase(dataCell.getDataTypeHeader()));
	}

	@Test(expected = InvalidSpreadsheetCell.class)
	public void thatDataCannotExistWithoutColumnDataTypeHeaderInformation() throws InvalidSpreadsheetCell {
		createDataCellForTest("", "Value");
	}

	private void createDataCellForTest(String columnDataTypeHeader, String value)
			throws InvalidSpreadsheetCell {
		dataCell = new DataCell(columnDataTypeHeader, value);
	}

}