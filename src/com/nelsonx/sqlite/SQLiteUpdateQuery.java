package com.nelsonx.sqlite;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultUpdateQuery;
import com.crossdb.sql.SQLDateTimeFormat;
import com.crossdb.sql.SQLFormat;

public class SQLiteUpdateQuery extends DefaultUpdateQuery {

	@Override
	public String toString() {
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		String query2 = "UPDATE " + table + " SET ";
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		for (int m = 0; m < columns.size(); m++) {

			Column col = (columns.get(m));
			Object val = col.getColumnValue();
			String in_val;
			if (val == null) {
				in_val = null;
			}
			else if (val instanceof String) { // then sql escape and put quotes around it
				if (col.isNoAlter()) {
					in_val = (String) val;
				}
				else {
					in_val = "'" + SQLFormat.escape((String) val) + "'";
				}
			}
			else if (val instanceof java.util.Date) {
				in_val = "'" + sqldf.format(val) + "'";
			}
			else if (val instanceof Boolean) {
				Boolean b = (Boolean) val;
				if (b.booleanValue()) {
					// true, so 1
					in_val = "1";
				}
				else
					in_val = "0";

			}
			else {
				in_val = val.toString();
			}
			//	String val = (String)();
			query2 += col.getName() + " = " + in_val + ",";


		}
		query2 = query2.substring(0, query2.length() - 1);

		/*query2 += " WHERE ";
		// rifle through tables and return string
		int i;
		for(i = 0; i < where_clauses.size(); i++){
			String where = (String)(where_clauses.get(i));
			query2 += where + "  ";
		}
		if(i > 0){
			query2 = query2.substring(0,query2.length() -1);
		 }*/
		if (wclause.hasConditions()) {
			query2 += " WHERE ";
			// rifle through tables and return string
			query2 += wclause.toString();
		}

		//query2b = query2b.substring(0,query2b.length() - 1);
		//query2b += " ) ";
		return query2; // + query2b;
	}

}
