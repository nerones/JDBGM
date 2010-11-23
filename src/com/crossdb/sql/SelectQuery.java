package com.crossdb.sql;

/**

This represents a Select query.
<p>

 todo: move all the addWhere functions to WhereClause and deprecate them in this so that they can all be used in any query like delete and update.

 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

import java.util.Date;

import com.crossdb.sql.optimization.OptimizationHint;

/*
Should have a predefined WhereClause in which the user can add conditions to it.
 */
public interface SelectQuery extends QueryStatement{

	/**

	 If no columns are specified (ie: addColumn never called), then default returns all columns - *

	@param column - column name to return in column list

	 */
	void addColumn(String column);

    /**
     * equivalent to table.column
     * @param table
     * @param column
     */
    void addColumn(String table, String column);

    
    void addFunctionColumn(String function, String column);
    /**
     * For use with aggregate functions such as:
     * AVG
     * COUNT
     * MIN
     * MAX
     * SUM
     * etc...
     * <p>
     * Warning: Does not guarantee database independence if used, use the aggregage functions such as countColumn
     * to get database independence
     *
     * @param function
     * @param table
     * @param column
     */
    void addFunctionColumn(String function, String table, String column);

    void sumColumn(String column);
    void sumColumn(String table, String column);

    void countColumn(String column);
    void countColumn(String table, String column);

    void averageColumn(String column);
    void averageColumn(String table, String column);

    void minColumn(String column);
    void minColumn(String table, String column);

    void maxColumn(String column);
    void maxColumn(String table, String column);

	/**

	 defaults to inner join

	@param table - table name to add to query

	 */
	void addTable(String table);
	/**
	@param join_type - type of join using the fields in Join.java
	@param table - table name to add to query
	@see Join

	 */
	void addTable(int join_type, String table);

	/**


	Instead of creating a Join then inserting it, since you can only have one condition on a left outer join
	to work with oracle, this is the convenient way to do it.

	@param join_type - type of join using the fields in Join.java
	@param table - table name to add to query
	@see Join
	@see WhereCondition
	 */
	void addTable(int join_type, String table, WhereCondition cond);

	/**

	WARNING: This may get deprecated as it is unecessary considering that you
	 can only have one join condition on an outer join (see requirements doc at www.crossdb.com)

	@param join - a join object that has all the information needed.
	@see Join

	 */
	void addTable(Join join);
	//void removeColumn(String column);
	//Not sure how the function thing will work cause there are too many function variations
	// Maybe have a separate function for each?  like datediff() would actually be a function
	// in a db dependent class implementation called Functions or something so you would need
	// an interface called SQLFunctions or something with all the functions in it.
	//void addColumn(int function, String column); // functions map to
	/**
	 * This one will compare a string with no alterations, so in general
	 * you would use this one to compare 2 columns.
	 * like col1 = col2
	 *
	 * @see #addWhereString
	 */
	void addWhereCondition(String x, int comparison, String y);
	//void addWhereCondition(
    void addWhereCondition(String x, int comparison, int y);
	void addWhereCondition(String x, int comparison, long y);
	void addWhereCondition(String x, int comparison, Date y);
	void addWhereCondition(String and_or, String x, int comparison, String y);
    void addWhereCondition(String and_or, String x, int comparison, int y);
	void addWhereCondition(String and_or, String x, int comparison, long y);
	void addWhereCondition(String and_or, String x, int comparison, Date y);
	/**
	 * This one will be for comparing a column against a string.
	 * So the implementation should escape the string as required. (generally escaping single quotes.
	 *
	 * The end user of this function need not worry about it, just pass in the string to compare to
     *
     *  The implementation should wrap it with single quotes and escape it.
	 *
	 * ex: col1 = 'somestring'
	 */
	void addWhereString(String x, int comparison, String y);
    /**
     * @see #addWhereString
     * @param and_or
     * @param x
     * @param comparison
     * @param y
     */
	void addWhereString(String and_or, String x, int comparison, String y);

	void addWhereCondition(WhereCondition cond);

	void addWhereClause(IWhereClause wc);
   

	void addWhereNotNull(String col);
	void addWhereNotNull(String and_or, String col);
	void addWhereIsNull(String col);
	void addWhereIsNull(String and_or, String col);



	void addOrderBy(String order_by);
	void addGroupBy(String group_by);
	//void setLimit(int count);
	//void setLimit(int offset, int count);

	/**
	Returns the SQL statement.
	 */
	//String toString();

	/* *
	Uses stmt to execute the query.  This is so you can keep reusing the same
	statement.  Be sure to use new statements if you want more than one resultset
	open at the same time.
	 * /
    CrossdbResultSet execute(java.sql.Statement stmt) throws SQLException ;

	/* *
	This one takes a java.sql.Connection and then creates a statement and executes.

     The statement created is closed when the return ResultSet is closed.
     * /
    CrossdbResultSet execute(Connection conn) throws SQLException ;

	/**
	@see SQLFactory#setSchema
	 */
	//public void setSchema(String schema);

    /**
     * will UNION the passed in SelectQuery with the current one.
     * <p>
     * This is implemented like a linked list
     * @param sq
     */
    void union(SelectQuery sq);

    /**
     * set whether this query is distinct or not
     * @param distinct
     */
    void setDistinct(boolean distinct);

    /**
     * Returns a certain number of rows from the result set
     *
     * @param rowCount
     */
    void setLimit(int rowCount);
    /**
     * Example, if you want 125 - 150, you would do setLimit(125, 25);
     *
     * This really doesn't seem to be supported by all dbs, so might drop this in favor of setLimit(rowCount)
     *
     * @param offset into the results
     * @param rowCount num to return
     */
    void setLimit(int offset, int rowCount);

    /**
     * Adds an optimization hint to the query.
     * @see OptimizationHint
     * @param optimizationHint
     */
    void addOptimizationHint(OptimizationHint optimizationHint);

}
