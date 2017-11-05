package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class MSExcelDataSetTest {

	MSExcelDataSet msExcelDataSet = new MSExcelDataSet();

	@Test public void shouldBeAbleToDetectUsingEmptyMethodIfResultSetEmpty() throws Exception {
		assertTrue(msExcelDataSet.empty());
	}

	@Test public void shouldReturnCorrectCountOfRowsInTheDataset() {
		// Given an empty data set
		assertTrue(msExcelDataSet.getRowCount() == 0);
	}

	@Test public void thatListOfSpreadsheetRowsIsNotEmptyWhenSheetContainsData() throws Exception {
		// Given a Dataset with spreadsheetrows
		SpreadSheetRow row1 = new SpreadSheetRow();
	}
}