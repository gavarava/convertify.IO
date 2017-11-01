package com.convertify.data;

public class SpreadSheetDatarow extends DataRow {

	private String header;
	private String column;

	public SpreadSheetDatarow(String header, String column) {
		this.header = header;
		this.column = column;
	}

	public String getHeader() {
		return header;
	}

	public String getColumn() {
		return column;
	}
}
