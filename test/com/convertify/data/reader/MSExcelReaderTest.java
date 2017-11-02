package com.convertify.data.reader;

import com.convertify.data.MSExcelDataSet;
import com.convertify.data.SpreadSheetRow;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import static org.junit.Assert.*;

public class MSExcelReaderTest {

	private MSExcelReader msExcelReader;
	private File          file;

	@Before public void setup() throws FileNotFoundException {
		setupTestExcelSheet();
		msExcelReader = new MSExcelReader(file);
	}

	private void setupTestExcelSheet() {
		URL resource = this.getClass().getResource("/TestSpreadSheet.xls");
		file = new File(resource.getPath());
	}

	@Test(expected = FileNotFoundException.class)
	public void shouldThrowExceptionWhenInvalidFileGivenWhileMSExcelReadersInitialization()
			throws FileNotFoundException {
		file = new File("/random/invalid/path");
		msExcelReader = new MSExcelReader(file);
	}

	@Test public void shouldGetARowWhenExcelSheetContainsTheFirstRow() throws Exception {
		MSExcelDataSet<SpreadSheetRow> result = msExcelReader.read();

	}

	@Test public void shouldReturnDataSetWhenItReturnsAValidDataSet() throws Exception {
		MSExcelDataSet<SpreadSheetRow> result = msExcelReader.read();
		assertNotNull("Excel Sheet was read as null data set", result);
		assertFalse(result.getResultSet().isEmpty());
	}

}