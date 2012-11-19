package com.crossdb.sql;

/**
 * Representa una sentencia {@code UPDATE} define algunos métodos extras para
 * poder realizar acciones de manera mas simple como por ejemplo el método {@link #addToColumn(String, int)}
 * aunque la mas importante es el método {@link #addWhere()}. que se usa para poder
 * establecer la restricción where.
 * 
 * @author Travis Reeder - travis@spaceprogram.com Space Program Inc. Copyright (c) 2002.
 * @author Nelson Efrain Abraham Cruz - neac03@gmail.com
 * @version 0.3
 */


public abstract class UpdateQuery extends DefaultUpdateableQuery {

	/**
	 * El constructor de la clase obliga a que se le pase una implementación de
	 * el formateador de tipos de datos para que la clase sepa como formatear los
	 * diferentes tipos de datos.
	 * 
	 * @param formatter La clase que formatea los tipos de datos.
	 */
	public UpdateQuery(Formatter formatter) {
		super(formatter);
	}

	// TODO revisar si crea algún problema
	/**
	 * This will add a value to a column during an update, for example:
	 * columnName + 2
	 * 
	 * @param column
	 * @param valueToAdd
	 *            an integer value to add, can also be a negative number
	 */
	public abstract void addToColumn(String column, int valueToAdd);

	/**
	 * Same as addToColumn(String, int) version, but with a double
	 * 
	 * @see UpdateQuery#addToColumn(String, int)
	 * @param column
	 * @param valueToAdd
	 */
	public abstract void addToColumn(String column, double valueToAdd);

	/**
	 * convenience method, equivalent to addToColumn(column, 1);
	 * 
	 * @param column
	 */
	public abstract void incrementColumn(String column);
	
	/**
	 * Crea una cláusula where para la sentencia, puede ser llamado
	 * mas de una vez para crear una cláusula where compleja. Este método
	 * obliga a que la clase que lo implemente posea como atributo a una clase
	 * {@link WhereClause} sobre la cual se pueden ir armando la cláusula, no hay
	 * método para setear una instancia de la misma por lo que siempre se debe trabajar
	 * sobre la referencia a la instancia que crea y entrega este método, por ello mismo
	 * este método siempre entrega una referencia a un único objeto. Para
	 * mas detalle de uso referirse a la documentación de dicha clase.
	 * 
	 * @see WhereClause
	 */
	public abstract WhereClause addWhere();

}
