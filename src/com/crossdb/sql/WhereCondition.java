package com.crossdb.sql;

/**

The WhereCondition x = y AND (x=z OR x=b)
<p>
/**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

public class WhereCondition {


	public static final int LESS_THAN = 20;
	public static final int LESS_THAN_OR_EQUAL_TO = 50;
	public static final int EQUAL_TO = 100; // ex: WHERE a = b or a = 'b' or 'a' = 'b'
	public static final int GREATER_THAN_OR_EQUAL_TO = 150;
	public static final int GREATER_THAN = 200;

	public static final int NOT_EQUAL_TO = 4;
	public static final int LIKE = 5; // ex: WHERE a LIKE '%x%' or 'x%' or '%x' or 'x%x' ???
	public static final int IN = 6; // list ex: WHERE a IN (x, y, z)
	public static final int BETWEEN = 7;

	public static final int NOT_NULL = 11;
	public static final int IS_NULL = 12;

	//public static final int

	/**
	 * Table for x comparator
	 */
	String x_table;
	Object x;
	int comparison;
	/**
	 * Table for y comparator
	 */
	String y_table;
	Object y;


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
		this.x_table = x_table;
		this.x = x;
		this.comparison = comparison;
		//this.y_table = y_table;
		this.y = y;
	}

	/**
	 * Comparing different columns from different tables.
	 */
	public WhereCondition(String x_table, Object x, int comparison, String y_table, Object y) {
		this.x_table = x_table;
		this.x = x;
		this.comparison = comparison;
		this.y_table = y_table;
		this.y = y;
	}

	public Object getPre() {
		return x;
	}

	public String getPreTable() {
		return x_table;
	}

    /**
     * @deprecated use getPost()
     * @return
     */
	public Object getPred() {
		return y;
	}

    /**
     * @deprecated use getPostTable instead
     * @return
     */
	public String getPredTable() {
		return y_table;
	}
    public String getPostTable() {
		return y_table;
	}

	public Object getPost() {
		return y;
	}

	public int getOperator() {
		return comparison;
	}

	public int getComparison() {
		return comparison;
	}


}

