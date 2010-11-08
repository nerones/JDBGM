/**
 * default update query that other implementations can extend
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 7:24:05 PM
 * 
 */
package com.crossdb.sql;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public abstract class DefaultUpdateQuery extends DefaultUpdateableQuery implements UpdateQuery {

	protected String table;

	protected WhereClause wclause = new WhereClause();
	//List order_by; // ORDER BY order_by
	//int limit[]; // 2 max that will be offset, count


	public DefaultUpdateQuery() {

		super();
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

	public void addWhereCondition(String and_or, String pre, int comparison, String pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, pred));
	}

	public void addWhereCondition(String and_or, String pre, int comparison, int pred) {
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
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
    public void addWhereClause(IWhereClause wc) {
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

    public void addToColumn(String column, int valueToAdd) {
        addColumnNoAlter(column, column + " + (" + valueToAdd + ")");
    }

    public void addToColumn(String column, double valueToAdd) {
        addColumnNoAlter(column, column + " + (" + valueToAdd + ")");
    }

    public void incrementColumn(String column) {
        addToColumn(column, 1);
    }

    public abstract String toString();
    
    /*
	public int execute(Statement stmt) throws SQLException {
		return stmt.executeUpdate(toString());
	}

	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;
	}
	*/
}
