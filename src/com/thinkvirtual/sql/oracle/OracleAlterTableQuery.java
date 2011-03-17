package com.thinkvirtual.sql.oracle;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import com.crossdb.sql.*;

public class OracleAlterTableQuery extends DefaultAlterTableQuery {

	
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
		for(int i = 0; i < drops.size(); i++){
			//Column col = (Column)(columns.get(i));
			ret += "DROP " + drops.get(i) + ",";
		}
		ret = ret.substring(0, ret.length() -1);
		return ret;
	}


	
}
