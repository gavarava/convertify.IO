package com.convertify.data;

public class DataCell {

	public static final  DataCell EMPTY_CELL   = new DataCell();
	private static final String   BLANK_STRING = "";

	private String dataTypeHeader;
	private String data;

	public DataCell(String dataTypeHeader, String data) {
		if (dataTypeHeader == null || dataTypeHeader.isEmpty()) {
			throw new IllegalArgumentException("Data cannot exist without specifying its form in the dataTypeHeader property");
		}
		this.dataTypeHeader = dataTypeHeader;
		this.data = data;
	}

	private DataCell() {
	}

	public String getDataTypeHeader() {
		return dataTypeHeader;
	}

	public String getData() {
		return data;
	}

	public boolean empty() {
		return data == null || BLANK_STRING.equals(data);
	}

}
