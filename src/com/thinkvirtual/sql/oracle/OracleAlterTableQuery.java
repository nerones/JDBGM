package com.thinkvirtual.sql.oracle;

/** This is an initial beta of a class that will represent a query string */

import com.crossdb.sql.Column;
import com.crossdb.sql.DataTypes;
import com.crossdb.sql.DefaultAlterTableQuery;

public class OracleAlterTableQuery extends DefaultAlterTableQuery {

	
	/**
	 * @param datatype
	 */
	public OracleAlterTableQuery() {
		super(new DataTypes() {
			
			@Override
			public String getAsString(int type, int size) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param datatype
	 */

	public String toString(){
		String ret = "ALTER TABLE " + table + " ";
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		for(int i = 0; i < adds.size(); i++){
			Column col = (Column)(adds.get(i));
			ret += "ADD " + col.getName() + " " + OracleDataTypes.getAsString(col);
			if(col.isNullable() == 0){
				ret += " NOT NULL ";
			}
			ret += ",";
		}
		ret = ret.substring(0, ret.length() -1);
		return ret;
	}


	
}
