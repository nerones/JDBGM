package com.crossdb.sql;

/**
 This is an initial beta of a class that will represent a query string


 /**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */


public abstract class UpdateQuery extends DefaultUpdateableQuery {

	//public abstract void setTable(String table);

//	public abstract void addWhereCondition(String x, int comparison, String y);
//
//	public abstract void addWhereCondition(String x, int comparison, int y);
//
//	public abstract void addWhereCondition(String x, int comparison, Date y);
//
//	public abstract void addWhereCondition(String and_or, String x, int comparison, String y);
//
//	public abstract void addWhereCondition(String and_or, String x, int comparison, int y);
//
//	public abstract void addWhereCondition(String and_or, String x, int comparison, Date y);
//
//	public abstract void addWhereString(String x, int comparison, String y);
//
//	public abstract void addWhereString(String and_or, String x, int comparison, String y);
//
//	public abstract void addWhereCondition(WhereCondition cond);
//
//	public abstract void addWhereClause(WhereClause wc);
//
//	public abstract void addWhereNotNull(String col);
//
//	public abstract void addWhereNotNull(String and_or, String col);
//
//	public abstract void addWhereIsNull(String col);
//
//	public abstract void addWhereIsNull(String and_or, String col);


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
	 * @see WhereClause.
	 */
	public abstract WhereClause addWhere();

}
