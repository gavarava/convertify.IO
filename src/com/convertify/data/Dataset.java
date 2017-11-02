package com.convertify.data;

import java.util.Collection;
import java.util.Map;

public interface Dataset {

	Collection<?> getResultSet();

	boolean empty();

}