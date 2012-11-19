package com.crossdb.sql;

/**
 * Representa el comportamiento de una sentencia {@code DELETE}, es bastante simple
 * pues solo necesita establecer el nombre de la tabla sobre la que se esta trabajando
 * y una restricción {@code WHERE}.
 * 
 * @author Travis Reeder - travis@spaceprogram.com - Space Program Inc. Copyright (c) 2002
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.3
 */


public interface DeleteQuery extends UpdateStatement{

	// public SelectQuery(String table);
	/**
	 * Especifica el nombre de la tabla a la que se quiere modificar.
	 * 
	 * @param table El nombre de la tabla
	 */
	public void setTable(String table);

	/**
	 * Agrega una cláusula {@code WHERE} representada por la clase {@link WhereClause} 
	 * a la sentencia {@code DELETE}, dicha clase es la que se encarga de armar la
	 * restricción. La función debe devolver siempre la misma referencia a la única
	 * instancia miembro de la sentencia, puesto que no hay método para establecer
	 * una instancia como miembro de esta clase.  	
	 */
	public WhereClause addWhere();

}
