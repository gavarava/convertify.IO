package com.convertify.data;

import java.util.ArrayList;
import java.util.List;

public class MSExcelDataSet<T> implements Dataset {

	private List<T> rows = new ArrayList<>();
	private boolean empty = rows.isEmpty();

	@Override public List<T> resultSet() {
		return rows;
	}

	@Override public boolean empty() {
		return empty;
	}

	public int getRowCount() {
		return rows.size();
	}

	public void add(T row) {
		rows.add(row);
	}
}
