package com.crossdb.sql;

/**
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz 2011 Date: Jun 27, 2002 Time: 8:55:57 PM
 * @version 0.2
 */
public abstract class DefaultInsertQuery extends InsertQuery {
	// contemplado casi todos los casos exepto por un par de opciones de sqlite
	protected String table;

	protected boolean return_id;
	protected boolean isFromDefaultValues;
	protected boolean isFromSelect;
	protected boolean isFromValues;

	protected SelectQuery select = null;

	public DefaultInsertQuery() {
		super();
		return_id = false;
		isFromDefaultValues = false;
		isFromSelect = false;
		isFromValues = true;

	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addAutoIncrementColumn(String column) {
		Column c = new Column(column, null);
		c.setAutoIncrementPK(true);
		columns.add(c);
		// values.add(null);
	}

	public void addAutoIncrementColumn(String column, String sequence) {
		Column c = new Column(column, null);
		c.setAutoIncrementPK(true);
		//TODO revisar setsequence
		//c.setSequence(sequence);
		columns.add(c);
		// values.add(null);
	}

	public void returnID(boolean b) {
		return_id = b;
	}

	public void setFromDefault(boolean isFromDefault) {
		if (isFromDefault) {
			isFromSelect = false;
			isFromValues = false;
		}
		this.isFromDefaultValues = isFromDefault;
	}

	public boolean isFromDefault() {
		return isFromDefaultValues;
	}

	public SelectQuery getSelectStmt() {
		return select;
	}

	public boolean isFromSelect() {
		return isFromSelect;
	}

	public void setSelectStmt(SelectQuery select) {
		isFromSelect = true;
		isFromDefaultValues = false;
		isFromValues = false;
		this.select = select;
	}
	
	public void setFromValues(boolean isFromValues){
		isFromSelect = false;
		isFromDefaultValues = false;
		this.isFromValues = true;
	}

	public abstract String toString();

}
