package com.convertify.data.reader;

import com.convertify.data.Dataset;

import java.io.IOException;

public interface DatasetReader {

	Dataset read() throws IOException;

}
