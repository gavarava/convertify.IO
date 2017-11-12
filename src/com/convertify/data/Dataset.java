package com.convertify.data;

import java.util.Collection;

public interface Dataset {

	Collection<Object> resultSet();

	boolean empty();

}