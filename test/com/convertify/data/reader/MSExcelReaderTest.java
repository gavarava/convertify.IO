package com.convertify.data.reader;

import com.convertify.data.DataCell;
import com.convertify.data.DataRow;
import com.convertify.data.MSExcelDataSet;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MSExcelReaderTest {

	private File          file;
	private MSExcelReader msExcelReader;

	// @formatter:off
	@Mock private Sheet sheet;
	// @formatter:on
	@Before
	public void setup() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		setupTestExcelSheet();
	}

	@Test(expected = FileNotFoundException.class)
	public void shouldThrowExceptionWhenInvalidFileGivenWhileMSExcelReadersInitialization()
			throws FileNotFoundException {
		file = new File("/random/invalid/path");
		msExcelReader = new MSExcelReader(file);
	}

	@Test
	public void shouldGetTheFirstActiveSheetFromExcelFile() throws IOException {
		setupExcelFileForTest();
		fixSheetForTest(2);
		msExcelReader.read();
		Mockito.verify(msExcelReader, times(1)).getFirstActiveSheetExcelFile();
	}

	@Test
	public void shouldSetupExcelSheetHeaderWhenFileHasMoreThanOneRow() throws IOException {
		setupExcelFileForTest();
		fixSheetForTest(2);
		msExcelReader.read();
		Mockito.verify(msExcelReader, times(1)).setExcelWorkSheet(any(HSSFSheet.class));
	}

	@Test( expected = UnsupportedOperationException.class )
	public void thatThrowsUnsupportedOperationExceptionWhenYouHaveOnlyOneRow() throws IOException {
		setupExcelFileForTest();
		fixSheetForTest(1);
		msExcelReader.read();
	}

	@Test
	public void shouldCreateADataCellFromOneExcelSheetCellAndHeader() throws IOException {
		setupExcelFileForTest();
		fixSheetForTest(2);
		String header = "TestHeader";
		Cell dummyCell = mock(Cell.class);
		when(dummyCell.getStringCellValue()).thenReturn("TestData");
		DataCell dataCell = msExcelReader.createDataCellFromCellHeaderAndCellValue(header, dummyCell);
		assertNotNull(dataCell);
		assertTrue("TestHeader".equalsIgnoreCase(dataCell.getDataTypeHeader()));
		assertTrue("TestData".equalsIgnoreCase(dataCell.getData()));
	}

	@Test
	public void shouldCreateADatarowWhenAHeaderAndARowIsPresentInExcelSheet() {
		Row headerRow = mock(Row.class);
		Row excelDataRow = mock(Row.class);
		DataRow dataRow = msExcelReader.createDataRowFromHeaderRowAndExcelRow(headerRow, excelDataRow);
		assertNotNull(dataRow);
		assertFalse(dataRow.empty());
		assertNotNull(dataRow.getCellAt(0));
	}

	@Test
	public void shouldCollectDatarowsInfoDatasetWhenHeaderAndDatarowAvailable() throws Exception {
		// Given a DataRow
		// Get a Test Data Row
		//When
		MSExcelDataSet<DataRow> result = msExcelReader.collectDatarowsIntoDataSet();
		// Then
		assertNotNull("Excel Sheet was read as null data set", result);
		assertFalse(result.resultSet().isEmpty());
	}

	private void setupTestExcelSheet() {
		URL resource = this.getClass().getResource("/TestSpreadSheet.xls");
		file = new File(resource.getPath());
	}

	private void fixSheetForTest(int noOfRows) throws IOException {
		doReturn(sheet).when(msExcelReader).getFirstActiveSheetExcelFile();
		when(sheet.getPhysicalNumberOfRows()).thenReturn(noOfRows);
	}

	private void setupExcelFileForTest() {
		msExcelReader = spy(MSExcelReader.class);
		setSourceForTest();
	}

	private File setSourceForTest() {
		File mockFile = mock(File.class);
		msExcelReader.setSource(mockFile);
		return mockFile;
	}

}