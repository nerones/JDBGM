package com.crossdb.sql;

/**
 * Representa una cláusula WHERE como por ejemplo WHERE x = y, la cláusula where
 * esta formada por diferentes condiciones y cláusulas {@link WhereClause} anidadas 
 * como por ejemplo WHERE (x = y AND (x=z OR x=b)). Todas las funciones que ofrece
 * esta clase son para ir construyendo las diferentes condiciones de la cláusula, todas
 * ellas vienen con el prefijo "and" u "or" esto es para definir el modo en que se
 * irán concatenando las diferentes condiciones, la unica que ves que se ignora
 * el prefijo es cuando se esta agregando la primera condición a la cláusula.
 * <p>
 * Si se quiere agregar los nombre de las tablas como por ejemplo "tabla.columna = 1"
 * se debe agregar como un solo elemento o sea "tabla.columna" se debe ingresar como
 * nombre de columna sobre la cual se quiere establecer una condición, la clase
 * no hace ningún tipo de control sobre la existencia de la tabla o la correctitud del
 * nombre de las columnas, estos errores se veran cuando se este ejecutando la sentencia
 * sobre el motor.
 * <p>
 * Entonces cada conjunto de paréntesis puede ser visto como una cláusula where,
 * y básicamente podemos ver a WhereClause como una lista de condiciones y/o
 * cláusulas where.
 * </p>
 * @author Travis Reeder - travis@spaceprogram.com Copyright (c) 2002 - Company: Space Program Inc.
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 */
public class WhereClause {

	public static final String LESS_THAN = "<";
	public static final String LESS_THAN_OR_EQUAL_TO = "<=";
	public static final String EQUAL_TO = "="; // ex: WHERE a = b or a = 'b' or 'a'
											// = 'b'
	public static final String GREATER_THAN_OR_EQUAL_TO = ">=";
	public static final String GREATER_THAN = ">";

	public static final String NOT_EQUAL_TO = "<>";
	public static final String LIKE = "LIKE"; // ex: WHERE a LIKE '%x%' or 'x%' or '%x'
	public static final String NOT_LIKE = "NOT LIKE";
										// or 'x%x' ???
	public static final String IN = "IN"; // list ex: WHERE a IN (x, y, z)
	public static final String NOT_IN = "NOT IN"; // list ex: WHERE a IN (x, y, z)
	public static final String BETWEEN = "BETWEEN";

	public static final String NOT_NULL = "IS NOT NULL";
	public static final String IS_NULL = "IS NULL";
	
	String whereClauseString = null;

	/**
	 * For formatting dates in toString
	 * 
	 * @see #toString()
	 */
	Formatter formatter;

	public WhereClause(Formatter formatter) {
		whereClauseString = "";
		this.formatter = formatter;

	}
	
	private void addCondition(String and_or, String column, String operator, Object value){
		
		if (whereClauseString != "") whereClauseString += " " + and_or + " ";
		
		whereClauseString += column + " " + operator + " " + getValueAsString(value);
	}
	
	private void addConditionNoAlter(String and_or, String column, String operator, Object value){
		
		if (whereClauseString != "") whereClauseString += " " + and_or + " ";
		
		whereClauseString += column + " " + operator + " " + value;
	}
	
	private void addCondition(String and_or, WhereClause innerClause){
		if (whereClauseString != "") whereClauseString += " " + and_or + " ";
		whereClauseString += innerClause.getConditionsString();
	}

	/**
	 * @param value
	 * @return
	 */
	private String getValueAsString(Object value) {
		//TODO mover al formateado
		String valueAsString = "";
		if (value instanceof java.util.Date) {

			valueAsString += "'" + formatter.format((java.util.Date) value) + "'";
		} else if (value instanceof String){
			valueAsString += "'" + value + "'" ;
		} else
			// TODO value se convierte a string pero puede ser cualquier objeto.
			valueAsString += value ;
		
		return valueAsString;
	}
	
