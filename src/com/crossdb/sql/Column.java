/**

Generic column class.

Note: This was taken from DataField.

 * <p>Title: crossdb - Column</p>
 * <p>Description: Represents a column in a table</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * 
 	<p>Revision 1: add primary keys and foreign keys support</p>
  	@author Jorge P&eacute;rez Burgos
	@author jorge.perez@adaptia.net
	@author http://www.adaptia.net
	@author	(C) 2003
	@version 0.2
 */
 
package com.crossdb.sql;

import java.sql.ResultSetMetaData;

public class Column extends Object {

	String name = null;
	int is_nullable = ResultSetMetaData.columnNullable; // equivalent to java.sql.ResultSetMetaData nullable fields
	int type = 0; // maps to java.sql.Types
	int size = 50; // default 50
	int columnIndex = 0;
	private boolean isPrimaryKey = false;
	private boolean isForeignKey = false;
	// If a column is foreign key we need the the foreign table and the foreign primary key
	private String foreignTable = null;
	private String foreignPrimaryKey = null;
	
	// auto inc variables
	boolean auto_increment = false;
	int start_with =1;
	int increment_by = 1;
	String sequence = null;
	
	String col_default= null;

	public Column(int columnIndex){
		this.columnIndex = columnIndex;
	}

	public Column(String n){
		name = n;
	}
	/**
	 Takes a name and a type referenced from java.sql.Types
	 */
	public Column(String n, int type){
		name = n;
		this.type = type;
	}

	
	
	public String getName(){
		return name;
	}
	/**
	 * Returns 1 if yes, 0 if no, or -1 if unsure. same as in java.sql.ResultSetMetaData
	 */
	public int isNullable(){
		return is_nullable;
	}
	/**
	 * returns the java.sql.Types equivalent.
	 */
	public int getType(){
		return type;
	}
	
	public boolean isPrimaryKey(){
		return isPrimaryKey;
	}
	
	public boolean isForeignKey(){
		return isForeignKey;
	}
	
	public boolean isAutoIncrement(){
		return auto_increment;
	}
	
	public void setAutoIncrement(boolean b){
		auto_increment = b;
	}
	
	public void setStartWith(int sw){
		start_with = sw;
	}
	public int getStartWith(){
		return start_with;
	}
	public void setIncrementBy(int ib){
		increment_by = ib;
	}
	public int getIncrementBy(){
		return increment_by;
	}
	/**
	 * Sets the sequence used for this column if it's an auto increment column.
	 */
	public void setSequence(String sequence_name){
		sequence = sequence_name;
	}
	public String getSequence(){
		return sequence;
	}
	
	
	
	public String getDefaultValue(){
		return col_default;
	}
	/**
	For varchars size
	 */
	public int getSize(){
		return size;
	}
	public void setSize(int s){
		size = s;
	}
	public void setName(String n){
		name = n;
	}
	
	public void setPrimaryKey(boolean b){
		isPrimaryKey = b;
	}
	
	public void setForeignKey(boolean b){
		isForeignKey = b;
	}
	
	/**
		Takes an int because ResultSetMetaData returns an int on this one.
	
		From java.sql.ResultSetMetaData in java API
		Returns:
		the nullability status of the given column; one of columnNoNulls, columnNullable or columnNullableUnknown
	 */
	public void setNullable(int n){
		is_nullable = n;
	}
	public void setType(int t){
		type = t;
	}
	public void setType(int t, int size){
		type = t;
		this.size = size;
	}

	public void setDefaultValue(String s){
		col_default = s;
	}
	
	/**
	 * @return String
	 */
	public String getForeignPrimaryKey() {
		return foreignPrimaryKey;
	}

	/**
	 * @return String
	 */
	public String getForeignTable() {
		return foreignTable;
	}

	/**
	 * Sets the foreignPrimaryKey.
	 * @param foreignPrimaryKey The foreignPrimaryKey to set
	 */
	public void setForeignPrimaryKey(String foreignPrimaryKey) {
		this.foreignPrimaryKey = foreignPrimaryKey;
	}

	/**
	 * Sets the foreignTable.
	 * @param foreignTable The foreignTable to set
	 */
	public void setForeignTable(String foreignTable) {
		this.foreignTable = foreignTable;
	}

}
