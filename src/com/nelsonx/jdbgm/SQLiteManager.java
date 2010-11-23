/**
 * 
 */
package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class SQLiteManager extends JDBCManager {
	

	protected final String jdbc = "jdbc:sqlite:";
	
	public SQLiteManager(String location, String user, String password) throws JDException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.location = location;
		this.user = user;
		this.password = password;
		if (location.split("/").length > 1){
			location = location.split("/")[1];
		}
		locationURL = jdbc+location+".db";
		beginConnection();
		System.out.println(locationURL);
	}
	
	public Connection getConnection(){
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(locationURL);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("aca");
				e.printStackTrace();
			}
		}
		return connection;
	}

	
}
