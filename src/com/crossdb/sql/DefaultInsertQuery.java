
package com.crossdb.sql;

/**
 * Se puede configurar la sentencia INSERT con 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz 2011
 * Date: Jun 27, 2002
 * Time: 8:55:57 PM
 * 
 */
public abstract class DefaultInsertQuery extends InsertQuery{

	protected String table;

	protected boolean return_id = false;
	
	protected boolean isFromDefaultValues = false;
	protected boolean isFromSelect = false;
	protected boolean isFromValues = true;
	
	protected SelectQuery select = null;

	public DefaultInsertQuery() {

		super();

	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addAutoIncrementColumn(String column) {
		Column c = new Column(column, null);
		c.setAutoIncrement(true);
		columns.add(c);
		// values.add(null);
	}

	public void addAutoIncrementColumn(String column, String sequence) {
		Column c = new Column(column, null);
		c.setAutoIncrement(true);
		c.setSequence(sequence);
		columns.add(c);
		// values.add(null);
	}

	public void returnID(boolean b) {
		return_id = b;
	}
	
	public void setFromDefault(boolean isFromDefault){
		if (isFromDefault) {
			isFromSelect = false;
			isFromValues = false;
		}
		this.isFromDefaultValues = isFromDefault;
	}
	public boolean isFromDefault(){
		return isFromDefaultValues;
	}
	
	public SelectQuery getSelectStmt(){
		return select;
	}
	
	public boolean isFromSelect(){
		return isFromSelect;
	}
	
	public void setSelectStmt(SelectQuery select){
		isFromSelect = true;
		isFromDefaultValues = false;
		isFromValues = false;
		this.select = select;
	}

	public abstract String toString();
	

}
