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

public class OracleInsertQuery extends DefaultInsertQuery {
    private String sequenceSuffix = "_seq";


    /**
	 Uses default sequence as would be made in OracleCreateTableQuery
	 */
	/*public void addAutoIncrementColumn(String column) {
		Column c = new Column(column);
		c.setAutoIncrement(true);
		columns.add(c);
		values.add(null);
	}

	public void addAutoIncrementColumn(String column, String sequence) {
		Column c = new Column(column);
		c.setAutoIncrement(true);
		c.setSequence(sequence);
		columns.add(c);
		values.add(null);
	}*/


	public String toString() {
		String query2 = "INSERT INTO " + table + " ( ";
		String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
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
					in_val = OracleHelper.processString((String) val);
					in_val = "'" + SQLFormat.escape(in_val) + "'";
				}

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
			else if (val instanceof java.util.Date) {
				in_val = " to_date('" + sqldf.format(val) + "','YYYY-MM-DD HH24:MI:SS') ";
			}
			else {
				in_val = val.toString();
			}
			//	String val = (String)();
			query2 += col.getName() + ",";
			query2b += in_val + ",";


		}
		query2 = query2.substring(0, query2.length() - 1);
		query2b = query2b.substring(0, query2b.length() - 1);
		query2b += " ) ";
		return query2 + query2b;
	}


	public int execute(java.sql.Statement stmt) throws SQLException {

		int ret = 0;
		//q = new 	Query(conn);
		String query2 = "INSERT INTO " + table + " ( ";
		String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		for (int m = 0; m < columns.size(); m++) {

			ColumnValue col = (ColumnValue) (columns.get(m));
			Object val = col.getValue();  //values.get(m);
			if (col.isAutoIncrement()) {
				String seq_name = col.getSequence();
				if (seq_name == null) {
					// default to table_name + col_name + '_seq'
					seq_name = table + /*"_"+ col.getName() + */ sequenceSuffix; // same as create table
				}
				String seq_query = "SELECT " + seq_name + ".nextval from DUAL";
				ResultSet rs = stmt.executeQuery(seq_query);
				rs.next();
				int nextval = rs.getInt("nextval"); // return this value if asked for
				ret = nextval;
				rs.close();
				query2 += col.getName() + ",";
				query2b += nextval + ",";
			}
			else {


				String in_val;
				if (val == null) {
					in_val = null;
				}
				else if (val instanceof String) { // then sql escape and put quotes around it
					if (col.isNoAlter()) {
						in_val = (String) val;
					}
					else {
						in_val = OracleHelper.processString((String) val);
						in_val = "'" + SQLFormat.escape(in_val) + "'";
					}
					// fixes oracle issues
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
				query2 += col.getName() + ",";
				query2b += in_val + ",";

			}

		}
		query2 = query2.substring(0, query2.length() - 1);
		query2b = query2b.substring(0, query2b.length() - 1);
		query2b += " ) ";
		//System.out.println(query2 + query2b);
		stmt.executeUpdate(query2 + query2b);
		//return query2 + query2b;
		return ret;
	}

    public void setSequenceSuffix(String sequenceSuffix) {
        this.sequenceSuffix = sequenceSuffix;
    }

}
