package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataRowTest {

	private DataRow row = new DataRow();

	@Test public void shouldBeAbleToAddCellsHorizontallyWithDataToTheRow() throws InvalidSpreadsheetCellException {
		addCells();
		assertTrue(row.cellCount() > 0);
	}

	@Test public void shouldBeAbleToRemoveCellsFromTheRow() throws InvalidSpreadsheetCellException {
		addCells();
		removeCells(1, 0);
		assertTrue(row.cellCount() == 0);
	}

	@Test public void shouldBeAbleToGetCellAtParticularIndex() throws InvalidSpreadsheetCellException {
		addCells();
		assertNotNull(row.getCellAt(0));
	}

	@Test(expected = IndexOutOfBoundsException.class) public void shouldThrowExceptionWhenTryingToGetCellAtIndexBeyondTheSizeOfRow()
			throws InvalidSpreadsheetCellException {
		addCells();
		row.getCellAt(10);
	}

	@Test public void thatCellsShouldMoveToTheLeftWhenCellAtAnIndexIsRemoved() throws InvalidSpreadsheetCellException {
		addCells();
		DataCell CELL_AT_INDEX_2_AT_INSERT = new DataCell("HeaderText3", "DataText3");
		row.add(CELL_AT_INDEX_2_AT_INSERT);
		row.add(new DataCell("HeaderText4", "DataText4"));
		removeCells(1);
		assertTrue(CELL_AT_INDEX_2_AT_INSERT.getData().equals(row.getCellAt(1).getData()));
	}

	private void removeCells(int ... cellAt) {
		for (int cellAtIndex : cellAt) {
			row.deleteCellAt(cellAtIndex);
		}
	}

	private void addCells() throws InvalidSpreadsheetCellException {
		row.add(new DataCell("HeaderText", "DataText"));
		row.add(new DataCell("HeaderText2", "DataText2"));
	}

	@Test public void thatSpreadSheetRowIsNotEmptyWhenItHasOneOrMoreCells() throws InvalidSpreadsheetCellException {
		addCells();
		assertFalse(row.empty());
	}
}