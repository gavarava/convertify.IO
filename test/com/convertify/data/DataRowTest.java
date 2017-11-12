package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataRowTest {

	private DataRow row = new DataRow();

	@Test public void shouldBeAbleToAddCellsHorizontallyWithDataToTheRow() throws InvalidSpreadsheetCell {
		addCells();
		assertTrue(row.cellCount() > 0);
	}

	@Test public void shouldBeAbleToRemoveCellsFromTheRow() throws InvalidSpreadsheetCell {
		addCells();
		removeCells(1, 0);
		assertTrue(row.cellCount() == 0);
	}

	@Test public void shouldBeAbleToGetCellAtParticularIndex() throws InvalidSpreadsheetCell {
		addCells();
		assertNotNull(row.getCellAt(0));
	}

	@Test(expected = IndexOutOfBoundsException.class) public void shouldThrowExceptionWhenTryingToGetCellAtIndexBeyondTheSizeOfRow()
			throws InvalidSpreadsheetCell {
		addCells();
		row.getCellAt(10);
	}

	@Test public void thatCellsShouldMoveToTheLeftWhenCellAtAnIndexIsRemoved() throws InvalidSpreadsheetCell {
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

	private void addCells() throws InvalidSpreadsheetCell {
		row.add(new DataCell("HeaderText", "DataText"));
		row.add(new DataCell("HeaderText2", "DataText2"));
	}

	@Test public void thatSpreadSheetRowIsNotEmptyWhenItHasOneOrMoreCells() throws InvalidSpreadsheetCell {
		addCells();
		assertFalse(row.empty());
	}
}