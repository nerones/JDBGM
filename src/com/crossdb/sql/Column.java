package com.crossdb.sql;

import java.sql.ResultSetMetaData;
import java.sql.Types;
// TODO review the documentation
/**
 * Clase que representa una columna  de una tabla en una base de datos relacional.
 * 
 * Note: This was taken from DataField.
 * 
 * <p>
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Jorge P&eacute;rez Burgos - jorge.perez@adaptia.net - http://www.adaptia.net (C) 2003
 * @author Nelson Efrain A. Cruz
 * @version 0.2
 */
public class Column {

	private String columnName = null;
	
	/**
	 * Indica si la columna acepta valores nulos, tomando el mismo valor por
	 * defecto que ResultSetMetaData.columnNullable que es 1 y equivale a nullable.
	 */
	private int is_nullable = ResultSetMetaData.columnNullable;
//	TODO review how to handle dataTypes
	
	/**
	 * Identifica el tipo de dato de la columna
	 */
	private int type = 0; // maps to java.sql.Types
	
	/**
	 * Tamaño por defecto para las cadenas de texto variable como CHAR y VARCHAR
	 */
	private int varcharSize = 50;
	
	//private int columnIndex = 0;
	
	/**
	 * Indica si la columna es clave primaria o no.
	 */
	private boolean isPrimaryKey = false;
	
	/**
	 * Indica si la columna  es clave foranea o no.
	 */
	private boolean isForeignKey = false;

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

	/*
	 * Tener en cuenta que en SQLite solo puede ser autoincrement si es clave
	 * primaria en postgresql no existe la cláusula autoincrement así que no se
	 * usa en este proyecto
	 */
	//private boolean isAutoIncrement = false;
	
	/*
	 * variables de ajuste para AUTOINCREMENT que no son soportados por SQLite
	 */
	//private int start_with = 1;
	//private int increment_by = 1;
	
	/*
	 *Sequence lo vi en oracle es para designar algo asi como un generador de "secuencias"
	 *para agregar a las tablas, no quedo claro: se crea una sequence y esta genera  
	 */
	//private String sequence = null;
	
	/**
	 * El valor por defecto de la columna que solo puede ser un valor constante.
	 */
	private String columnDefaultValue = null;
	
	/**
	 * Si la columna es del tipo UNIQUE
	 */
	private boolean unique = false;
	
	/**
	 * Si bien esta clase define una columna, podemos usar una lista de columnas
	 * para almacenar los valores de cada columna correspondientes a una fila de
	 * una tabla, por lo que la columna también puede almacenar un valor
	 */
	private Object columnValue;
	
	/**
	 * Sirve para identificar si el valor que toma la columna no ha de ser alterado,
	 * por ejemplo si se quiere pasar un numero como string y que no sea convertido
	 * en integer por ejemplo
	 * 
	 */
	private boolean noAlter;
	
	

	/**
	 * Crea una columna con un nombre y un valor, usado principalmente cuando creamos
	 * una columna para agregar a una sentencia INSERT o UPDATE
	 * 
	 * @param columnName el nombre de la columna
	 * @param columnValue el valor de la columna
	 */
	public Column(String columnName, Object columnValue) {
		this.columnName = columnName;
		this.columnValue = columnValue;
	}

	/*
	 * No se para que es el columnIndex
	 */
//	public Column(int columnIndex) {
//		this.columnIndex = columnIndex;
//	}