	private String escapeArrayAsString(Object[] value){
		//TODO mover al formateador
		String valueAsString = "(";
		for (int i = 0; i < value.length-1 ; i++) {
			if (value[i] instanceof String)
				valueAsString += "'" + value[i] + "', ";
			else valueAsString += value[i] + ", ";
		}
		if (value[value.length-1] instanceof String)
			valueAsString += "'" + value[value.length-1] + "')";
		else valueAsString += value[value.length-1] + ")";
		return valueAsString;
	}
	
	/**
	 * Genera una cláusula where con la cadena que se le pasa como parámetro, la cadena
	 * debe ser la condición de la cláusula sin la palabra reservada "WHERE". No se
	 * comprueba que haya errores por lo que de existir alguno este será reportado
	 * cuando se quiera ejecutar la sentencia sobre el motor.
	 * 
	 * @param condition expresa explícitamente la condición de la cláusula WHERE.
	 */
	public void explicitWhere(String condition){
		whereClauseString += condition;
	}

	/**
	 * Agrega a la cláusula where una condición para crear una condición compleja
	 * del modo {@code "( a=x or (c=d and e=f))"}  donde {@link WhereClause} representa
	 * la condición entre paréntesis dentro del paréntesis principal. Si se lo llama varias
	 * veces se concatenara con el operador "AND" con cualquier condición anterior.
	 * 
	 * @param innerClause La cláusula compuesta a agregar.
	 */
	public void andNestClause(WhereClause innerClause){
		
		addCondition("AND", innerClause);
	}
	
