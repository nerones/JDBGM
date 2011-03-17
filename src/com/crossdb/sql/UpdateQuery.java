package com.crossdb.sql;

/**
 This is an initial beta of a class that will represent a query string


 /**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

import java.util.Date;

public abstract class UpdateQuery extends DefaultUpdateableQuery {

	public abstract void setTable(String table);

	public abstract void addWhereCondition(String x, int comparison, String y);

	public abstract void addWhereCondition(String x, int comparison, int y);

	public abstract void addWhereCondition(String x, int comparison, Date y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, String y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, int y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, Date y);

	public abstract void addWhereString(String x, int comparison, String y);

	public abstract void addWhereString(String and_or, String x, int comparison, String y);

	public abstract void addWhereCondition(WhereCondition cond);

	public abstract void addWhereClause(WhereClause wc);

	public abstract void addWhereNotNull(String col);

	public abstract void addWhereNotNull(String and_or, String col);

	public abstract void addWhereIsNull(String col);

	public abstract void addWhereIsNull(String and_or, String col);

	/**
	 * This one is used for passing in a string value and not altering it on the
	 * insert. ie: Not putting single quotes around it or escaping anything.
	 * Just goes in exactly as it is in value.
	 */
	//public abstract void addColumnNoAlter(String column, String value);
	// TODO revisar si crea algún problema
	/**
	 * This will add a value to a column during an update, for example:
	 * columnName + 2
	 * 
	 * @param column
	 * @param valueToAdd
	 *            an integer value to add, can also be a negative number
	 */
	public abstract void addToColumn(String column, int valueToAdd);

	/**
	 * Same as addToColumn(String, int) version, but with a double
	 * 
	 * @see UpdateQuery#addToColumn(String, int)
	 * @param column
	 * @param valueToAdd
	 */
	public abstract void addToColumn(String column, double valueToAdd);

	/**
	 * convenience method, equivalent to addToColumn(column, 1);
	 * 
	 * @param column
	 */
	public abstract void incrementColumn(String column);

}