	/**
	 * Crea una columna con el nombre indicado y los demás valores a sus
	 * valores por defecto.
	 * 
	 * @param columnName el nombre de la columna.
	 */
	public Column(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Crea una columna con nombre dado por name y el tipo de dato de la columna
	 * dado por java.sql.Types.
	 * 
	 * @param name
	 *             el nombre de la columna
	 * @param type
	 *            el tipo de dato de la columna tomado de {@link java.sql.Types}
	 */
	public Column(String name, int type) {
		this.columnName = name;
		this.type = type;
	}

	/**
	 * Crea un objeto Columna pero con la posibilidad de definir si es clave
	 * primaria, toma el tipo de
	 * datos desde la clase {@link java.sql.Types}
	 * 
	 * @param name
	 *            El nombre de la columna
	 * @param type
	 *            El tipo de dato para la columna
	 * @param isPK
	 *            Si es clave primaria
	 */
	public Column(String name, int type, boolean isPK) {
		this.columnName = name;
		this.type = type;
		isPrimaryKey = isPK;
		//isAutoIncrement = isAutoIncr;
	}
	
	public Object getColumnValue() {
		return columnValue;
	}

	/**
	 * Pone un valor para la columna, que se debe corresponder con el tipo
	 * declarado para la misma, que luego será usado para ingresar filas de datos
	 * a la tabla correspondiente. 
	 * @param value Un valor para la columna
	 */
	public void setColumnValue(Object value) {
		this.columnValue = value;
	}
	
	
	/**
	 * @see #setNoAlter(boolean)
	 * 
	 */
	public boolean isNoAlter() {
		return noAlter;
	}

	/**
	 * Si se desea que el valor de la columna ingresado mediante {@link #setColumnValue(Object)}
	 * no sea modificado a la hora de convertir la columna a una cadena de texto
	 * se debe poner en true esta bandera.
	 * 
	 * @param noAlter indica si el valor de la columna no debe ser alterado
	 */
	public void setNoAlter(boolean noAlter) {
		this.noAlter = noAlter;
	}
	
	/**
	 * Devuelve el nombre o identificador de la columna. Un simple getter
	 * 
	 * @return el nombre de la columna
	 */
	public String getName() {
		return columnName;
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
	
	/*
	 * Para saber si la columna es AUTOINCREMENT
	 * @return true si es AUTOINCREMENT, false en caso contrarop
	 */
//	public boolean isAutoIncrement() {
//		return isAutoIncrement;
//	}
//
//	public void setAutoIncrement(boolean b) {
//		isAutoIncrement = b;
//	}

//	public void setStartWith(int sw) {
//		start_with = sw;
//	}
//
//	public int getStartWith() {
//		return start_with;
//	}
//
//	public void setIncrementBy(int ib) {
//		increment_by = ib;
//	}
//
//	public int getIncrementBy() {
//		return increment_by;
//	}
	// TODO mover setSequence SQLite lo soporta?
	/*
	 * Sets the sequence used for this column if it's an auto increment column.
	 */
//	public void setSequence(String sequence_name) {
//		sequence = sequence_name;
//	}

//	public String getSequence() {
//		return sequence;
//	}

	public String getColumnDefaultValue() {
		return columnDefaultValue;
	}
	
	
	/**
	 * Para ver si es una columna con la restricción UNIQUE
	 * @return true si la columna tiene la restricción UNIQUE, falso en caso contrario
	 */
	public boolean isUnique() {
		return unique;
	}
	
	/**
	 * Si se pone en true esta bander la columna tendra la restricción {@code UNIQUE},
	 * el valor por defecto es false.
	 * @param unique True si se desea que la columna sea UNIQUE
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * @see #setDefaultSize(int)
	 * @return El tamaño por defecto para los tipos de datos que lo soporten
	 */
	public int getDefaultSize() {
		return varcharSize;
	}

	/**
	 * Pone un valor por defecto para los tipos de datos que lo soporten.
	 * @param s
	 */
	public void setDefaultSize(int s) {
		varcharSize = s;
	}

	public void setColumnName(String n) {
		columnName = n;
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
	 * @param t el tipo de dato tomado de {@link Types}
	 */
	public void setType(int t) {
		type = t;
	}

	/**
	 * Especifica el tipo de datos de la columna y de ser necesario el tamaño para
	 * tipos de dato como CHAR, de no ser necesario especificar un tamaño para el tipo
	 * ha de usarse {@link #setType(int)}.
	 * 
	 * @param t el tipo de dato tomado de {@link Types}
	 * @param size el tamaño del tipo
	 */
	public void setType(int t, int size) {
		type = t;
		this.varcharSize = size;
	}

	/**
	 * Un valor por defecto para los elementos de las filas que se vayan
	 * agregando a esta columna que solo pueden ser un valor constante como un
	 * numero o una cadena de caracteres.
	 * 
	 * @param s el valor constante por defecto de la columna
	 */
	public void setDefaultColumnValue(String s) {
		columnDefaultValue = s;
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
	 * Agrega el nombre de la clave primaria de la tabla foranea.
	 * 
	 * @param foreignPrimaryKey
	 *            The foreignPrimaryKey to set
	 * @see #setForeignPrimaryKey(String)
	 */
	private void setForeignPrimaryKey(String foreignPrimaryKey) {
		this.foreignPrimaryKey = foreignPrimaryKey;
	}

	/**
	 * 
	 * Agrega el nombre de la tabla a la que apunta la clave foranea de ser la columna
	 * una clave foranea. Tener en cuenta que se debe poner en verdadero {@link #isForeignKey} y este
	 * método no lo hace por eso
	 * se debe usar algunos de los métodos {@link #setForeignKey(String)} o {@link #setForeignKey(String, String)}
	 * 
	 * @param foreignTable
	 *            The foreignTable to set
	 */
	private void setForeignTable(String foreignTable) {
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
	 * 
	 */
	public void setForeignKey(String foreignTable, String foreignPK) {
		isForeignKey = true;
		setForeignTable(foreignTable);
		setForeignPrimaryKey(foreignPK);
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
	 * 
	 */
	public void setForeignKey(String foreignTable) {
		isForeignKey = true;
		setForeignTable(foreignTable);
		setForeignPrimaryKey(columnName);
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
