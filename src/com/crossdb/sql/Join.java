package com.crossdb.sql;

/**
 * Esta clase representa la union de tablas mediante {@code JOIN}, los diferentes
 * métodos que ofrece esta clase son para establecer con que tipo de {@code JOIN}
 * se hará la union, los tipos que se pueden usar están definidos por las constantes
 * estáticas de esta clase pero no es posible usarlos para definir el tipo de {@code JOIN}
 * si no que esto se debe hacer mediante la llamada de la función adecuada, sus nombres
 * indican el tipo que se usará.
 * <p>
 * Los {@code JOIN} anidados se pueden hacer mediante llamadas sucesivas a los métodos
 * estos se irán concatenando a medida que se los llama. <strong> No se revisa si es correcta
 * o no la union que se esta armando </strong> de existir error este será reportado
 * cuando se quiera usar la sentencia contra un motor.
 * <p>
 * Para cada tipo de {@code JOIN} (definido por el nombre de la función) existen
 * tres funciones del mismo nombre que aceptan distintos parámetros:
 * <ul>
 * 	<li>La que acepta el nombre de la tabla sobre la que se quiere hacer {@code JOIN}
 *  y la condición para la unión </li>
 *  <li>La que acepta el nombre de la tabla, un alias para la tabla y la condición
 *  para la unión con {@code JOIN}. Para los tipos {@code LEFT JOIN} y {@code LEFT OUTER JOIN}
 *  es posible indicar si usara el elemento {@code NATURAL} mediante el primer parámetro </li>
 *  <li>La que acepta una sentencia select como fuente, un alias para la tabla resultante de la consulta y la condición
 *  para la unión con {@code JOIN}. Para los tipos {@code LEFT JOIN} y {@code LEFT OUTER JOIN}
 *  es posible indicar si usara el elemento {@code NATURAL} mediante el primer parámetro </li>
 * </ul>    
 * @author Travis Reeder - travis@spaceprogram.com - Copyright (c) 2002 Space Program Inc.
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.2
 */
public class Join {
	
	
	public static final String JOIN = "JOIN";
	public static final String LEFT_JOIN = "LEFT JOIN";
	public static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
	public static final String INNER_JOIN = "INNER JOIN";
	public static final String CROSS_JOIN = "CROSS_JOIN";
	public static final String NATURAL = "NATURAL";
	
	private String joinAsString;
	//int type;
	
	//WhereClause wclause = new WhereClause();
	
	public Join(){
		joinAsString = "";
		//this.type = 1;//join_type;
		//this.table_name = table_name;
	}
	
	private void join(boolean isNatural, String tableName, String tableAlias, String joinOption, String joinCondition){
		String as;
		if (tableAlias.equals("")) as = "";
		else as  = "AS " + tableAlias + " ";
		if (isNatural) joinAsString += " " + NATURAL; 
		joinAsString += " " + joinOption + " " + tableName + " "+ as;
		if (!joinCondition.equals(""))	joinAsString +=	"ON " + joinCondition;
	}
	
	private void join(boolean isNatural, SelectQuery selectSource, String selectAlias, String joinOption, String joinCondition){
		String as;
		if (selectAlias.equals("")) as = "";
		else as  = "AS " + selectAlias + " ";
		if (isNatural) joinAsString += " " + NATURAL;
		joinAsString += " " + joinOption + " (" + selectSource.toString()+ ") " + as;
		if (!joinCondition.equals(""))	joinAsString +=	"ON " + joinCondition;
	}
	
