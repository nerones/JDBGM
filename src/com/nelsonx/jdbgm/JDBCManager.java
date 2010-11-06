package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Implementation of {@link GenericManager} to manage a relational data base
 * through JDBC
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 * @since  0.5
 */
public class JDBCManager implements GenericManager {
	
	protected ExceptionHandler handler;
	protected Connection connection;
	protected Statement st;
	protected String location, user, password, locationURL;
	
	@Deprecated
	protected Connection getConection(String location, String user, String password) {
		if (connection == null) {
			String url = "jdbc:mysql://" + location;
			System.out.println(url);
			try {
				connection = DriverManager.getConnection(url,user,password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
		
	}
	public Connection getConnection() {
		if ( (connection == null) ) {
			try {
				connection = DriverManager.getConnection(locationURL, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
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
	@Override
	public void beginTransaction() {
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void endTransaction() {
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
		}
	}


}