	/**
	 * Lo mismo que {@link #andNestClause(WhereClause)} pero se concatenan las
	 * condiciones con el operador "OR".
	 * @see #andNestClause(WhereClause)
	 */
	public void orNestClause(WhereClause innerClause){
		
		addCondition("OR", innerClause);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con un operador de comparación como "key = value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND". Las operadores de comparación son "=,<>,>,>=,<,<=" y solo pueden ser estos si se le pasa, cualquier
	 * otro se producira un error de tiempo de ejecución.
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value El valor a comparar con el parámetro key, pudiendo ser el nombre de otra columna.
	 * @param comparisonOperator El operador que indica que tipo de comparación se realiza en la cláusula.
	 */
	public void andComparison(String key, String comparisonOperator, Object value){
		validateOperator(comparisonOperator);
		addCondition("AND", key, comparisonOperator, value);
	}
	
	/**
	*Lo mismo que {@link #andComparison(String, String, Object)} pero las sucesivas llamadas
	*a el método producirá concatenaciones con el operador lógico "OR".
	*
	*/
	public void orComparison(String key, String comparisonOperator, Object value){
		validateOperator(comparisonOperator);
		addCondition("OR", key, comparisonOperator, value);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key = value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value El valor a comparar con el parámetro key, pudiendo ser el nombre de otra columna.
	 */
	public void andEquals(String key, Object value) {
		addCondition("AND", key, EQUAL_TO, value);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key = value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "OR".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value El valor a comparar con el parámetro key, pudiendo ser el nombre de otra columna.
	 */
	public void orEquals(String key, Object value){
		addCondition("OR", key, EQUAL_TO, value);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IN (value,value2)"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND". EL parámetro value debe ser una lista separada por comas  como cadena de caracteres
	 *  de la forma "a,b,c"
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value La lista de elementos donde se debe buscar, debe ser un array de objetos.
	 */
	public void andIn(String key, Object[] value) {
		
		addConditionNoAlter("AND", key, IN, escapeArrayAsString(value));
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IN value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "OR". EL parámetro value debe ser una lista separada por comas  como cadena de caracteres
	 *  de la forma "a,b,c"
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value La lista de elementos donde se debe buscar, debe ser un array de objetos.
	 */
	public void orIn(String key, Object[] value) {
		addConditionNoAlter("OR", key, IN, escapeArrayAsString(value));
	}
	
	/**
	 * Lo mismo que {@link #andIn(String, Object[])} pero la negación lógica del mismo "NOT IN"
	 * @see WhereClause#andIn(String, Object[])
	 */
	public void andNotIn(String key, Object[] value) {
		addConditionNoAlter("AND", key, NOT_IN, escapeArrayAsString(value));
	}
	
	/**
	 * Lo mismo que {@link #orIn(String, Object[])} pero la negación lógica del mismo "NOT IN"
	 * @see WhereClause#orIn(String, Object[])
	 */
	public void orNotIn(String key, Object[] value) {
		addConditionNoAlter("OR", key, NOT_IN, escapeArrayAsString(value));
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key LIKE value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND". Para el parámetro value se debe pasar explícitamente el comodín "%", por ejemplo
	 * si value = '%uno' la condición será "key LIKE '%uno'", si value = 'uno' la condición sera
	 * "key LIKE 'uno'"
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value La cadena que interviene en la condición incluido el comodín "%".
	 */
	public void andLike(String key, String value){
		addCondition("AND", key, LIKE, value);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key LIKE value"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "OR". Para el parámetro value se debe pasar explícitamente el comodín "%", por ejemplo
	 * si value = '%uno' la condición será "key LIKE '%uno'", si value = 'uno' la condición sera
	 * "key LIKE 'uno'"
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 * @param value La cadena que interviene en la condición incluido el comodín "%".
	 */
	public void orLike(String key, String value){
		addCondition("AND", key, LIKE, value);
	}
	
	/**
	 * Igual que {@link #andLike(String, String)} pero con la condición "NOT LIKE"
	 * 
	 */
	public void andNotLike(String key, String value){
		addCondition("AND", key, NOT_LIKE, value);
	}
	
	/**
	 * Igual que {@link #orLike(String, String)} pero con la condición "NOT LIKE"
	 * 
	 */
	public void orNotLike(String key, String value){
		addCondition("OR", key, NOT_LIKE, value);
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IS NOT NULL"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 */
	public void andNotNull(String key){
		addConditionNoAlter("AND", key, NOT_NULL, "");
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IS NOT NULL"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "OR".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 */
	public void orNotNull(String key){
		addConditionNoAlter("OR", key, NOT_NULL, "");
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IS NULL"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "AND".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 */
	public void andNull(String key){
		addConditionNoAlter("AND", key, IS_NULL, "");
	}
	
	/**
	 * Genera la cláusula WHERE para alguna sentencia con el operador "key IS NULL"
	 * si se lo llama varias veces, las condiciones se concatenaran con el operador
	 * "OR".
	 * 
	 * @param key El nombre de la columna que interviene en la condición.
	 */
	public void orNull(String key){
		addConditionNoAlter("OR", key, IS_NULL, "");
	}
	
	/**
	 * and key BETWEEN value1 AND value2
	 * @param key
	 * @param value1
	 * @param value2
	 */
	public void andBetween(String key, int value1, int value2){
		addConditionNoAlter("AND", key, BETWEEN, value1 + " AND " + value2);
	}
	
	public void orBetween(String key, int value1, int value2){
		addConditionNoAlter("OR", key, BETWEEN, value1 + " AND " + value2);
	}


	/**
	 * @return
	 */
	private String getConditionsString() {
		return "(" + whereClauseString + ")";
	}
	
	public String toString() {
		
		return " WHERE (" + whereClauseString + ")";
	}

	/**
	 * gets operator string
	 */
	private boolean validateOperator(String operator) {
		
		if (operator.equals(EQUAL_TO)) return true;
		else if (operator.equals(NOT_EQUAL_TO)) return true;
		else if (operator.equals(LESS_THAN)) return true;
		else if (operator.equals(LESS_THAN_OR_EQUAL_TO)) return true;
		else if (operator.equals(GREATER_THAN)) return true;
		else if (operator.equals(GREATER_THAN_OR_EQUAL_TO)) return true;
		
		else throw new RuntimeException("Invalid value of constant to parse operator value in WhereClause");
		
	}

}
