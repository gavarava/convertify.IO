package com.convertify.data.reader;

import com.convertify.data.DataCell;
import com.convertify.data.DataRow;
import com.convertify.data.InvalidSpreadsheetCell;
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
		fixMockedSheetForTest(2);
		msExcelReader.read();
		Mockito.verify(msExcelReader, times(1)).getFirstActiveSheetExcelFile();
	}

	@Test
	public void shouldSetupExcelSheetHeaderWhenFileHasMoreThanOneRow() throws IOException {
		setupExcelFileForTest();
		fixMockedSheetForTest(2);
		msExcelReader.read();
		Mockito.verify(msExcelReader, times(1)).setExcelWorkSheet(any(HSSFSheet.class));
	}

	@Test( expected = UnsupportedOperationException.class )
	public void thatThrowsUnsupportedOperationExceptionWhenYouHaveOnlyOneRow() throws IOException {
		setupExcelFileForTest();
		fixMockedSheetForTest(1);
		msExcelReader.read();
	}

	@Test
	public void shouldCreateADataCellFromOneExcelSheetCellAndHeader()
			throws IOException, InvalidSpreadsheetCell {
		setupExcelFileForTest();
		fixMockedSheetForTest(2);
		String header = "TestHeader";
		Cell dummyCell = mock(Cell.class);
		when(dummyCell.getStringCellValue()).thenReturn("TestData");
		DataCell dataCell = msExcelReader.createDataCellFromCellHeaderAndCellValue(header, dummyCell);
		assertNotNull(dataCell);
		assertTrue("TestHeader".equalsIgnoreCase(dataCell.getDataTypeHeader()));
		assertTrue("TestData".equalsIgnoreCase(dataCell.getData()));
	}

	@Test
	public void shouldCreateADatarowWhenAHeaderAndARowIsPresentInExcelSheet() throws FileNotFoundException {
		Row headerRow = createMockSingleHSSFRowWithSingle("TestHeader");
		Row excelDataRow = createMockSingleHSSFRowWithSingle("TestStringValue");
		msExcelReader = new MSExcelReader(file);
		DataRow dataRow = msExcelReader.createDataRowFromHeaderRowAndExcelRow(headerRow, excelDataRow);
		assertNotNull(dataRow);
		assertFalse(dataRow.empty());
		assertNotNull(dataRow.getCellAt(0));
	}

	@Test
	public void shouldCollectDataRowsInfoDatasetWhenHeaderAndDatarowAvailable() throws Exception {
		msExcelReader = new MSExcelReader(file);
		msExcelReader.collectMetaData();
		MSExcelDataSet<DataRow> result = msExcelReader.collectDatarowsIntoDataSet();
		assertNotNull("Excel Sheet was read as null data set", result);
		assertFalse(result.resultSet().isEmpty());
	}

	private Row createMockSingleHSSFRowWithSingle(String stringCellValue) {
		Row row = mock(Row.class);
		when(row.getPhysicalNumberOfCells()).thenReturn(1);
		Cell mockCellWithValue = getMockCellWithValue(stringCellValue);
		when(row.getCell(0)).thenReturn(mockCellWithValue);
		return row;
	}

	private Cell getMockCellWithValue(String stringCellValue) {
		Cell cell = mock(Cell.class);
		when(cell.getStringCellValue()).thenReturn(stringCellValue);
		when(cell.getColumnIndex()).thenReturn(0);
		return cell;
	}

	private void setupTestExcelSheet() {
		URL resource = this.getClass().getResource("/TestSpreadSheet.xls");
		file = new File(resource.getPath());
	}

	private void fixMockedSheetForTest(int noOfRows) throws IOException {
		doReturn(sheet).when(msExcelReader).getFirstActiveSheetExcelFile();
		when(sheet.getPhysicalNumberOfRows()).thenReturn(noOfRows);
		doReturn(new MSExcelDataSet<>()).when(msExcelReader).collectDatarowsIntoDataSet();
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