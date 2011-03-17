package com.nelsonx.postgre;

import com.crossdb.sql.ColumnValue;
import com.crossdb.sql.DefaultInsertQuery;
import com.crossdb.sql.SQLDateTimeFormat;
import com.crossdb.sql.SQLFormat;

public class PostgreSQLInsertQuery extends DefaultInsertQuery {

	@Override
	public String toString() {
		String query2 = "INSERT INTO " + table + " ( ";
		String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		for(int m = 0; m < columns.size(); m++){
			
			ColumnValue col = (ColumnValue)(columns.get(m));
			Object val = col.getValue(); //values.get(m);
			String in_val;
			if(val == null){
				in_val = null;
			}
			else if(val instanceof String){ // then sql escape and put quotes around it
				if(col.isNoAlter()){
					in_val =(String)val;
				}
				else{
					in_val = "'" + SQLFormat.escape((String)val) + "'";
				}
			}
			else if(val instanceof java.util.Date){
				//System.out.print("  IN DATE  ");
				//java.util.Date d1 = (java.util.Date)val;
				in_val = "'" + sqldf.format((java.util.Date)val) + "'";
			}
			else if(val instanceof Boolean){
				Boolean b = (Boolean)val;
				if(b.booleanValue()){
					// true, so 1
					in_val = "1";
				}
				else in_val = "0";
				
			}
			else{
				in_val = val.toString();
			}
			//	String val = (String)();
			query2 += col.getName() + ",";
			query2b += in_val + ",";
			
			
			
		}
		query2 = query2.substring(0,query2.length() - 1);
		query2b = query2b.substring(0,query2b.length() - 1);
		query2b += " ) ";
		return query2 + query2b;
	}

}