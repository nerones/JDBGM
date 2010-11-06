/**
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

package com.thinkvirtual.sql.oracle;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import com.crossdb.sql.*;


public class OracleUpdateQuery extends DefaultUpdateQuery implements UpdateQuery {



	/**
	 * These date ones probably have to be changed to work with dates like the insert query?
	 */
	public void addWhereCondition(String pre, int comparison, java.util.Date pred) {
		//Where where = new Where(pre, comparison, pred);
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, java.util.Date pred) {
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public String toString() {
		String query2 = "UPDATE " + table + " SET ";
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		for (int m = 0; m < columns.size(); m++) {

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
				else {
					in_val = OracleHelper.processString((String)val);
					in_val = "'" + SQLFormat.escape(in_val) + "'";
				}

			}
			else if (val instanceof java.util.Date) {
				in_val = " to_date('" + sqldf.format(val) + "','YYYY-MM-DD HH24:MI:SS') ";
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

		//query2b = query2b.substring(0,query2b.length() - 1);
		//query2b += " ) ";
		return query2; // + query2b;
	}


}
