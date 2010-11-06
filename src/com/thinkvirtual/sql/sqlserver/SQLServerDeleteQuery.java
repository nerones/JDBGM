package com.thinkvirtual.sql.sqlserver;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

import com.crossdb.sql.*;

public class SQLServerDeleteQuery implements com.crossdb.sql.DeleteQuery {

	String query1;

	String table;
	//List columns; // = new ArrayList(); // SELECT columns
	//List values;
	//List tables;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	//List order_by; // ORDER BY order_by
	//int limit[]; // 2 max that will be offset, count

	WhereClause wclause = new WhereClause();

	public SQLServerDeleteQuery() {
		query1 = "";
		//tables = new ArrayList();
		//columns = new ArrayList();
		//values = new ArrayList();
		//where_clauses = new ArrayList();
		//order_by = new ArrayList();
	}


	public void setTable(String table) {
		this.table = table;
	}


	public void addWhereCondition(String pre, int comparison, String pred) {
		//Where where = new Where(pre, comparison, pred);
		wclause.addCondition(new WhereCondition(pre, comparison, pred));
	}

	public void addWhereCondition(String pre, int comparison, int pred) {
		//Where where = new Where(pre, comparison, pred);
		wclause.addCondition(new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}

	public void addWhereCondition(String pre, int comparison, java.util.Date pred) {
		//Where where = new Where(pre, comparison, pred);
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, String pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, pred));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, int pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}

	public void addWhereCondition(String and_or, String pre, int comparison, java.util.Date pred) {
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, " '" + sqldf.format(pred) + "' "));
	}

	public void addWhereCondition(WhereCondition cond) {
		wclause.addCondition(cond);
	}

	public void addWhereClause(WhereClause wc) {
		wclause.addClause(wc);

	}

	public void addWhereString(String pre, int comparison, String pred) {
		wclause.addCondition(new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
	}

	public void addWhereString(String and_or, String pre, int comparison, String pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
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
		String ret = "DELETE FROM " + table;
		//String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		if (wclause.hasConditions()) {
			ret += " WHERE ";
			// rifle through tables and return string
			ret += wclause.toString();
		}
		return ret;
	}

	public int execute(Statement stmt) throws SQLException {
		//q = new 	Query(conn);
		//rs = stmt.executeQuery(querystring);
		return stmt.executeUpdate(toString());
	}

	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		return execute(stmt);
		//return q.executeStatement(toString());
	}
}
