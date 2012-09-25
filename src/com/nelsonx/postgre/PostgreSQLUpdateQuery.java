package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultUpdateQuery;

public class PostgreSQLUpdateQuery extends DefaultUpdateQuery {

	public PostgreSQLUpdateQuery() {

		super(new PostgreSQLFormatter());
	}


	public String toString() {
		String updateAsString = "UPDATE " + table + " SET ";
		for (Column column : columns){
			updateAsString += column.getName() + " = " + getFormatter().valueAsString(column) + ", ";
		}
		updateAsString = updateAsString.substring(0, updateAsString.length() - 2);
		if (wclause != null) {
			updateAsString += wclause.toString();
		}
		return updateAsString;
	}

}
