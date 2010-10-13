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
public class MySqlManager implements GenericManager{
	
	protected ExceptionHandler handler;
	protected Connection connection;
	protected Statement st;
	protected String location, user, password, locationURL;
	protected final String jdbc = "jdbc:mysql://";
	
	public MySqlManager(String location, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.location = location;
		this.user = user;
		this.password = password;
		locationURL = jdbc+location;
		// TODO Auto-generated constructor stub
	}
	
	protected Connection getConection(String location, String user, String password) {
		if (connection == null) {
			//Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + location;
			//Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "tester", "tester");
			System.out.println(url);
			try {
				connection = DriverManager.getConnection(url,user,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
		
	}
	public Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(locationURL, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public void closeConection() {
		
	}
	public void makeQuery() {
		
	}
	
	public void beginConnection() {
		/*
		 * Utilizado para demarcar el inicio de una secuencia de 
		 */
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public int updateAndClose(String sql) {
		/*
		 * No se si estara bien, o si sera util, esto hay que LEER MAS!
		 */
		int result = -1;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(locationURL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stat;
		try {
			stat = connection.createStatement();
			result = stat.executeUpdate(sql);
			stat.close();
			getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public CachedRowSet queryAndClose(String sql) {
		/*
		 * Lo mismo que para updateAndClose
		 */
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(locationURL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stat;
		CachedRowSetImpl crs = null;
		try {
			stat = connection.createStatement();
			ResultSet rs =stat.executeQuery(sql);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			stat.close();
			getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return crs;
	}
	
	public Object executeAndClose(Command command) throws SQLException {
		try {
			return command.execute(this);
		} finally {
			this.connection.close();
		}
	}
	/**
	 * 
	 */
	@Override
	public void setExceptionHandler() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	@Override
	public void getExceptionHandler() {
		// TODO Auto-generated method stub
		
	}
	

}
