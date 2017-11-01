package com.convertify.data.reader;

import com.convertify.data.DataRow;
import com.convertify.data.Dataset;

public interface DatasetReader {

	Dataset<? extends DataRow> read();

}
