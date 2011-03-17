package com.crossdb.sql;


/**
 This represents an SQL Insert query.
 <p>
 Note: You should ALWAYS specify an auto_increment column using addAutoIncrementColumn() if you are using one of course.
 <p>
 Rest is pretty self explanatory.
 <p>
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */
public abstract class InsertQuery extends DefaultUpdateableQuery {

	// public SelectQuery(String table);
	public abstract void setTable(String table);

	// void addColumn(String column, String value, boolean auto_inc);
	public abstract void addAutoIncrementColumn(String column); // uses default sequence

	public abstract void addAutoIncrementColumn(String column, String sequence); // uses named
																	// sequence

	/**
	 * Returns the SQL statement.
	 */
	// String toString();
	/**
	 * Returns the last id inserted if this is specified.
	 */
	public abstract void returnID(boolean b);

	/**
	 * Uses stmt to execute the query. This is so you can keep reusing the same
	 * statement. Be sure to use new statements if you want more than one
	 * resultset open at the same time.
	 */
	// int execute(java.sql.Statement stmt) throws SQLException ;
	/**
	 * This one takes a java.sql.Connection and then creates a statement and
	 * executes.
	 */
	// int execute(Connection conn) throws SQLException ;

	/**
	 * Returns a java.sql.PreparedStatement object based on the query.
	 * 
	 * This should guarantee that the ordering of the "?" passed in is the same
	 * ordering as in the returned statement.
	 */
	//PreparedStatement getPreparedStatement(Connection conn) throws SQLException;

}
