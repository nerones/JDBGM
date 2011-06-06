package com.crossdb.sql;

import java.sql.ResultSetMetaData;
import java.sql.Types;

import com.spaceprogram.sql.mysql.MySQLDataTypes;
// TODO review the documentation
/**
 * Generic column class.
 * 
 * Note: This was taken from DataField.
 * 
 * <p>
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * 
 * @version 0.1
 * @author Jorge P&eacute;rez Burgos
 * @author jorge.perez@adaptia.net - http://www.adaptia.net (C) 2003
 * @author Nelson Efrain A. Cruz
 * @version 0.2
 */
public class Column {

	private String name = null;
	/**
	 * Indica si la columna acepta valores nulos.
	 */
	private int is_nullable = ResultSetMetaData.columnNullable; // equivalent to
	// java.sql.ResultSetMetaData
	// nullable fields
	/**
	 * Identifica el tipo de dato de la columna
	 */
	private int type = 0; // maps to java.sql.Types
	/**
	 * Tamaño por defecto para las cadenas (CHAR, VARCHAR)
	 */
	private int size = 50; // default 50
	private int columnIndex = 0;
	/**
	 * Indica si es clave primaria o no.
	 */
	private boolean isPrimaryKey = false;
	/**
	 * Indica si es clave foranea o no.
	 */
	private boolean isForeignKey = false;
	// If a column is foreign key we need the the foreign table and the foreign
	// primary key
	/**
	 * Tabla a la que apunta la columna en el caso de que se trate de una
	 * columna que sea clave foranea
	 */
	private String foreignTable = null;
	/**
	 * Nombre de la columna que es clave primaria de la tabla a la que apunta esta columna, 
	 * en el caso de que se trate de una columna que sea clave foranea
	 */
	private String foreignPrimaryKey = null;

	// auto inc variables
	/**
	 * Si la columna presenta la restricción AUTOINCREMENT
	 */
	/*
	 * Tener en cuenta que en SQLite solo puede ser autoincrement si es clave
	 * primaria
	 */// TODO es soportado por SQLite pero solo cuando es INTEGER PRIMARY KEY eliminarlo?
	private boolean isAutoIncrement = false;
	/*
	 * variables de ajuste para AUTOINCREMENT no creo que sea soportado por SQLite
	 */
	private int start_with = 1;
	private int increment_by = 1;
	/*
	 *Sequence lo vi en oracle es para designar algo asi como un generador de "secuencias"
	 *para agregar a las tablas, no quedo claro: se crea una sequence y esta genera  
	 */
	private String sequence = null;
	
	/*
	 * creo que es el valor por defecto pero tengo duda
	 */
	private String col_default = null;
	
	/**
	 * Si la columna es del tipo UNIQUE
	 */
	private boolean unique = false;
	
	/**
	 * un valor para la columna
	 */
	private Object value;
	
	private boolean noAlter;
	
	

