package com.convertify.reader;

import java.io.File;
import java.util.Map;

public interface SpreadsheetToMapConverter {
	
	Map<String, ?> convertExcelSheetToMap(File spreadsheet);

}