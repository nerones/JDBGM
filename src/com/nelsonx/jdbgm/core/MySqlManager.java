/**
 * 
 */
package com.nelsonx.jdbgm.core;

import java.sql.*;

import javax.sql.rowset.CachedRowSet;

import com.nelsonx.jdbgm.Command;
import com.sun.rowset.CachedRowSetImpl;


/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MySqlManager {
	
	protected Connection connection;
	protected Statement st;
	protected String location, user, password, locationURL;
	protected final String jdbc = "jdbc:mysql://";
	
	public MySqlManager(String location, String user, String password) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.location = location;
		this.user = user;
		this.password = password;
		locationURL = jdbc+location;
		// TODO Auto-generated constructor stub
	}
	
	protected Connection getConection(String location, String user, String password) throws Exception{
		if (connection == null) {
			//Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + location;
			//Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "tester", "tester");
			System.out.println(url);
			connection = DriverManager.getConnection(url,user,password);
		}
		return connection;
		
	}
	protected Connection getConnection() throws SQLException{
		if (connection == null) {
			connection = DriverManager.getConnection(locationURL, user, password);
		}
		return connection;
	}
	
	public void closeConection() {
		
	}
	public void makeQuery() {
		
	}
	
	public void beginConnection() throws SQLException {
		/*
		 * Utilizado para demarcar el inicio de una secuencia de 
		 */
		getConnection().setAutoCommit(false);
	}
	
	public void endConnection(){
		/*
		 * Mejorar el manejo de las exepxiones, mas bien hacer algo con la excepciones
		 */
		try {
			getConnection().commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method is used to make update actions over de data base, such as UPDATE, INSERT or DELETE
	 * @param sql 
	 * @return the number of rows affected by the sql statement
	 */
	public int update(String sql){
		/*
		 * RECORDAR que hacer con statement, si mantener una instancia del mismo
		 * mientras dure la conexion u obtener un nuevo statement cada vez que se
		 * realiza un update.
		 */
		Statement stat = null;
		int result = -1;
		try {
			stat = getConnection().createStatement();
			result = stat.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	return result; 
	}
	
	public ResultSet query(String sql){
		Statement stat = null;
		ResultSet resultset = null;
		try {
			stat = getConnection().createStatement();
			resultset = stat.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return resultset;
	}
	
	public int updateAndClose(String sql) throws Exception{
		/*
		 * No se si estara bien, o si sera util, esto hay que LEER MAS!
		 */
		int result = -1;
		Connection connection = DriverManager.getConnection(locationURL, user, password);
		Statement stat = connection.createStatement();
		result = stat.executeUpdate(sql);
		stat.close();
		getConnection().close();
		return result;
	}
	
	public CachedRowSet queryAndClose(String sql) throws SQLException {
		/*
		 * Lo mismo que para updateAndClose
		 */
		Connection connection = DriverManager.getConnection(locationURL, user, password);
		Statement stat = connection.createStatement();
		ResultSet rs =stat.executeQuery(sql);
		CachedRowSetImpl crs = new CachedRowSetImpl();
		crs.populate(rs);
		stat.close();
		getConnection().close();
		return crs;
	}
	
	public Object executeAndClose(Command command) throws SQLException {
		try {
			return command.execute(this);
		} finally {
			this.connection.close();
		}
	}
	

}
