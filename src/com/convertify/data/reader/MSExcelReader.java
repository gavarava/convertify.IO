package com.convertify.data.reader;

import com.convertify.data.MSExcelDataSet;
import com.convertify.data.SpreadSheetRow;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class MSExcelReader implements DatasetReader {

	private static int HEADER_ROW = 0;

	private File source;

	public MSExcelReader(File sourceFile) throws FileNotFoundException {
		if (fileIsInvalid(sourceFile)) {
			throw new FileNotFoundException();
		}
		this.source = sourceFile;
	}

	private boolean fileIsInvalid(File sourceFile) {
		return !sourceFile.exists();
	}

	@Override public MSExcelDataSet<SpreadSheetRow> read() {
		MSExcelDataSet<SpreadSheetRow> resultset = new MSExcelDataSet<>();
		try {
			HSSFSheet activeSheet = createHSSFSheetFromFirstActiveWorkSheet();
			if (hasRows(activeSheet)) {
				HSSFRow headerRow = activeSheet.getRow(HEADER_ROW);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultset;
	}

	private HSSFSheet createHSSFSheetFromFirstActiveWorkSheet() throws IOException {
		InputStream inputStream = new FileInputStream(source);
		HSSFWorkbook workbook = new HSSFWorkbook((inputStream));
		return workbook.getSheetAt(workbook.getActiveSheetIndex());
	}

	private boolean hasRows(HSSFSheet sheet) {
		return sheet.getPhysicalNumberOfRows() >= 1;
	}
}
