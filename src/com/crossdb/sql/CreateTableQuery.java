package com.crossdb.sql;

/**
 * Description: There are some oddities with this class that are here to support
 * all databases. The main one is that you can specify a sequence table for dbs
 * like Oracle or SAPDB that need a sequence table to insert into identity
 * columns.
 * <p>
 * If none are specified, the implementation of this class should create the
 * sequence table called <table_name>_seq.
 * <p>
 * Now if the user specifies a sequence column, he should also use that same
 * sequence name in any insert query no matter what db he is using. dbs like
 * mysql or sql server ignore the sequence names.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * </p>
 * 
 * Es interfaz sirve de base para representar una sentencia CREATE TABLE. La interfaz
 * en si solo representa los atributos de la tabla (nombre básicamente), los tipos de datos, nombres de columnas
 * y restricciones sobre ellas se declaran en la clase {@link Column}. Las opciones para
 * {@link CreateTableQuery} estan limitadas debido a que debe ser soportada por varios
 * motores.
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.2
 */
public interface CreateTableQuery extends UpdateStatement{
	
	/**
	 * Establece el nombre de la Tabla que se esta por crear
	 * @param name - el nombre de la tabla
	 */
	void setName(String name);
	
	/**
	 * Establece la tabla a crear como TEMPORARY, opción que es soportada por
	 * MySQL, SQLite y PostgreSQL
	 * @param istemporary - si la tabla a de ser TEMPORARY o no.
	 */
	void setTemporary(boolean istemporary);

	/*
	 * No se agrega, al menos por ahora la opción CREATE TABLE table_name AS SELECT
	 * por algunas incompatibilidades, como por ejemplo en SQLite de agregarse la opción
	 * AS SELECT no se pueden agregar mas columnas aparte de las devueltas por select
	 * en cambio en MySQL y PostgreSQL si se puede. quizás haya algún mode de sortear
	 * esto. 
	 */
	/**
	 * Se puede crear una tabla nueva a partir de una sentencia {@code SELECT}
	 * que se le pasa como parámetro. Pero si es especificada esta fuente para
	 * la sentencia {@code CREATE TABLE} no es posible poner definiciones de
	 * columnas.
	 * @param select la sentencia select fuente para crear la nueva tabla.
	 */
	void setSelectStatementSource( SelectQuery select);
	
	/**
	 * Añade las columnas que contendrá la tabla a ser creada, las características
	 * de las columnas se establecen en la clase {@link Column}-
	 * @param c La columna a ser añadida en la tabla.
	 */
	void addColumn(Column c);
	
	
	
	boolean isCompositePK();
	
	String getCompositePK();

	/*
	 * This is used for dbs like Oracle and SAP db, but needs to be implemented
	 * for all dbs for cross db compatability.
	 * 
	 * Not necessary to use it though, because implementation should have
	 * default sequence names.
	 */
	// void setSequence(String sequence_table_name);
	
	String columnToString(Column column);

}
