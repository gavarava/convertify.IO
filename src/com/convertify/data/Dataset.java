package com.convertify.data;

import java.util.Map;

public interface Dataset<DataRow> {

	Map<String, ? extends DataRow> unmarshalledData();

}