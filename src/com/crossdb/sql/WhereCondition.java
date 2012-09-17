package com.crossdb.sql;

/**
 * Es la representación de las condiciones <code>x = y </code> que 
 * se usan dentro de una cláusula WHERE. La clase puede contener los nombres de las
 * tablas a comparar, si es que se comparan valores de mas de una tabla, los nombres
 * de los campos a comparar y de haber algún valor contra el que comparar.
 * <p>
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 */
@Deprecated
public class WhereCondition {

	public static final int LESS_THAN = 20;
	public static final int LESS_THAN_OR_EQUAL_TO = 50;
	public static final int EQUAL_TO = 100; // ex: WHERE a = b or a = 'b' or 'a'
											// = 'b'
	public static final int GREATER_THAN_OR_EQUAL_TO = 150;
	public static final int GREATER_THAN = 200;

	public static final int NOT_EQUAL_TO = 4;
	public static final int LIKE = 5; // ex: WHERE a LIKE '%x%' or 'x%' or '%x'
										// or 'x%x' ???
	public static final int IN = 6; // list ex: WHERE a IN (x, y, z)
	public static final int BETWEEN = 7;

	public static final int NOT_NULL = 11;
	public static final int IS_NULL = 12;

	// public static final int

	/**
	 * El nombre de la primer tabla a comparar se la llama internamente tabla x
	 */
	private String xTable;
	/**
	 * El campo x a ser comparado, se le asigna el tipo {@link Object} pues puede
	 * albergar nombre de la columna, otra {@link WhereCondition} o bien un valor.  
	 */
	private Object x;
	
	private int comparison;
	/**
	 * El nombre de la segunda tabla a comparar se la llama internamente tabla y
	 */
	private String yTable;
	/**
	 * El campo y a ser comparado, se le asigna el tipo {@link Object} pues puede
	 * albergar nombre de la columna, otra {@link WhereCondition} o bien un valor.
	 */
	private Object y;
	
	// TODO opciones repetidas en los constructores
	/**
	 * Constructor base acepta cualquier
	 */
	public WhereCondition(Object x, int comparison, Object y) {
		this.x = x;
		this.comparison = comparison;
		this.y = y;
	}

	public WhereCondition(Object x, int comparison, int y) {
		this.x = x;
		this.comparison = comparison;
		this.y = new Integer(y);
	
	}

	public WhereCondition(String x, int comparison, int y) {
		this.x = x;
		this.comparison = comparison;
		this.y = new Integer(y);
	}

	public WhereCondition(String x, int comparison, String y) {
		this.x = x;
		this.comparison = comparison;
		this.y = y;

	}

	/**
	 * With comparison not relating to table
	 */
	public WhereCondition(String x_table, Object x, int comparison, Object y) {
		this.xTable = x_table;
		this.x = x;
		this.comparison = comparison;
		// this.y_table = y_table;
		this.y = y;
	}

	/**
	 * Comparing different columns from different tables.
	 */
	public WhereCondition(String x_table, Object x, int comparison,
			String y_table, Object y) {
		this.xTable = x_table;
		this.x = x;
		this.comparison = comparison;
		this.yTable = y_table;
		this.y = y;
	}

	public Object getPre() {
		return x;
	}

	public String getPreTable() {
		return xTable;
	}

	/**
	 * @deprecated use getPost()
	 * @return y
	 */
	public Object getPred() {
		return y;
	}

	/**
	 * @deprecated use getPostTable instead
	 * @return y_table
	 */
	public String getPredTable() {
		return yTable;
	}
	/**
	 * 
	 * @return yTable
	 */
	public String getPostTable() {
		return yTable;
	}
	/**
	 * 
	 * @return y
	 */
	public Object getPost() {
		return y;
	}
	
	@Deprecated
	public int getOperator() {
		return comparison;
	}

	public int getComparison() {
		return comparison;
	}

}
