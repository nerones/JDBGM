package com.crossdb.sql;


/**
 * Esta clase representa una sentencia SELECT. Si nunca se agrega una columna,
 * llamando a {@link #addColumn(String)} por ejemplo, se tomara como que se
 * quieren devolver todas las columnas resultantes, o sea SELECT * FROM...
 * <p>
 * Además provee una interfaz para agregar los comandos correspondientes a la
 * sentencia SQL 
 * 
 * @author Nelson Efrain A. Cruz
 * @author Travis Reeder - travis@spaceprogram.com Copyright (c) 2002 Space Program Inc.
 * @version 0.5
 */
public interface SelectQuery extends QueryStatement {

	/**
	 * Establece si la sentencia tiene la restricción {@code DISTINCT} o no, de ser
	 * false no tiene dicha restricción y la sentencia se comporta como si tuviera
	 * la restricción {@code ALL} la cual puede ser omitida pues es el comportamiento
	 * por defecto.
	 * 
	 * @param distinct Establece si la columna presenta la restricción o no.
	 */
	void setDistinct(boolean distinct);
	
	/**
	 * Agrega una columna a la sentencia SELECT que se esta armando. Si no hay
	 * columnas agregadas a la sentencia (no se llamo a ningún método addColumn)
	 * se devolverá de manera predeterminada todas las columnas (SELECT *).
	 * <p>
	 * Si se desea agregar una funcion definida por el usuario a la sentencia select
	 * se la puede agregar como una columna del modo 
	 * <strong>{@code addColumn("myFunction()")}</strong>
	 * 
	 * @param column
	 *            Nombre de la columna a devolver en la lista de columnas de la
	 *            sentencia select.
	 */
	void addColumn(String column);

	/**
	 * Agrega una columna a la sentencia SELECT pero haciendo referencia a una
	 * columna {@code column} en una tabla {@code table} por table.column, es usado cuando la
	 * sentencia select se este realiza sobre mas de una tabla.
	 * 
	 * @param table
	 *            el nombre de la tabla que contiene la columna que se quiere
	 *            agregar.
	 * @param column
	 *            el nombre de la columna que se esta agregando.
	 */
	void addColumn(String table, String column);
	
	/**
	 * Agrega una columna a la sentencia SELECT pero haciendo referencia a una
	 * columna {@code column} en una tabla {@code table} por table.column con el alias {@code alias}, es usado cuando la
	 * sentencia select se este realiza sobre mas de una tabla.
	 * 
	 * @param alias Un alias para la columna
	 * @param table El nombre de la tabla que contiene la columna que se quiere agregar
	 * @param column El nombre de la columna que se esta agregando
	 */
	void addColumn(String table, String column, String alias);

	/**
	 * Usado para agregar las funciones agregadas que poseen los motores de BB
	 * DD tales como: AVG, COUNT, MIN, MAX, SUM, etc.
	 * 
	 * @param function
	 *            el nombre de la función tomado desde la Functions.
	 * @param column
	 *            el nombre de la columna.
	 */
	// TODO agregar funciones desde la clase Functions
	void addFunctionColumn(String function, String column);

	/**
	 * Usado para agregar las funciones agregadas que poseen los motores de BB
	 * DD tales como: AVG, COUNT, MIN, MAX, SUM, etc.
	 * <p>
	 * Warning: Does not guarantee database independence if used, use the
	 * aggregate functions such as countColumn to get database independence
	 * 
	 * @param alias
	 *            es la etiqueta o alias que tomara esta columna en la tabla
	 *            resultante de la consulta
	 * @param functionId
	 *            el identificador de la función que se especifica en Functions
	 * @param column
	 *            el identificador de la columna
	 */
	void addFunctionColumn(String functionId, String column, String alias);
	

	void sumColumn(String column);

	void sumColumn(String table, String column);

	void countColumn(String column);

	void countColumn(String table, String column);

	void averageColumn(String column);

	void averageColumn(String table, String column);

	void minColumn(String column);

	void minColumn(String table, String column);

	void maxColumn(String column);

