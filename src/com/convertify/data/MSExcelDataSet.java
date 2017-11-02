package com.convertify.data;

import java.util.ArrayList;
import java.util.List;

public class MSExcelDataSet<SpreadSheetRow> implements Dataset {

	private List<SpreadSheetRow> rows = new ArrayList<>();
	private boolean empty = rows.isEmpty();

	@Override public List<SpreadSheetRow> getResultSet() {
		return rows;
	}

	@Override public boolean empty() {
		return empty;
	}
}
