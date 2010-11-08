/**
 *
 * most implementations should just extend this
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 6:34:51 PM
 * 
 */
package com.crossdb.sql;

import com.crossdb.sql.optimization.OptimizationHint;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public abstract class DefaultSelectQuery implements SelectQuery {


	protected List columns; // = new ArrayList(); // SELECT columns
	protected List tables;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	protected List order_by; // ORDER BY order_by
	protected List group_by; // GROUP BY order_by
    //protected String limit;


	protected WhereClause wclause = new WhereClause();
    protected DefaultSelectQuery union;
    private boolean isDistinct;
    protected List optimizationHints = new ArrayList();

    public DefaultSelectQuery(){
        //query1 = "";
        tables = new ArrayList();
        columns = new ArrayList();
        //where_clauses = new ArrayList();
        order_by = new ArrayList();
        group_by = new ArrayList();
    }

	/**
	 Should be in a separate file

	 CHANGE IN WhereClause.java!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 That's where it's being done now.
	 */
	public static String getOperatorString(int operator){
		String ret = null;
		switch(operator){
			case WhereCondition.EQUAL_TO:
				ret = "=";
				break;
			case WhereCondition.NOT_EQUAL_TO:
				ret = "<>";
				break;
			case WhereCondition.LESS_THAN:
				ret = "<";
				break;
			case WhereCondition.LESS_THAN_OR_EQUAL_TO:
				ret = "<=";
				break;
			case WhereCondition.GREATER_THAN:
				ret = ">";
				break;
			case WhereCondition.GREATER_THAN_OR_EQUAL_TO:
				ret = ">=";
				break;
			case WhereCondition.LIKE:
				ret = "LIKE";
				break;

		}
		return ret;
	}



	public void addTable(String table){

		tables.add(table);
	}

	public void addTable(int join_type, String table){

		tables.add(new Join(join_type, table));
	}

	public void addTable(int join_type, String table, WhereCondition cond){
		Join j = new Join(join_type, table);
		j.addWhereCondition(cond);
		tables.add(j);


	}
	public void addTable(Join join){

		tables.add(join);
	}
	public void addColumn(String column){

		columns.add(column);
	}

    public void addColumn(String table, String column) {
        columns.add(table + "." + column);
    }

    public void addFunctionColumn(String function, String column) {
        columns.add(function + "(" + column + ") ");
    }

    public void addFunctionColumn(String function, String table, String column) {
        columns.add(function + "(" + table + "." + column + ") ");
    }

    public void sumColumn(String column) {
        addFunctionColumn("SUM", column);

    }

    public void sumColumn(String table, String column) {
        addFunctionColumn("SUM", table, column);
    }

    public void countColumn(String column) {
        addFunctionColumn("COUNT", column);
    }

    public void countColumn(String table, String column) {
        addFunctionColumn("COUNT", table, column);

    }

    public void averageColumn(String column) {
        addFunctionColumn("AVG", column);

    }

    public void averageColumn(String table, String column) {
        addFunctionColumn("AVG", table, column);

    }

    public void minColumn(String column) {
        addFunctionColumn("MIN", column);
    }

    public void minColumn(String table, String column) {
        addFunctionColumn("MIN", table, column);

    }

    public void maxColumn(String column) {
        addFunctionColumn("MAX", column);

    }

    public void maxColumn(String table, String column) {
        addFunctionColumn("MAX", table, column);

    }

    /**
	 The following are convenience functions instead of making new whereconditions and adding them, you just
	 use these methods.
	 */
	public void addWhereCondition(String pre, int comparison, String pred){
		//Where where = new Where(pre, comparison, pred);
		wclause.addCondition(new WhereCondition(pre, comparison, pred));
	}
    public void addWhereCondition(String pre, int comparison, int pred){
		//Where where = new Where(pre, comparison, pred);
		wclause.addCondition(new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}
	public void addWhereCondition(String pre, int comparison, long pred){
		//Where where = new Where(pre, comparison, pred);
		wclause.addCondition(new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}
	public void addWhereCondition(String pre, int comparison, java.util.Date pred){
		//Where where = new Where(pre, comparison, pred);
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(new WhereCondition(pre, comparison, "'" + sqldf.format(pred) + "'"));
	}
	public void addWhereCondition(String and_or, String pre, int comparison, String pred){
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, pred));
	}
    public void addWhereCondition(String and_or, String pre, int comparison, int pred){
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}
	public void addWhereCondition(String and_or, String pre, int comparison, long pred){
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "" + pred)); // could handle the ints better
	}
	public void addWhereCondition(String and_or, String pre, int comparison, java.util.Date pred){
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "'" + sqldf.format(pred) + "'"));
	}

    /**
     * same as addWhereString("AND", pre, comparison, pred)
     *
     * @see #addWhereString(String, String, int, String)
     */
	public void addWhereString(String pre, int comparison, String pred){
		wclause.addCondition(new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
	}
    /**
     * Use this for adding a string that you want to be SQL formatted.  All outside strings should use this.
     * user addWhereCondition() for column comparison
     */
	public void addWhereString(String and_or, String pre, int comparison, String pred){
		wclause.addCondition(and_or, new WhereCondition(pre, comparison, "'" + SQLFormat.escape(pred) + "'"));
		}

	public void addWhereCondition(WhereCondition cond){
		wclause.addCondition(cond);
	}

	public void addWhereClause(IWhereClause wc){
		wclause.addClause(wc);

	}
    public void addWhereClause(WhereClause wc){
		wclause.addClause(wc);

	}
	public void addWhereNotNull(String col){
		wclause.addCondition(new WhereCondition(col, WhereCondition.NOT_NULL, ""));
	}
	public void addWhereNotNull(String and_or, String col){
		wclause.addCondition(and_or, new WhereCondition(col, WhereCondition.NOT_NULL, ""));
	}
	public void addWhereIsNull(String col){
		wclause.addCondition(new WhereCondition(col, WhereCondition.IS_NULL, ""));
	}
	public void addWhereIsNull(String and_or, String col){
		wclause.addCondition(and_or, new WhereCondition(col, WhereCondition.IS_NULL, ""));
	}



    public void addOrderBy(String order_by){

		this.order_by.add(order_by);
	}
	public void addGroupBy(String group_by){

		this.group_by.add(group_by);
	}


	public abstract String toString();



    /**
     * will recurse through all SelectQuery's until union is null
     * @return query unioned together
     */
    private String getUnionizedQuery() {
        if(union != null){
            return toString() + " UNION " + union.getUnionizedQuery();
        }
        else{
            return toString();
        }
    }
    
    /*
    public CrossdbResultSet execute(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		CrossdbResultSet ret = execute(stmt);
		// Can't close the statement here, because that also closes the Resultset!  ouch
        //stmt.close();
        // but lets set a flag that we made the statement so we should close it after
        ret.setStatementCreated(stmt);
		return ret;
	}
    public CrossdbResultSet execute(Statement stmt) throws SQLException{

        String q = getUnionizedQuery();
		return new DefaultResultSet(stmt.executeQuery(q));
	}
	*/

    public void union(SelectQuery sq) {
        this.union = (DefaultSelectQuery)sq;
    }

    public void setDistinct(boolean distinct) {
        this.isDistinct = distinct;
    }

    protected int limit[]; // 2 max that will be offset, count

	public void setLimit(int count){
		if(limit == null){
			limit = new int[1];
		}
		limit[0] = count;
	}

	public void setLimit(int offset, int count){
		if(limit == null){
			limit = new int[2];
		}
		limit[0] = offset;
		limit[1] = count;
	}

    public void addOptimizationHint(OptimizationHint optimizationHint) {
        optimizationHints.add(optimizationHint);
    }

    public boolean isDistinct() {
        return isDistinct;
    }
}
