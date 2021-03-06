package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultInsertQuery;
import com.crossdb.sql.Formatter;

/**
 * Implementación especifica de {@link DefaultInsertQuery} para el 
 * DBMS PostgreSQL.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class PostgreSQLInsertQuery extends DefaultInsertQuery {

	PostgreSQLInsertQuery(Formatter formatter) {
		super(formatter);
	}

	@Override
	public String toString() {
		String query2 = "INSERT INTO " + table ;
		if (isFromDefault()){
			query2 += " DEFAULT VALUES";
			return query2;
		}
		query2 += " (";
		String query2b = ") VALUES (";
		for(Column col : columns){
			query2 += col.getName() + ", ";
			query2b += getFormatter().valueAsString(col) + ", ";

		}
		query2 = query2.substring(0,query2.length() - 2);
		query2b = query2b.substring(0,query2b.length() - 2);
		query2b += ")";
		
		if (isFromSelect()){
			query2 += ") " + getSelectStmt().toString();
			//System.out.println("select");
			return query2;
		} else {
			//System.out.println("values");
			return query2 + query2b;
		}
		
	}

}
