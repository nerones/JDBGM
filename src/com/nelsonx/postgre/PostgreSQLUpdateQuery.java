package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultUpdateQuery;
import com.crossdb.sql.Formatter;

/**
 * Implementación especifica de {@link DefaultUpdateQuery} para el DBMS
 * PostgreSQL.
 * 
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class PostgreSQLUpdateQuery extends DefaultUpdateQuery {
	
	PostgreSQLUpdateQuery(Formatter formatter) {
		super(formatter);
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