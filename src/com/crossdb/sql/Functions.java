package com.crossdb.sql;

/**
 * EL soporte para funciones es básico, la única herramienta que se da son unas
 * cuantas constantes que definen las funciones mas comunes y que son equivalentes
 * entre los diferentes motores, el uso de algunas funciones no asegura la independencia
 * del código escrito, si se usan funciones definidas por el usuario estas se pueden declarar
 * como una columna mas en la sentencia select.
 * La mayoría de las constantes aquí declaradas son reconocidas por cualquiera
 * de los DBMS a los que se le esta dando soporte, de existir una función equivalente
 * pero con distinto nombre esta se debe declarar en una subclase de esta misma clase
 * que será especifica para un DBMS en particular.
 * 
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.5
 */
// TODO ver si es mejor crear una clase abstracta 
public  abstract class Functions {
	
	public static final String ABS = "ABS";
	public static final String COALESCE = "COALESCE";
	public static final String IFNULL = "IFNULL";
	public static final String LENGTH = "LENGTH";
	public static final String LIKE = "LIKE";
	public static final String LOWER = "LOWER";
	public static final String LTRIM = "LTRIM";
	public static final String NULLIF = "NULLIF";
	public static final String QUOTE = "QUOTE";
	public static final String REPLACE = "REPLACE";
	public static final String ROUND = "ROUND";
	public static final String RTRIM = "RTRIM";
	public static final String SUBSTR = "SUBSTR";
	public static final String TRIM = "TRIM";
	public static final String UPPER = "UPPER";
	
	
	public static final String AVG = "AVG";
	public static final String COUNT = "COUNT";
	public static final String GROUP_CONCAT = "GROUP_CONCAT";
	public static final String SUM = "SUM";
	public static final String MAX = "MAX";
	public static final String MIN = "MIN";
	
	
	public static final String DATE = "DATE";
	public static final String DATETIME = "DATETIME";
	public static final String TIME = "TIME";

}
