package com.crossdb.sql;


/**
 * Esta clase representa una sentencia SELECT. Si nunca se agrega una columna,
 * llamando a {@link #addColumn(String)} por ejemplo, se tomara como que se
 * quieren devolver todas las columnas resultantes, o sea SELECT * FROM...
 * <p>
 * Además provee una interfaz para agregar los comandos correspondientes a la
 * sentencia SQL 
 * 
 * <p>
 * Copyright: Copyright (c) 2002 Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A.Cruz
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
	
	//TODO las columnas pueden tener alias, no se agrego todavia.
	/**
	 * Agrega una columna a la sentencia SELECT que se esta armando. Si no hay
	 * columnas agregadas a la sentencia (no se llamo a ningún método addColumn)
	 * se devolverá de manera predeterminada todas las columnas (SELECT *)
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
	 * Usado para agregar las funciones agregadas que poseen los motores de BB
	 * DD tales como: AVG, COUNT, MIN, MAX, SUM, etc.
	 * 
	 * @param function
	 *            el nombre de la función.
	 * @param column
	 *            el nombre de la columna.
	 */
	// TODO agregar funciones desde la clase Functions
	void addFunctionColumn(String function, String column);

	/**
	 * For use with aggregate functions such as: AVG COUNT MIN MAX SUM etc...
	 * <p>
	 * Warning: Does not guarantee database independence if used, use the
	 * aggregage functions such as countColumn to get database independence
	 * 
	 * @param function
	 * @param table
	 * @param column
	 */
	void addFunctionColumn(String function, String table, String column);

	/**
	 * Usado para agregar las funciones agregadas que poseen los motores de BB
	 * DD tales como: AVG, COUNT, MIN, MAX, SUM, etc.
	 * <p>
	 * Warning: Does not guarantee database independence if used, use the
	 * aggregage functions such as countColumn to get database independence
	 * 
	 * @param alias
	 *            es la etiqueta o alias que tomara esta columna en la tabla
	 *            resultante de la consulta
	 * @param functionId
	 *            el identificador de la función que se especifica en Functions
	 * @param table
	 *            especifica la tabla de la que se esta tomando la columna
	 * @param column
	 *            el identificador de la columna
	 */
	void addFunctionColumn(String alias, int functionId, String table,
			String column);

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

	//TODO la tabla de FROM puede provenir de una sentencia select
	/**
	 * Agrega una tabla a la sentencia SELECT sobre la cual se ara la consulta
	 * con INNER JOIN como predeterminado. Es decir el identificador de tabla
	 * que se agregara después de FROM.
	 * 
	 * @param table
	 *            el nombre de la tabla sobre la cual se esta haciendo la
	 *            consulta.
	 */
	void addTable(String table);
	
	Join addJoin();

//	/**
//	 * Agrega una tabla a la sentencia SELECT con la opción de JOIN
//	 * seleccionada. Las opciones de JOIN se obtienen de las constantes
//	 * declaradas en la clase {@link Join}.
//	 * 
//	 * @param joinType
//	 *            - type of join using the fields in Join.java
//	 * @param table
//	 *            - table name to add to query
//	 * @see Join
//	 */
//	void addTable(int joinType, String table);
//
//	/**
//	 * Instead of creating a Join then inserting it, since you can only have one
//	 * condition on a left outer join to work with oracle, this is the
//	 * convenient way to do it.
//	 * 
//	 * @param join_type
//	 *            - type of join using the fields in Join.java
//	 * @param table
//	 *            - table name to add to query
//	 * @see Join
//	 * @see WhereCondition
//	 */
//	void addTable(int join_type, String table, WhereCondition cond);
//
//	/**
//	 * WARNING: This may get deprecated as it is unecessary considering that you
//	 * can only have one join condition on an outer join (see requirements doc
//	 * at www.crossdb.com)
//	 * 
//	 * @param join
//	 *            - a join object that has all the information needed.
//	 * @see Join
//	 */
//	void addTable(Join join);

	// void removeColumn(String column);
	// Not sure how the function thing will work cause there are too many
	// function variations
	// Maybe have a separate function for each? like datediff() would actually
	// be a function
	// in a db dependent class implementation called Functions or something so
	// you would need
	// an interface called SQLFunctions or something with all the functions in
	// it.
	// void addColumn(int function, String column); // functions map to
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

	// void setLimit(int count);
	// void setLimit(int offset, int count);

	/**
	 * Returns the SQL statement.
	 */
	// String toString();

	/* *
	 * Uses stmt to execute the query. This is so you can keep reusing the same
	 * statement. Be sure to use new statements if you want more than one
	 * resultset open at the same time. / CrossdbResultSet
	 * execute(java.sql.Statement stmt) throws SQLException ;
	 * 
	 * /* * This one takes a java.sql.Connection and then creates a statement
	 * and executes.
	 * 
	 * The statement created is closed when the return ResultSet is closed. /
	 * CrossdbResultSet execute(Connection conn) throws SQLException ;
	 * 
	 * /**
	 * 
	 * @see SQLFactory#setSchema
	 */
	// public void setSchema(String schema);

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

//	/**
//	 * Adds an optimization hint to the query.
//	 * 
//	 * @see OptimizationHint
//	 * @param optimizationHint
//	 */
//	void addOptimizationHint(OptimizationHint optimizationHint);

}