	void maxColumn(String table, String column);

	/**
	 * Agrega una tabla a la sentencia SELECT sobre la cual se ara la consulta,
	 * si se quieren agregar otras tablas mediante JOIN estas se deben agregar mediante
	 * el método {@link #addJoin()}, solo la primer tabla se agrega mediante esta función
	 * de modo que si se hace la consulta sobre una única tabla solo se debe llamar a esta
	 * función. Como es posible establecer sentencia {@code SELECT} anidada en vez de una tabla 
	 * mediante {@link #addTable(SelectQuery,String)} <strong>solo tendra efecto la ultima función en ser 
	 * llamada </strong> por ejemplo si antes se había llamado a {@link #addTable(SelectQuery,String)} y luego
	 * a esta función la sentencia representada en el parámetro SelectQuery será descartada y la sentencia
	 * tomara para {@code FROM} el nombre de la tabla.
	 * 
	 * @param table
	 *            el nombre de la tabla sobre la cual se esta haciendo la
	 *            consulta.
	 */
	void addTable(String table);
	
	/**
	 * Realiza la misma función que {@link #addTable(String)} pero en este caso
	 * la fuente para la consulta es otra consulta anidada, la que también puede
	 * estar envuelta en una restricción {@code JOIN}.
	 * 
	 * @param selectSource La sentencia {@code SELECT} anidada para esta consulta.
	 * @see #addTable(String)
	 */
	void addTable(SelectQuery selectSource, String alias);
	
	Join addJoin();

	//TODO La clausula where puede tomar funciones en sus condiciones
	/**
	 * Agrega una cláusula {@code WHERE} representada por la clase {@link WhereClause} 
	 * a la sentencia {@code SELECT}, dicha clase es la que se encarga de armar la
	 * restricción. La función debe devolver siempre la misma referencia a la única
	 * instancia miembro de la sentencia, puesto que no hay método para establecer
	 * una instancia como miembro de esta clase.  	
	 */
	WhereClause addWhere();
	
	/**
	 * Agrega la cláusula {@code GROUP BY} con las condiciones que se le pasan como
	 * cadena de caracteres.
	 * @param groupBy Las condiciones explícitas por las que se quiere agrupar.
	 */
	void addGroupBy(String groupBy);
	
	/**
	 * Agrega la cláusula {@code HAVING} con las condiciones que se le pasan como
	 * cadena de caracteres.
	 * @param havingExpresion Las condiciones explícitas por las que se quiere filtrar.
	 */
	void addHaving(String havingExpresion);

	/**
	 * Returns the SQL statement.
	 */
	// String toString();


	/**
	 * Para establecer la {@code UNION} de la sentencia actual con la que se
	 * esta pasando por parámetro.
	 * 
	 * @param sq La sentencia {@code SELECT} con la que se quiere hacer UNION
	 */
	void union(SelectQuery sq);
	
	/**
	 * Para establecer la {@code UNION ALL} de la sentencia actual con la que se
	 * esta pasando por parámetro.
	 * 
	 * @param sq La sentencia {@code SELECT} con la que se quiere hacer UNION
	 */
	void unionAll(SelectQuery sq);

	
	/**
	 * Agrega la restricción {@code ORDER BY} a la sentencia a través de una cadena
	 * de texto.
	 * @param orderBy Una cadena que contiene la lista de columnas por las que se quiere ordenar.
	 */
	void addOrderBy(String orderBy);
	
	/**
	 * Limita el numero de filas que puede devolver como resultado la sentencia.
	 * 
	 * @param rowCount La máxima cantidad de filas que debe devolver la sentencia.
	 */
	void setLimit(int rowCount);

	/**
	 * Limita el numero de filas que puede devolver como resultado la sentencia
	 * pero indicando una cantidad de filas que se deben omitir.
	 * @param offset
	 *            EL numero de filas que se omiten del resultado antes de devolver el resultado.
	 * @param rowCount
	 *            El limite de filas que puede devolver como resultado.
	 */
	void setLimit(int offset, int rowCount);

}
