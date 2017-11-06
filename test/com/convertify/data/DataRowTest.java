package com.convertify.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataRowTest {

	private DataRow row = new DataRow();

	@Test public void shouldBeAbleToAddCellsHorizontallyWithDataToTheRow() {
		addCells();
		assertTrue(row.cellCount() > 0);
	}

	@Test public void shouldBeAbleToRemoveCellsFromTheRow() {
		addCells();
		removeCells(1, 0);
		assertTrue(row.cellCount() == 0);
	}

	@Test public void shouldBeAbleToGetCellAtParticularIndex() {
		addCells();
		assertNotNull(row.getCellAt(0));
	}

	@Test(expected = IndexOutOfBoundsException.class) public void shouldThrowExceptionWhenTryingToGetCellAtIndexBeyondTheSizeOfRow() {
		addCells();
		row.getCellAt(10);
	}

	@Test public void thatCellsShouldMoveToTheLeftWhenCellAtAnIndexIsRemoved() {
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

	private void addCells() {
		row.add(new DataCell("HeaderText", "DataText"));
		row.add(new DataCell("HeaderText2", "DataText2"));
	}

	@Test public void thatSpreadSheetRowIsNotEmptyWhenItHasOneOrMoreCells() {
		addCells();
		assertFalse(row.empty());
	}
}