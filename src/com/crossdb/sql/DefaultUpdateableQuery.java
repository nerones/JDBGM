package com.crossdb.sql;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Queries that you set column values, Insert and Update, should extend this
 * 
 * TODO: Would be nice to be able to setup a query like this then just call
 * doUpdate or doInsert to do either? Or getInsertQuery or getUpdateQuery from
 * UpdateableQuery
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1 Date: Sep 4, 2002 Time: 12:21:44 AM
 * 
 */
public class DefaultUpdateableQuery implements UpdateableQuery {
	protected ArrayList<Column> columns; // SELECT columns

	// protected List values;

	public DefaultUpdateableQuery() {

		columns = new ArrayList<Column>();

	}

	// protected int no_alter_values = 0; // counts the number of no alter
	// values

	public void addColumn(String column, String value) {
		Column c = new Column(column, value);
		columns.add(c);
		// values.add(value);
	}

	public void addColumn(String column, float value) {
		Column c = new Column(column, new Float(value));
		columns.add(c);
		// values.add(new Float(value));
	}

	public void addColumn(String column, double value) {
		Column c = new Column(column, new Double(value));
		columns.add(c);
		// values.add(new Double(value));
	}

	public void addColumn(String column, int value) {
		Column c = new Column(column, new Integer(value));
		columns.add(c);
		// values.add(new Integer(value));
	}

	public void addColumn(String column, java.util.Date value) {
		Column c = new Column(column, value);
		columns.add(c);
		// values.add(value);
	}

	public void addColumn(String column, boolean value) {
		columns.add(new Column(column, new Boolean(value)));
		// values.add(new Boolean(value));
	}

	public void addColumn(String column, BigDecimal value) {
		columns.add(new Column(column, value));
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public void addColumnNoAlter(String column, String value) {
		Column c = new Column(column, value);
		c.setNoAlter(true);
		columns.add(c);

		// values.add(0,value);
		// no_alter_values++;

	}

	/**
	 * Add the list of columns to the current list in the query.
	 */
	public void appendColumns(ArrayList<Column> columns) {
		this.columns.addAll(columns);

	}

	/**
	 * 
	 * @return List of Column objects
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}

}
