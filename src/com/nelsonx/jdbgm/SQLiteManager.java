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
	
	public Connection getConnection() throws JDException{
		if ( (connection == null) ) {
			try {
				connection = DriverManager.getConnection(locationURL, user, password);
			} catch (SQLException e) {
				// TODO arreglar esta excepción
				throw new JDException("problema al conectar con la base de datos", e);
			}
		}
		return connection;
	}

	
}