	/**
	 * Crea una columna con un nombre y un valor, usado principalmente cuando creamos
	 * una columna para agregar a una sentencia INSERT o UPDATE
	 * 
	 * @param columnName - el nombre de la columna
	 * @param columnValue - el valor de la columna
	 */
	public Column(String columnName, Object columnValue) {
		name = columnName;
		value = columnValue;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	

	public boolean isNoAlter() {
		return noAlter;
	}

	public void setNoAlter(boolean noAlter) {
		this.noAlter = noAlter;
	}

	/*
	 * No se para que es el columnIndex
	 */
	public Column(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * Crea una columna con el nombre indicado en name
	 * 
	 * @param name
	 *            - el nombre de la columna.
	 */
	public Column(String name) {
		this.name = name;
	}

	/**
	 * Crea una columna con nombre dado por name y el tipo de dato de la columna
	 * dado por java.sql.Types.
	 * 
	 * @param name
	 *            - el nombre de la columna
	 * @param type
	 *            - el tipo de dato de la columna tomado de
	 *            {@link java.sql.Types}
	 */
	public Column(String name, int type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Crea un objeto Columna pero con la posibilidad de definir si es clave
	 * primaria y si tiene la opción de autoincrementar activa, toma el tipo de
	 * datos desde la clase {@link java.sql.Types}
	 * 
	 * @param name
	 *            El nombre de la columna
	 * @param type
	 *            El tipo de dato para la columna
	 * @param isPK
	 *            Si es clave primaria
	 * @param isAutoIncr
	 *            si tiene activa la opción de autoincrementar
	 */
	public Column(String name, int type, boolean isPK, boolean isAutoIncr) {
		this.name = name;
		this.type = type;
		isPrimaryKey = isPK;
		isAutoIncrement = isAutoIncr;
	}
	
	/**
	 * Devuelve el nombre o identificador de la columna. Un simple getter
	 * 
	 * @return el nombre de la columna
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns 1 if yes, 0 if no, or -1 if unsure. same as in
	 * java.sql.ResultSetMetaData
	 */
	public int isNullable() {
		return is_nullable;
	}

	/**
	 * returns the java.sql.Types equivalent.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Para saber si la columna es una clave primaria
	 * @return TRUE si es clave primaria, FALSE en caso contrario.
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	
	/**
	 * Para saber si la columna es clave foranea, en caso de serlo se tiene que
	 * especificar la tabla a la que se apunta y la clave primaria de esa tabla.
	 * @return TRUE si es clave primaria, FALSE en caso contrario.
	 */
	public boolean isForeignKey() {
		return isForeignKey;
	}
	
	/**
	 * Para saber si la columna es AUTOINCREMENT
	 * @return true si es AUTOINCREMENT, false en caso contrarop
	 */
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean b) {
		isAutoIncrement = b;
	}

	public void setStartWith(int sw) {
		start_with = sw;
	}

	public int getStartWith() {
		return start_with;
	}

	public void setIncrementBy(int ib) {
		increment_by = ib;
	}

	public int getIncrementBy() {
		return increment_by;
	}
	// TODO mover setSequence SQLite lo soporta?
	/**
	 * Sets the sequence used for this column if it's an auto increment column.
	 */
	public void setSequence(String sequence_name) {
		sequence = sequence_name;
	}

	public String getSequence() {
		return sequence;
	}

	public String getDefaultValue() {
		return col_default;
	}
	
	
	/**
	 * Para ver si es una columna con la restricción UNIQUE
	 * @return true si la columna tiene la restricción UNIQUE, falso en caso contrario
	 */
	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * For varchars size
	 */
	public int getSize() {
		return size;
	}

	public void setSize(int s) {
		size = s;
	}

	public void setName(String n) {
		name = n;
	}

	public void setPrimaryKey(boolean b) {
		isPrimaryKey = b;
	}

	public void setForeignKey(boolean b) {
		isForeignKey = b;
	}

	/**
	 * Takes an int because ResultSetMetaData returns an int on this one.
	 * 
	 * From java.sql.ResultSetMetaData in java API Returns: the nullability
	 * status of the given column; one of columnNoNulls, columnNullable or
	 * columnNullableUnknown
	 */
	public void setNullable(int n) {
		is_nullable = n;
	}

	/**
	 * Especifica el tipo de datos de la columna.
	 * 
	 * @param t - el tipo de dato tomado de {@link Types}
	 */
	public void setType(int t) {
		type = t;
	}

	/**
	 * Especifica el tipo de datos de la columna y de ser necesario el tamaño para
	 * tipos de dato como CHAR, de no ser necesario especificar un tamaño para el tipo
	 * ha de usarse {@link #setType(int)}.
	 * @param t - el tipo de dato tomado de {@link Types}
	 * @param size - el tamaño del tipo
	 */
	public void setType(int t, int size) {
		type = t;
		this.size = size;
	}

	public void setDefaultValue(String s) {
		col_default = s;
	}

	/**
	 * @return String
	 */
	public String getForeignPrimaryKey() {
		return foreignPrimaryKey;
	}

	/**
	 * @return String
	 */
	public String getForeignTable() {
		return foreignTable;
	}

	/**
	 * Sets the foreignPrimaryKey.
	 * 
	 * @param foreignPrimaryKey
	 *            The foreignPrimaryKey to set
	 */
	public void setForeignPrimaryKey(String foreignPrimaryKey) {
		this.foreignPrimaryKey = foreignPrimaryKey;
	}

	/**
	 * Sets the foreignTable.
	 * 
	 * @param foreignTable
	 *            The foreignTable to set
	 */
	public void setForeignTable(String foreignTable) {
		this.foreignTable = foreignTable;
	}

	/**
	 * Añade una clave y tabla foranea para esta Columna, marca implícitamente
	 * esta clase como una que tiene clave foranea, este método esta pensado
	 * para añadir toda la información de clave foranea de una sola vez.
	 * 
	 * @param foreignTable
	 *            La tabla a la que se referencia
	 * @param foreignPK
	 *            La clave primaria de la tabla a la que se esta referenciando
	 * 
	 * @see #isForeignKey()
	 * @see #setForeignKey(boolean)
	 * @see #setForeignPrimaryKey(String)
	 * @see #setForeignTable(String)
	 */
	public void setForeignKey(String foreignTable, String foreignPK) {
		isForeignKey = true;
		this.foreignTable = foreignTable;
		foreignPrimaryKey = foreignPK;
	}

	/**
	 * Añade una clave y tabla foranea para esta Columna asumiendo que la clave
	 * foranea tiene el mismo identificador en ambas tablas, marca
	 * implícitamente esta clase como una que tiene clave foranea, este método
	 * esta pensado para añadir toda la información de clave foranea de una sola
	 * vez.
	 * 
	 * @param foreignTable
	 *            La tabla a la que se referencia
	 * 
	 * @see #isForeignKey()
	 * @see #setForeignKey(boolean)
	 * @see #setForeignPrimaryKey(String)
	 * @see #setForeignTable(String)
	 */
	public void setForeignKey(String foreignTable) {
		isForeignKey = true;
		this.foreignTable = foreignTable;
		foreignPrimaryKey = name;
	}
	
//	public String toString(DataTypes datatype){
//		String query1 = "";
//		
//		query1 += getName() + " ";
//
//		query1 += datatype.getAsString(this);
//		// if (df.isAutoIncrement()) {
//		// //una tabla de mysql solo puede tener una columna auto_increment
//		// query1 += " AUTO_INCREMENT";// primary key NOT NULL";
//		if (isUnique()) query1 += " UNIQUE";
//		if (isPrimaryKey() && !isCompositePK()){
//			query1 += " PRIMARY KEY";
//			if (isAutoIncrement()) query1 += " AUTO_INCREMENT";
//		} 
//		if (isNullable() == 0)
//			query1 += " NOT NULL";
//		if (getDefaultValue() != null) {
//			// "datetime")){
//				// Can't use functions like Now() in defaults in mysql
//			
//			if (getType() == java.sql.Types.VARCHAR
//					|| getType() == java.sql.Types.CHAR) {
//				query1 += " DEFAULT '" + getDefaultValue() + "' ";
//			} else if (!(getType() == java.sql.Types.TIMESTAMP)) { 
//				query1 += " DEFAULT " + getDefaultValue();
//			}
//
//		}
//		
//		
//		return query1;
//	}

}
