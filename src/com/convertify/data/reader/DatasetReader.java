package com.convertify.data.reader;

import com.convertify.data.DataSet;

import java.io.IOException;

public interface DatasetReader {

	DataSet read() throws IOException;

}
