package com.convertify.data;

public class SpreadsheetCell {

	public static final SpreadsheetCell EMPTY_SPREADSHEET_CELL = new SpreadsheetCell();
	private static final String BLANK_STRING = "";

	private String columnDataTypeHeader;
	private String data;

	public SpreadsheetCell(String columnDataTypeHeader, String data) {
		if (columnDataTypeHeader == null || columnDataTypeHeader.isEmpty()) {
			throw new IllegalArgumentException("Data cannot exist without specifying its form in the columnDataTypeHeader property");
		}
		this.columnDataTypeHeader = columnDataTypeHeader;
		this.data = data;
	}

	private SpreadsheetCell() {
	}

	public String getColumnDataTypeHeader() {
		return columnDataTypeHeader;
	}

	public String getData() {
		return data;
	}

	public boolean empty() {
		return data == null || BLANK_STRING.equals(data);
	}

}
