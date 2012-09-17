
package com.crossdb.sql;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Esta interfaz definirá el comportamiento de las consultas que modificaran el contenido
 * de tablas ya existentes como ser {@link InsertQuery} y {@link UpdateQuery}. Los métodos
 * comunes que definirá está interfaz son {@link #setTable(String)} y {@link #addColumn(Column)},
 * esta ultima función es polimorfica y acepta distintos tipos de argumentos en pos de un
 * agregado de columnas mas sencillo.
 * <p>
 * Hay que tener en cuenta que la función addColumn()...
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.2
 * 
 */
public interface UpdateableQuery extends UpdateStatement{
	// TODO revisar la documentación de las funciones addColumn
	void setTable(String table);

	void addColumn(String column, String value);

	// void addColumn(String column, Integer value);
	void addColumn(String column, int value);

	void addColumn(String column, float value);

	void addColumn(String column, double value);

	void addColumn(String column, java.util.Date value);

	void addColumn(String column, boolean value);

	void addColumn(String column, BigDecimal value);
	
	public void addColumn(Column column);

	/**
	 * Agrega una lista de columnas a la consulta actual en ves de agregar de a una columna
	 * por ves.
	 */
	void appendColumns(ArrayList<Column> columns);

	/**
	 * Obtiene un {@link ArrayList} que contiene las columnas que seran modificadas por 
	 * @return List of ColumnValues
	 */
	ArrayList<Column> getColumns();

	/*
	 * void addColumn(int column, String value); //void addColumn(String column,
	 * Integer value); void addColumn(int column, int value); void addColumn(int
	 * column, float value); void addColumn(int column, double value); void
	 * addColumn(int column, java.util.Date value); void addColumn(int column,
	 * boolean value);
	 */
	// TODO sería buena idea enfatizar algunas palabras con negritas
	/**
	 * This one is used for passing in a string value and not altering it on the
	 * insert. ie: Not putting single quotes around it or escaping anything.
	 * Just goes in exactly as it is in value.
	 * <p>
	 * Esta función es usada para pasar cadenas de texto que no serán alteradas cuando
	 * se este armando la correspondiente consulta. Puede contener cualquier valor y
	 * este sera pasado al DBMS sin ninguna alteración. Hay que tener cuidado ya que
	 * por el momento no se toma ninguna contramedida en caso de que se este inyectando
	 * código malicioso.
	 */
	void addColumnNoAlter(String column, String value);
}
