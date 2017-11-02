package com.convertify;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class POILibraryLearningTests {

	private File excelSheet;

	@Before public void setup() {
		URL resource = this.getClass().getResource("/TestSpreadSheet.xls");
		excelSheet = new File(resource.getPath());
	}

	@Test public void thatFileIsReadableUsingJavaFileIO() throws IOException {
		assertTrue("Didnt get the file", excelSheet.exists());
	}

	@Test public void thatAnExcelSheetIsReadableAsAnInputStream() throws IOException {
		HSSFWorkbook workbook = getHSSFWorkbookForTest();
		assertThat("Excel Sheet does not have the first Sheet",workbook.getActiveSheetIndex(), is(equalTo(0)));
	}

	@Test public void thatHSSFSheetContainsMoreThanOneRow() throws IOException {
		HSSFWorkbook workbook = getHSSFWorkbookForTest();
		HSSFSheet firstActiveSheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		assertTrue(firstActiveSheet.getPhysicalNumberOfRows() > 1);
	}

	private HSSFWorkbook getHSSFWorkbookForTest() throws IOException {
		InputStream excelSheetInputStream = new FileInputStream(excelSheet);
		return new HSSFWorkbook((excelSheetInputStream));
	}


}