package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataCellTest {

	private DataCell dataCell = DataCell.EMPTY_CELL;

	@Test public void thatEmptyEvaluatesToTrueIfThereIsNoCellValue() throws Exception {
		assertTrue(dataCell.empty());
	}

	@Test public void thatEmptyEvaluatesToFalseWhenThereIsADataValueAndHeader() throws Exception {
		createDataCellForTest("Header", "Value");
		assertFalse(dataCell.empty());
	}

	@Test public void thatColumnDataTypeHeaderCanExistWithBlankData() {
		createDataCellForTest("Header", "");
		assertTrue("Header".equalsIgnoreCase(dataCell.getDataTypeHeader()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void thatDataCannotExistWithoutColumnDataTypeHeaderInformation() {
		createDataCellForTest("", "Value");
	}

	private void createDataCellForTest(String columnDataTypeHeader, String value) {
		dataCell = new DataCell(columnDataTypeHeader, value);
	}

}