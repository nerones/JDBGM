/*

Represents a table in a database.

So holds a list of columns.
/**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */
package com.crossdb.sql;

import java.util.List;

public class Table {

	String name;
//	String display_name;
	
java.util.List columns = new java.util.ArrayList();
	
	
	public Table(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	public void addColumn(Column c){
		columns.add(c);
	}
	public List getColumns(){
		return columns;
	}
	public Column getColumn(int i){
		return (Column)columns.get(i);
	}
	public Column getColumn(String s){
		Column ret = null;
		for(int i = 0; i < columns.size(); i++){
			Column col = (Column)(columns.get(i));
			if(col.getName().equalsIgnoreCase(s)){
				ret = col;
				break;
			}

		}
		return ret;
	}


}
