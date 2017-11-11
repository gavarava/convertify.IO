package com.convertify.data;

import java.util.ArrayList;
import java.util.List;

public class DataRow {

	private List<DataCell> dataCells = new ArrayList<>();
	private boolean        header    = false;

	public DataRow() {
	}

	public DataRow(List<DataCell> dataCells) {
		this.dataCells = dataCells;
	}

	public boolean isHeader() {
		return false;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public void add(DataCell dataCell) {
		dataCells.add(dataCell);
	}

	public int cellCount() {
		return dataCells.size();
	}

	public boolean empty() {
		return dataCells.isEmpty();
	}

	public void deleteCellAt(int i) {
		dataCells.remove(i);
	}

	public DataCell getCellAt(int index) {
		if (index > cellCount()-1) {
			throw new IndexOutOfBoundsException(index + " is beyond the row size");
		}
		return dataCells.get(index);
	}
}
