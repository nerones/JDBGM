package com.crossdb.sql;

/**
 Represents and SQL Delete Statement.

 
/**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

import java.util.List;
import java.sql.*;

public interface DeleteQuery extends ExecuteUpdate {
	
	
	//public SelectQuery(String table);
	void setTable(String table);
		
void addWhereCondition(String x, int comparison, String y);
	//void addWhereCondition(
	void addWhereCondition(String x, int comparison, int y);
	void addWhereCondition(String x, int comparison, java.util.Date y);
	void addWhereCondition(String and_or, String x, int comparison, String y);
	void addWhereCondition(String and_or, String x, int comparison, int y);
	void addWhereCondition(String and_or, String x, int comparison, java.util.Date y);
	void addWhereCondition(WhereCondition cond);

	void addWhereClause(WhereClause wc);

	void addWhereString(String x, int comparison, String y);
	void addWhereString(String and_or, String x, int comparison, String y);

	void addWhereNotNull(String col);
	void addWhereNotNull(String and_or, String col);
	void addWhereIsNull(String col);
	void addWhereIsNull(String and_or, String col);
	
	/**
	Returns the SQL statement.
	 */
	//String toString();
	
	/**
	Returns the number of rows affected
	 */
	//int execute(Connection conn)throws SQLException ;
	/**
	Uses stmt to execute the query.  This is so you can keep reusing the same
	statement.  Be sure to use new statements if you want more than one resultset
	open at the same time.
	 */
	//int execute(java.sql.Statement stmt) throws SQLException ;
	
	
}
