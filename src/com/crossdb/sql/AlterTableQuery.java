package com.crossdb.sql;

/**
 * <p>Title: crossdb - AlterTableQuery</p>
 * <p>Description: Represents an SQL Alter Table Statement.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */


public interface AlterTableQuery extends UpdateStatement{
	
	/**
	 * Set the name of table to alter.
	 */
	void setTable(String table);
	
	/**
	 * Adds a column to be added.
	 */
	void addColumn(Column c);
	
	/**
	 *Establece un nuevo nombre para la tabla, e implica que la sentencia renombrara
	 *la tabla que se declaro con {@link #setTable(String)} . 
	 * @param table El nuevo nombre de la tabla
	 */
	void newTableName(String table);
	
	String columnToString(Column column);
	
//	/**
//	 * Adds a column to be dropped.
//	 */
//	void dropColumn(String c);
}
