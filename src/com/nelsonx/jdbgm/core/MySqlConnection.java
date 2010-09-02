/**
 * 
 */
package com.nelsonx.jdbgm.core;

import java.sql.*;


/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MySqlConnection {
	
	Statement st;
	public MySqlConnection() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		// TODO Auto-generated constructor stub
	}
	public void getConection(String location, String user, String password) throws Exception{
		//Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://" + location;
		//Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "tester", "tester");
		System.out.println(url);
		Connection conexion = DriverManager.getConnection(url,user,password);
		st = (Statement) conexion.createStatement();
	}
	
	public void closeConection() {
		
	}
	public void makeQuery() {
		
	}
	
	public void makeUpdate(String sql) throws Exception{
		st.executeUpdate(sql);
	}

}
