
package com.crossdb.sql;

/**
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz 2011
 * Date: Jun 27, 2002
 * Time: 8:55:57 PM
 * 
 */
public abstract class DefaultInsertQuery extends InsertQuery{

	protected String table;

	protected boolean return_id = false;
	
	protected boolean isFromDefault = false;
	
	protected SelectQuery select = null;

	public DefaultInsertQuery() {

		super();

	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addAutoIncrementColumn(String column) {
		ColumnValue c = new ColumnValue(column, null);
		c.setAutoIncrement(true);
		columns.add(c);
		// values.add(null);
	}

	public void addAutoIncrementColumn(String column, String sequence) {
		ColumnValue c = new ColumnValue(column, null);
		c.setAutoIncrement(true);
		c.setSequence(sequence);
		columns.add(c);
		// values.add(null);
	}

	public void returnID(boolean b) {
		return_id = b;
	}
	
	public void setFromDefault(boolean isFromDefault){
		this.isFromDefault = isFromDefault;
	}
	public boolean isFromDefault(){
		return isFromDefault;
	}
	
	public SelectQuery getSelectStmt(){
		return select;
	}
	
	public void setSelectStmt(SelectQuery select){
		this.select = select;
	}

	public abstract String toString();
	

}
