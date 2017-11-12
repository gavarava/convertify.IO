package com.convertify.data;

import java.util.List;

public interface DataSet {

	List<DataRow> resultSet();

	boolean empty();

}