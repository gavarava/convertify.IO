package com.convertify.data;

public class SpreadsheetCell {

	private String columnHeader;
	private String columnData;

	public SpreadsheetCell(String columnHeader, String column) {
		this.columnHeader = columnHeader;
		this.columnData = column;
	}

	public String getColumnHeader() {
		return columnHeader;
	}

	public String getColumnData() {
		return columnData;
	}

}
