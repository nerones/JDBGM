package com.thinkvirtual.sql.sqlserver;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import com.crossdb.sql.*;

public class SQLServerUpdateQuery  extends DefaultUpdateQuery implements com.crossdb.sql.UpdateQuery {

	String query1;

	String table;
	List columns; // = new ArrayList(); // SELECT columns
	List values;
	//List tables;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	WhereClause wclause = new WhereClause();
	//List order_by; // ORDER BY order_by
	//int limit[]; // 2 max that will be offset, count

	int no_alter_values = 0; // counts the number of no alter values

	public SQLServerUpdateQuery() {
		query1 = "";
		//tables = new ArrayList();
		columns = new ArrayList();
		values = new ArrayList();
		//where_clauses = new ArrayList();
		//order_by = new ArrayList();
	}


	public void setTable(String table) {
		this.table = table;
	}

	/*public void addColumn(String column, String value, boolean auto_inc){

	 columns.add(column);
	 if(auto_inc){
	 values.add(null);
	 }
	 }*/
	public void addColumn(String column, String value) {

		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}

	public void addColumn(String column, int value) {
		Column c = new Column(column);
		columns.add(c);
		values.add(new Integer(value));
	}
	/*		public void addColumn(String column, Integer value){
	 columns.add(column);
	 values.add(value);
	 }*/

	public void addColumn(String column, boolean value) {
		Column c = new Column(column);
		columns.add(c);
		values.add(new Boolean(value));
	}

	public void addColumn(String column, java.util.Date value) {
		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}

	public void addColumn(String column, float value) {
		Column c = new Column(column);
		columns.add(c);
		values.add(new Float(value));
	}

	public void addColumn(String column, double value) {
		Column c = new Column(column);
		columns.add(c);
		values.add(new Double(value));
	}

	public void addColumnNoAlter(String column, String value) {
		columns.add(0, new Column(column));
		values.add(0, value);
		no_alter_values++;

	}


	public void addWhereCondition(String pre, int comparison, String pred) {
		//Where where = new Where(pre, comparison, pred);
		/*String where = pre + " " + SQLServerSelectQuery.getOperatorString(comparison) + " " + pred;
		where_clauses.add(where);
		 */
		wclause.addCondition(new WhereCondition(pre, comparison, pred));
	}

	public void addWhereCondition(String pre, int comparison, int pred) {
		//Where where = new Where(pre, comparison, pred);
		/*String where = pre + " " + SQLServerSelectQuery.getOperatorString(comparison) + " " + pred;
		 where_clauses.add(where);*/
		wclause.addCondition(new WhereCondition(pre, comparison, "" + pred));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, String pred) {
//		where_clauses.add(and_or);
//		String where = pre + " " + SQLServerSelectQuery.getOperatorString(comparison) + " " + pred;
//		where_clauses.add(where);
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, pred));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, int pred) {
//		where_clauses.add(and_or);
//		String where = pre + " " + SQLServerSelectQuery.getOperatorString(comparison) + " " + pred;
//		where_clauses.add(where);
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "" + pred));
	}

	public void addWhereCondition(String pre, int comparison, java.util.Date pred) {
		//Where where = new Where(pre, comparison, pred);
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, java.util.Date pred) {
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public void addWhereString(String pre, int comparison, String pred) {
		wclause.addCondition(new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
	}

	public void addWhereString(String and_or, String pre, int comparison, String pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
	}

	public void addWhereCondition(WhereCondition cond) {
		wclause.addCondition(cond);
	}

	public void addWhereClause(WhereClause wc) {
		wclause.addClause(wc);

	}

	public void addWhereNotNull(String col) {
		wclause.addCondition(new WhereCondition(col, WhereCondition.NOT_NULL, ""));
	}

	public void addWhereNotNull(String and_or, String col) {
		wclause.addCondition(and_or, new WhereCondition(col, WhereCondition.NOT_NULL, ""));
	}

	public void addWhereIsNull(String col) {
		wclause.addCondition(new WhereCondition(col, WhereCondition.IS_NULL, ""));
	}

	public void addWhereIsNull(String and_or, String col) {
		wclause.addCondition(and_or, new WhereCondition(col, WhereCondition.IS_NULL, ""));
	}

	public String toString() {

		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		String query2 = "UPDATE " + table + " SET ";
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		for (int m = 0; m < columns.size(); m++) {

			Column col = (Column) (columns.get(m));
			Object val = values.get(m);
			String in_val;
			if (val == null) {
				in_val = null;
			}
			else if (val instanceof String) { // then sql escape and put quotes around it
				if (no_alter_values > m) {
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
		}
		 */
		if (wclause.hasConditions()) {
			query2 += " WHERE ";
			// rifle through tables and return string
			query2 += wclause.toString();
		}

		//query2b = query2b.substring(0,query2b.length() - 1);
		//query2b += " ) ";
		return query2; // + query2b;
	}

	public int execute(Statement stmt) throws SQLException {
		return stmt.executeUpdate(toString());
	}

	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;
	}
}
