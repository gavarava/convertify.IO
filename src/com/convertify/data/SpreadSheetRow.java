package com.convertify.data;

import java.util.ArrayList;
import java.util.List;

public class SpreadSheetRow {

	private List<SpreadsheetCell> spreadsheetCells;
	private boolean header = false;

	public SpreadSheetRow() {
		this.spreadsheetCells = new ArrayList<>();
	}

	public SpreadSheetRow(List<SpreadsheetCell> spreadsheetCells) {
		this.spreadsheetCells = spreadsheetCells;
	}

	public boolean isHeader() {
		return false;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}
}
