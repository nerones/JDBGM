package com.thinkvirtual.sql.sybase;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import com.crossdb.sql.*;

public class SybaseInsertQuery  extends DefaultInsertQuery implements InsertQuery {
	//Query q;
	String query1;
	
	String table;
	List columns; // = new ArrayList(); // SELECT columns
	List values;
	//List tables;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	//List order_by; // ORDER BY order_by
	//int limit[]; // 2 max that will be offset, count
	boolean return_id = false;
	int no_alter_values = 0; // counts the number of no alter values
	
	public SybaseInsertQuery(){
		query1 = "";
		//tables = new ArrayList();
		columns = new ArrayList();
		values = new ArrayList();
		//where_clauses = new ArrayList();
		//order_by = new ArrayList();
	}
	
	
	
	
	public void setTable(String table){
		this.table = table;
	}
	public void addAutoIncrementColumn(String column){
		/*
		Just ignore it.
		 Column c = new Column(column);
		c.setAutoIncrement(true);
		columns.add(c);
		 values.add(null);*/
		
	}
	public void addAutoIncrementColumn(String column, String sequence){
		
		/*Ignore it.
		 Column c = new Column(column);
		c.setAutoIncrement(true);
		c.setSequence(sequence);
		columns.add(c);
		 values.add(null);*/
	}
	/*	public void addColumn(String column, String value, boolean auto_inc){
	 
	 columns.add(column);
	 if(auto_inc){
	 values.add(null);
	 }
	 }*/
	public void addColumn(String column, String value){
		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}
	public void addColumn(String column, float value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Float(value));
	}
	public void addColumn(String column, double value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Double(value));
	}
	public void addColumn(String column, int value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Integer(value));
	}
	public void addColumn(String column, java.util.Date value){
		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}
	public void addColumn(String column, boolean value){
		columns.add(new Column(column));
		values.add(new Boolean(value));
	}
	public void addColumnNoAlter(String column, String value){
		columns.add(0, new Column(column));
		values.add(0,value);
		no_alter_values++;
		
	}
	/*public void addColumn(String column, Integer value){
	 columns.add(column);
	 values.add(value);
	 }*/
	public void returnID(boolean b){
		return_id = b;
	}
	public String toString(){
		String query2 = "INSERT INTO " + table + " ( ";
		String query2b = " ) VALUES ( ";
		//pr("col=" + cols.size() + " - " + dfs.length);
		//int m2 = 0;
		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		for(int m = 0; m < columns.size(); m++){
			
			Column col = (Column)(columns.get(m));
			Object val = values.get(m);
			String in_val;
			if(val == null){
				in_val = null;
			}
			else if(val instanceof String){ // then sql escape and put quotes around it
				if(no_alter_values > m){
					in_val =(String)val;
				}
				else{
					in_val = "'" + SQLFormat.escape((String)val) + "'";
				}
			}
			else if(val instanceof java.util.Date){
				in_val = "'" + sqldf.format(val) + "'";
			}
			else if(val instanceof Boolean){
				Boolean b = (Boolean)val;
				if(b.booleanValue()){
					// true, so 1
					in_val = "1";
				}
				else in_val = "0";
				
			}
			else{
				in_val = val.toString();
			}
			//	String val = (String)();
			query2 += col.getName() + ",";
			query2b += in_val + ",";
			
			
			
		}
		query2 = query2.substring(0,query2.length() - 1);
		query2b = query2b.substring(0,query2b.length() - 1);
		query2b += " ) ";
		return query2 + query2b;
	}
	public int execute(Statement stmt) throws SQLException {
		//q = new 	Query(conn);
		//conn.executeQuery();
		stmt.executeUpdate(toString());
		int ret = 0;
		if(return_id){
			String query1 = "SELECT @@identity AS new_id FROM " + table;
			java.sql.ResultSet rs = stmt.executeQuery(query1);
			rs.next();
			ret = rs.getInt("new_id");
			rs.close();
		}
		//PreparedStatement ps = conn.prepareStatement("whater");
		
		return ret;
	}
	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;
	}
	public PreparedStatement getPreparedStatement(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement(toString());
		return pstmt;
	}
}
