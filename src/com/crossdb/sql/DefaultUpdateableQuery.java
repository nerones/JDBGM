package com.crossdb.sql;

import java.math.BigDecimal;
import java.util.Vector;
import java.sql.Types;

// TODO: Would be nice to be able to setup a query like this then just call doUpdate or doInsert to do either? Or getInsertQuery or getUpdateQuery from UpdateableQuery
// TODO: Revisar si son innecesarias las interfaces y quedarse solo con las clases abstractas.
/**
 * Implementación base, ninguna otra clase debiera implementar {@link UpdateableQuery}
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.3
 * 
 */
public abstract class DefaultUpdateableQuery implements UpdateableQuery {
	
	/**
	 * Las columnas que intervienen en la sentencia.
	 */
	protected Vector<Column> columns; // SELECT columns

	public DefaultUpdateableQuery() {
		columns = new Vector<Column>();

	}

	// protected int no_alter_values = 0; // counts the number of no alter
	// values

	public void addColumn(String column, String value) {
		Column c = new Column(column, Types.CHAR, value);
		columns.add(c);
		// values.add(value);
	}

	public void addColumn(String column, float value) {
		Column c = new Column(column, Types.FLOAT, new Float(value));
		columns.add(c);
		// values.add(new Float(value));
	}

	public void addColumn(String column, double value) {
		Column c = new Column(column, Types.DOUBLE, new Double(value));
		columns.add(c);
		// values.add(new Double(value));
	}

	public void addColumn(String column, int value) {
		Column c = new Column(column, Types.INTEGER, new Integer(value));
		columns.add(c);
		// values.add(new Integer(value));
	}

	public void addColumn(String column, java.util.Date value) {
		Column c = new Column(column, Types.DATE, value);
		columns.add(c);
		// values.add(value);
	}

	public void addColumn(String column, boolean value) {
		columns.add(new Column(column, Types.BOOLEAN, new Boolean(value)));
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
	}


	public void appendColumns(Vector<Column> columns) {
		this.columns.addAll(columns);

	}

	public Vector<Column> getColumns() {
		return columns;
	}
	
	public abstract String toString();

}
