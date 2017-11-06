package com.convertify.data;

import java.util.ArrayList;
import java.util.List;

public class MSExcelDataSet<DataRow> implements Dataset {

	private List<DataRow> rows = new ArrayList<>();
	private boolean empty = rows.isEmpty();

	@Override public List<DataRow> resultSet() {
		return rows;
	}

	@Override public boolean empty() {
		return empty;
	}

	public int getRowCount() {
		return rows.size();
	}

	public void add(DataRow row) {
		rows.add(row);
	}
}
