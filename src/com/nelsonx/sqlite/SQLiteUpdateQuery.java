package com.nelsonx.sqlite;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultUpdateQuery;

public class SQLiteUpdateQuery extends DefaultUpdateQuery {

	public SQLiteUpdateQuery() {

		super(new SQLiteFormatter());
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
