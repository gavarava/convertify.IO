package com.spreadsheet.reader;

import java.io.File;
import java.util.Map;

public interface SpreadsheetMapConverter {
	
	public Map<String, ?> buildDataMapFromSpreadsheet(File spreadsheet);

}
