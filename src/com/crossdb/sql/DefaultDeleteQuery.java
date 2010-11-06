/**
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 8:24:42 PM
 * 
 */
package com.crossdb.sql;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public abstract class DefaultDeleteQuery implements DeleteQuery {

	protected String table;

	protected WhereClause wclause = new WhereClause();

	public DefaultDeleteQuery() {

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

	public void addWhereString(String pre, int comparison, String pred) {
		wclause.addCondition(new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
	}

	public void addWhereString(String and_or, String pre, int comparison, String pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
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

		return stmt.executeUpdate(toString());
	}

	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;


	}
}
