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
	 * Agrega una columna a la tabla, de haberse llamado antes a la sentencia {@link #newTableName(String)}
	 * esta dejara de tener efecto y la sentencia solo agregara columnas a la tabla.
	 */
	void addColumn(Column c);
	
	/**
	 *Establece un nuevo nombre para la tabla, e implica que la sentencia renombrara
	 *la tabla que se declaro con {@link #setTable(String)} y además de haberse
	 *llamado antes a {@link #addColumn(Column)} esta dejara de tener efecto y la sentencia
	 *resultante solo cambiara el nombre de la tabla.
	 * 
	 * @param table El nuevo nombre de la tabla
	 */
	void newTableName(String table);
	
	/**
	 *Toma una columna y genera la definición necesaria para la creación de la columna dado que existen
	 *ciertas restricciones sobre la definición de columnas en comparación a las definiciones en CREATE TABLE.  
	 * @param column una columna a ser agregada a la tabla
	 * @return la definición de la creación de la columna.
	 */
	String columnToString(Column column);
	
//	/**
//	 * Adds a column to be dropped.
//	 */
//	void dropColumn(String c);
}