	/**
	 * Agrega un elemento {@code JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase
	 * @param tableName El nombre de la tabla sobre la que se quiere hacer {@code JOIN}.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void join(String tableName, String joinCondition) {
		join(false, tableName, "", JOIN, joinCondition);
	}
	
	/**
	 * Agrega un elemento {@code JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase.
	 * @param tableName El nombre de la tabla sobre la que se quiere hacer {@code JOIN}.
	 * @param alias El alias que se le quiere asignar a la tabla.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void join(String tableName, String alias, String joinCondition) {
		join(false, tableName, alias, JOIN, joinCondition);
	}
	
	/**
	 * Agrega un elemento {@code JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase.
	 * @param selectSource El nombre de la sentencia {@code SELECT} sobre la que se quiere hacer {@code JOIN}.
	 * @param alias El alias que se le quiere asignar a la tabla resultado de la sentencia {@code SELECT}.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void join(SelectQuery selectSource, String alias, String joinCondition) {
		join(false, selectSource, alias, JOIN, joinCondition);
	}
	
	
	/**
	 * Agrega un elemento {@code LEFT JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase.
	 * @param tableName El nombre de la tabla sobre la que se quiere hacer {@code JOIN}.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void leftJoin(String tableName, String joinCondition) {
		join(false, tableName, "", LEFT_JOIN, joinCondition);
	}
	
	/**
	 * Agrega un elemento {@code LEFT JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase.
	 * @param isNatural Indica si se debe agregar el parámetro {@code NATURAL}.
	 * @param tableName El nombre de la tabla sobre la que se quiere hacer {@code JOIN}.
	 * @param alias El alias que se le quiere asignar a la tabla.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void leftJoin(boolean isNatural, String tableName, String alias, String joinCondition) {
		join(isNatural, tableName, alias, LEFT_JOIN, joinCondition);
	}
	
	/**
	 * Agrega un elemento {@code LEFT JOIN} a la cláusula FROM de la sentencia select
	 * a la que pertenece esta clase.
	 * @param isNatural Indica si se debe agregar el parámetro {@code NATURAL}.
	 * @param selectSource El nombre de la sentencia {@code SELECT} sobre la que se quiere hacer {@code JOIN}.
	 * @param alias El alias que se le quiere asignar a la tabla resultado de la sentencia {@code SELECT}.
	 * @param joinCondition La condición  textual que se usa para realizar la union.
	 */
	public void leftJoin(boolean isNatural, SelectQuery selectSource, String alias, String joinCondition) {
		join(isNatural, selectSource, alias, LEFT_JOIN, joinCondition);
	}
	
	
	
	public void leftOuterJoin(String tableName, String joinCondition) {
		join(false, tableName, "", LEFT_OUTER_JOIN, joinCondition);
	}
	
	public void leftOuterJoin(boolean isNatural, String tableName, String alias, String joinCondition) {
		join(isNatural, tableName, alias, LEFT_OUTER_JOIN, joinCondition);
	}
	
	public void leftOuterJoin(boolean isNatural, SelectQuery selectSource, String alias, String joinCondition) {
		join(isNatural, selectSource, alias, LEFT_OUTER_JOIN, joinCondition);
	}
	
	
	public void innerJoin(String tableName, String joinCondition) {
		join(false, tableName, "", INNER_JOIN, joinCondition);
	}
	
	public void innerJoin(String tableName, String alias, String joinCondition) {
		join(false, tableName, alias, INNER_JOIN, joinCondition);
	}
	
	public void innerJoin(SelectQuery selectSource, String alias, String joinCondition) {
		join(false, selectSource, alias, INNER_JOIN, joinCondition);
	}
	
	
	public void crossJoin(String tableName, String joinCondition) {
		join(false, tableName, "", CROSS_JOIN, joinCondition);
	}
	
	public void crossJoin(String tableName, String alias, String joinCondition) {
		join(false, tableName, alias, CROSS_JOIN, joinCondition);
	}
	
	public void crossJoin(SelectQuery selectSource, String alias, String joinCondition) {
		join(false, selectSource, alias, CROSS_JOIN, joinCondition);
	}
	
	/**
	 * Devuelve la restricción JOIN que forma parte de el parámetro FROM de la sentencia
	 * select a la que pertenece esta clase.
	 */
	public String toString(){
		return joinAsString;
	}

}
