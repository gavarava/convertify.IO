package com.convertify.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MSExcelDataSetTest {

	private MSExcelDataSet msExcelDataSet = new MSExcelDataSet();
	private List<DataCell> testDataCellList;

	@Test public void shouldBeAbleToDetectUsingEmptyMethodIfResultSetEmpty() throws Exception {
		assertTrue(msExcelDataSet.empty());
	}

	@Test public void shouldReturnZeroCountOfRowsInTheDatasetWhenEmpty() {
		// Given an empty data set
		assertTrue(msExcelDataSet.getRowCount() == 0);
	}

	@Test public void shouldReturnCorrectCountOfRowsInTheDatasetWhenContainsData() {
		msExcelDataSet.add(new DataRow(quickSetupDataCells()));
		assertTrue(msExcelDataSet.getRowCount() > 0);
	}

	@Test public void thatListOfSpreadsheetRowsIsNotEmptyWhenSheetContainsData() throws Exception {
		// Given a Dataset with spreadsheetrows
		List<DataCell> cellList1 = quickSetupDataCells();
		List<DataCell> cellList2 =
				createListOfCells().
						with(new DataCell("Header1", "Value1")).
						with(new DataCell("Header2", "Value2")).
						andReturnIIt();

		DataRow row1 = new DataRow(cellList1);
		DataRow row2 = new DataRow(cellList2);

		// When we add rows to the dataset
		msExcelDataSet.add(row1);
		msExcelDataSet.add(row2);

		// Then
		assertThat(msExcelDataSet.empty(), is(true));
	}

	private List<DataCell> quickSetupDataCells() {
		return createListOfCells().
				with(new DataCell("HeaderX", "ValueX")).
				with(new DataCell("HeaderN", "ValueM")).
				andReturnIIt();
	}

	private MSExcelDataSetTest createListOfCells() {
		testDataCellList = new ArrayList<>();
		return this;
	}

	private MSExcelDataSetTest with(DataCell dataCell) {
		testDataCellList.add(dataCell);
		return this;
	}

	private List<DataCell> andReturnIIt() {
		return testDataCellList;
	}
}