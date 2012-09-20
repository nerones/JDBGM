
package com.crossdb.sql;

import java.math.BigDecimal;
import java.util.Vector;

/**
 * Esta interfaz definirá el comportamiento de las consultas que modificaran el contenido
 * de tablas ya existentes como ser {@link InsertQuery} y {@link UpdateQuery}. Los métodos
 * comunes que definirá está interfaz son {@link #setTable(String)} y {@link #addColumn(Column)},
 * esta ultima función es polimorfica y acepta distintos tipos de argumentos en pos de un
 * agregado de columnas mas sencillo. Se proveen métodos abreviados para agregar columnas
 * para los tipos de datos mas comunes, si no se encuentra el método adecuado se puede
 * usar {@link #addColumn(Column)} que es mucho mas flexible o bien {@link #addColumnNoAlter(String, String)}
 * que pasa explícitamente el valor para la columna.
 * <p>
 * Hay que tener en cuenta que la función addColumn()...
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.3
 * 
 */
public interface UpdateableQuery extends UpdateStatement{
	// TODO revisar la documentación de las funciones addColumn
	/**
	 * Agrega el nombre de la tabla sobre la cual se esta por trabajar.
	 * @param tableEl nombre de la tabla.
	 */
	void setTable(String table);

	/**
	 * Agrega una columna a la sentencia a la cual se le quiere alterar (agregar)
	 * el valor. Se restringe el tipo de dato para el valor de la columna para que
	 * cuando se quiera interpretar su valor se lo pueda hacer correctamente.
	 * @param column El nombre de la columna.
	 * @param value Un valor para la columna.
	 */
	void addColumn(String column, String value);
	// void addColumn(String column, Integer value);
	
	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, int value);

	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, float value);

	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, double value);

	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, java.util.Date value);

	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, boolean value);

	/**
	 * @see #addColumn(String, String)
	 */
	void addColumn(String column, BigDecimal value);
	
	/**
	 * Agrega una columna a la sentencia a la cual se le quiere alterar (agregar)
	 * el valor. Se usa la clase {@link Column} para almacenar los datos de la columna,
	 * este método es mucho mas flexible que los otros métodos como {@link #addColumn(String, boolean)}
	 * que restringe el tipo de dato. Este método puede reemplazar a cualquiera de los
	 * otros, solo que estos otros proveen una forma abreviada de agregar columnas.
	 * @param column El nombre de la columna.
	 */
	public void addColumn(Column column);

	/**
	 * Agrega una lista de columnas a la consulta actual en ves de agregar de a una columna
	 * por ves.
	 */
	void appendColumns(Vector<Column> columns);

	/**
	 * Obtiene un {@link Vector} que contiene las columnas que seran modificadas por 
	 * @return List of ColumnValues
	 */
	Vector<Column> getColumns();

	/**
	 * This one is used for passing in a string value and not altering it on the
	 * insert. ie: Not putting single quotes around it or escaping anything.
	 * Just goes in exactly as it is in value.
	 * <p>
	 * Esta función es usada para pasar cadenas de texto que no serán alteradas cuando
	 * se este armando la correspondiente consulta. Puede contener cualquier valor y
	 * este sera pasado al DBMS sin ninguna alteración. Hay que tener cuidado ya que
	 * por el momento no se toma ninguna contra medida en caso de que se este inyectando
	 * código malicioso.
	 */
	void addColumnNoAlter(String column, String value);
	
	/**
	 * La intención de este método es recordar que se debe re escribir la función
	 * {@link Object#toString()}, se debe tener en cuenta que la clase base que implemente
	 * esta interfaz (de la que heredaran las implementaciones especificas) deberá declarar
	 * como abstracto el método para asi obligar a que sea implementado en las clases finales. 
	 * @return
	 */
	public String toString();
	
}
