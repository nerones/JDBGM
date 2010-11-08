package com.crossdb.sql;

/**
 * <p>Title: crossdb - AlterTableQuery</p>
 * <p>Description: Represents an SQL Alter Table Statement.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */


public interface AlterTableQuery {
	
	/**
	 * Set the table to alter.
	 */
	void setTable(String table);
	
	/**
	 * Adds a column to be added.
	 */
	void addColumn(Column c);
	
	/**
	 * Adds a column to be dropped.
	 */
	void dropColumn(String c);
	
	/**
	Returns the SQL statement.
	 */
	//String toString();
	
	/**
	Returns the number of rows affected
	 * /
	int execute(Connection conn)throws SQLException ;
	
		/**
	Uses stmt to execute the query.  This is so you can keep reusing the same
	statement.  Be sure to use new statements if you want more than one resultset
	open at the same time.
	 * /
	int execute(java.sql.Statement stmt) throws SQLException;
	*/
}
