package com.crossdb.sql;

/**
 * Esta clase representa una sentencia ALTER pero que es solo capaz de realizar dos acciones, el cambio
 * de nombre de la tabla o bien el agregado de columnas. El renombrado de la tabla presenta las mismas
 * restricciones que se tiene para nombrar las tablas, es decir que el nombre de la tabla a de ser un
 * nombre valido. El agregado de columnas tiene otras restricciones extras, las cuales se pueden
 * ver en la documentación online.
 * 
 * <p>Copyright (c) 2002 Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.5
 */
public interface AlterTableQuery extends UpdateStatement{
	
	/**
	 * Establece el nombre de la tabla a ser modificada.
	 */
	void setTable(String table);
	
	/**
	 * Agrega una columna a la tabla, de haberse llamado antes a la sentencia {@link #newTableName(String)}
	 * esta dejara de tener efecto y la sentencia solo agregara columnas a la tabla.
	 * 
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
	 * Toma una columna y genera la definición necesaria para la creación de la columna dado que existen
	 * ciertas restricciones sobre la definición de columnas en comparación a las definiciones en CREATE TABLE.
	 * Por ello no se revisara si la columna ingresada tiene alguna restricción de columna salvo por la de clave foranea,
	 * y de declararse la columna como clave foranea esta debe tener como valor por defecto a "null".  
	 * @param column una columna a ser agregada a la tabla
	 * @return la definición de la creación de la columna.
	 */
	String columnToString(Column column);

}
