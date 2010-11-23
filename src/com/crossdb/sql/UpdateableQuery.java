/**
 * For queries that modify the contents of a table such as: InsertQuery and UpdateQuery
 *
 * In this way, you can use the two almost interchangeably.
 *
 * setTable(), addColumn
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Aug 20, 2002
 * Time: 1:01:05 PM
 * 
 */
package com.crossdb.sql;

import java.util.List;
import java.math.BigDecimal;

public interface UpdateableQuery extends UpdateStatement{

	void addColumn(String column, String value);

	// void addColumn(String column, Integer value);
	void addColumn(String column, int value);

	void addColumn(String column, float value);

	void addColumn(String column, double value);

	void addColumn(String column, java.util.Date value);

	void addColumn(String column, boolean value);

	void addColumn(String column, BigDecimal value);
	
	public void addColumn(ColumnValue column);

	/**
	 * Add the list of columns to the current list in the query.
	 */
	void appendColumns(List columns);

	/**
	 * 
	 * @return List of ColumnValues
	 */
	List getColumns();

	/*
	 * void addColumn(int column, String value); //void addColumn(String column,
	 * Integer value); void addColumn(int column, int value); void addColumn(int
	 * column, float value); void addColumn(int column, double value); void
	 * addColumn(int column, java.util.Date value); void addColumn(int column,
	 * boolean value);
	 */
	/**
	 * This one is used for passing in a string value and not altering it on the
	 * insert. ie: Not putting single quotes around it or escaping anything.
	 * Just goes in exactly as it is in value.
	 */
	void addColumnNoAlter(String column, String value);
}
