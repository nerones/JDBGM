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

import java.util.List;

import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public interface UpdateQuery extends UpdateableQuery {
	
	void setTable(String table);

	void addWhereCondition(String x, int comparison, String y);
	void addWhereCondition(String x, int comparison, int y);
	void addWhereCondition(String x, int comparison, Date y);
	void addWhereCondition(String and_or, String x, int comparison, String y);
	void addWhereCondition(String and_or, String x, int comparison, int y);
	void addWhereCondition(String and_or, String x, int comparison, Date y);
	void addWhereString(String x, int comparison, String y);
	void addWhereString(String and_or, String x, int comparison, String y);
	void addWhereCondition(WhereCondition cond);

	void addWhereClause(WhereClause wc);
	void addWhereNotNull(String col);
	void addWhereNotNull(String and_or, String col);
	void addWhereIsNull(String col);
	void addWhereIsNull(String and_or, String col);
	
	/**
	This one is used for passing in a string value and not altering it on the insert.
	 ie: Not putting single quotes around it or escaping anything.  Just goes in exactly
	 as it is in value.
	 */
	void addColumnNoAlter(String column, String value);


    /**
     * This will add a value to a column during an update, for example:
     * columnName + 2
     *
     * @param column
     * @param valueToAdd an integer value to add, can also be a negative number
     */
    void addToColumn(String column, int valueToAdd);
    /**
     * Same as addToColumn(String, int) version, but with a double
     * @see UpdateQuery#addToColumn(String, int)
     * @param column
     * @param valueToAdd
     */
    void addToColumn(String column, double valueToAdd);
    /**
     * convenience method, equivalent to addToColumn(column, 1);
     * @param column
     */
    void incrementColumn(String column);



}
