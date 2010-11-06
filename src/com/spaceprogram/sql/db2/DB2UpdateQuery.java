/**
 * UpdateQuery for mysql
 *
 * @author Travis Reeder - travis@spaceprogram.com
 */

package com.spaceprogram.sql.db2;

import com.crossdb.sql.*;

public class DB2UpdateQuery extends DefaultUpdateQuery implements UpdateQuery {

	public DB2UpdateQuery() {
		super();
	}

	public String toString() {

		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		String query2 = "UPDATE " + table + " SET ";
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		for (int m = 0; m < columns.size(); m++) {

			ColumnValue col = (ColumnValue) (columns.get(m));
			Object val = col.getValue();  //values.get(m);
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


		if (wclause.hasConditions()) {
			query2 += " WHERE ";
			// rifle through tables and return string
			query2 += wclause.toString();
		}

		return query2; // + query2b;
	}


}
