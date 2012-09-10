package com.crossdb.sql;

import java.util.Vector;

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
 * {@link CreateTableQuery} están limitadas debido a que debe ser soportada por varios
 * motores.
 * <p>
 * Para evitar complicaciones a la hora de definir claves compuestas (pk,fk y unique) todas
 * estas restricciones se usan como restricciones de tablas mediante {@link TableConstraint}
 * por mas de que se trate de una clave no compuesta como por ejemplo una clave primaria
 * compuesta por una única columna.
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.5
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
	 * de las columnas se establecen en la clase {@link Column}, al añadir las clases
	 * con este método solo se añadirá la definición de columna cualquier restricción
	 * debe ser agregada mediante una {@link TableConstraint} o a través de los métodos
	 * específicos para añadir restricciones.
	 * 
	 * @param c La columna a ser añadida en la tabla.
	 * @see #addPrimaryKeyColumn(Column)
	 * @see #addForeignKeyColumn(Column)
	 * @see #addUniqueColumn(Column)
	 */
	void addColumn(Column c);
	
	/**
	 * Agrega una restricción de tabla pudiendo ser de cualquiera de los tipos
	 * internos que se define para {@link TableConstraint}, hay que tener en
	 * cuenta que una tabla solo puede tener una restricción del tipo PK y varias
	 * del tipo FK y UNIQUE. Las columnas que se agreguen mediante este método serán
	 * automáticamente agregadas a la lista de columnas de la tabla revisando
	 * previamente si estas no fueron ya agregadas. Y también revisa que no se este
	 * agregando mas de una clave primaria.
	 * 
	 * @param tableConstraint La restricción de tabla que se quiere agregar.
	 */
	void addTableConstraint(TableConstraint tableConstraint);
	
	/**
	 * Permite agregar una columna como clave primaria, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea poner un nombre a
	 * la restricción se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param pkcolumn La columna que será clave primaria.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addPrimaryKeyColumn(Column pkcolumn);
	
	/**
	 * Permite agregar una columna como clave primaria y con la restricción {@code AUTOINCREMENT} siendo este
	 * el único método con el que se puede realizar tal acción, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea poner un nombre a
	 * la restricción se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param autoPKColumn La columna que será clave primaria y autoincrement.
	 */
	void addAutoincrementPrimaryKeyColumn(Column autoPKColumn);
	
	/**
	 * Permite agregar una lista de columnas como clave primaria, internamente arma una
	 * {@link TableConstraint} con las columnas, si se desea poner un nombre a
	 * la restricción se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param pkcolumns Las columnas que serán clave primaria compuesta.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addCompositePrimaryKeyColumns(Vector<Column> pkcolumns);
	
	/**
	 * Permite agregar una columna con la restricción UNIQUE, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea poner un nombre a
	 * la restricción se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param uniqueColumn La columna que será UNIQUE.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addUniqueColumn(Column uniqueColumn);
	
	/**
	 * Permite agregar una lista de columnas con la restricción UNIQUE, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea poner un nombre a
	 * la restricción se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param uniqueColumns Las columnas que serán UNIQUE.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addCompositeUniqueColumns(Vector<Column> uniqueColumns);
	
	/**
	 * Permite agregar una columna como una clave foranea, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea armar una clave foranea
	 * mas compleja se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param fkcolumn La columna que será clave foranea.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addForeignKeyColumn(Column fkColumn);
	
	/**
	 * Permite agregar una lista de columnas como una clave foranea, internamente arma una
	 * {@link TableConstraint} con la columna, si se desea armar una clave foranea
	 * mas compleja se debería usar directamente el método {@link #addTableConstraint(TableConstraint)}.
	 * 
	 * @param fkcolumn Las columnas que serán una clave foranea.
	 * @see #addTableConstraint(TableConstraint)
	 */
	void addForeignKeyColumn(Vector<Column> fkColumns);
	
	//boolean isCompositePK();
	
	//String getCompositePK();

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
