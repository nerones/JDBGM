package com.spaceprogram.sql.mysql;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultInsertQuery;

/**
 * Implementación especifica de {@link DefaultInsertQuery} para el DBMS MySQL.
 * 
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class MySQLInsertQuery extends DefaultInsertQuery{


	MySQLInsertQuery() {
		super(new MySQLFormatter());
	}

	public String toString(){
		String insertString = "INSERT INTO " + table ;
		if (isFromDefault()){
			//Si se esta usando mysql con modo strict esta sentencia producirá un error
			insertString += " () VALUES ()";
			return insertString;
		}
		insertString += " (";
		//aprovecho el ciclo para armar la lista de valores a insertar
		String query2b = ") VALUES (";
		
		for (Column col : columns){
			insertString += col.getName() + ", ";
			query2b += getFormatter().valueAsString(col) + ", ";
		}
		insertString = insertString.substring(0,insertString.length() - 2);
		query2b = query2b.substring(0,query2b.length() - 2);
		query2b += ")";
		
		if (isFromSelect()){
			insertString += ") " + getSelectStmt().toString();
			//System.out.println("select");
			return insertString;
		} else {
			//System.out.println("values");
			return insertString + query2b;
		}
		
	}

}
