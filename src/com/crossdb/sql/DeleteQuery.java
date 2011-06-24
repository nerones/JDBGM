package com.crossdb.sql;

/**
 Represents and SQL Delete Statement.
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * erer
 * 
 */


public interface DeleteQuery extends UpdateStatement{

	// public SelectQuery(String table);
	void setTable(String table);

	void addWhereCondition(String x, int comparison, String y);

	// void addWhereCondition(
	void addWhereCondition(String x, int comparison, int y);

	void addWhereCondition(String x, int comparison, java.util.Date y);

	void addWhereCondition(String and_or, String x, int comparison, String y);

	void addWhereCondition(String and_or, String x, int comparison, int y);

	void addWhereCondition(String and_or, String x, int comparison,
			java.util.Date y);

	void addWhereCondition(WhereCondition cond);

	void addWhereClause(WhereClause wc);

	void addWhereString(String x, int comparison, String y);

	void addWhereString(String and_or, String x, int comparison, String y);

	void addWhereNotNull(String col);

	void addWhereNotNull(String and_or, String col);

	void addWhereIsNull(String col);

	void addWhereIsNull(String and_or, String col);

}
