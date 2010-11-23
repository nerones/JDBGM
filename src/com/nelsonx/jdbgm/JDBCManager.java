package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;

import com.crossdb.sql.QueryStatement;
import com.crossdb.sql.UpdateStatement;
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
	protected boolean isAutoCommit = true;
	protected boolean connectionStarted = false;
	
//	@Deprecated
//	protected Connection getConection(String location, String user, String password) {
//		if (connection == null) {
//			String url = "jdbc:mysql://" + location;
//			System.out.println(url);
//			try {
//				connection = DriverManager.getConnection(url,user,password);
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//			}
//		}
//		return connection;
//		
//	}
	
	public Connection getConnection() throws JDException {
		if ( (connection == null) ) {
			try {
				connection = DriverManager.getConnection(locationURL, user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new JDException("problema al conectar con la base de datos", e);
			}
		}
		return connection;
	}

	
	public void beginConnection() throws JDException {
		/*
		 *  
		 */
		try {
			getConnection();
			connectionStarted = true;
			isAutoCommit = true;
		} catch (JDException e) {
			throw e;
		}
	}
	
	public void endConnection() throws JDException{
		/*
		 * Mejorar el manejo de las exepxiones, mas bien hacer algo con la excepciones
		 */
		if ( (connection == null) ) return;
		
		try {
			if (!connection.isClosed()){
				if ( !isAutoCommit ){
					connection.commit();
				}
				getConnection().setAutoCommit(true);
			}
		} catch (SQLException e) {
//			handler.commitError();
			throw new JDException("endConnection","error de conexion a la base de datos", e);
		}finally{
			try {
				getConnection().close();
				connectionStarted = false;
			} catch (SQLException e) {
				throw new JDException("endConnection","error de conexion a la base de datos", e);
			}
		}
	}
	
	/**
	 * This method is used to make update actions over de data base, such as UPDATE, INSERT or DELETE
	 * @param sql 
	 * @return the number of rows affected by the sql statement
	 * @throws JDException 
	 */
	public int update(String sql) throws JDException{
		/*
		 * RECORDAR que hacer con statement, si mantener una instancia del mismo
		 * mientras dure la conexion u obtener un nuevo statement cada vez que se
		 * realiza un update.
		 */
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		Statement stat = null;
		int result = -1;
		try {
			stat = connection.createStatement();
			result = stat.executeUpdate(sql);
		} catch (SQLException e) {
			throw new JDException("problema mientras se ejecutaba la sentencia", e);
			// TODO Auto-generated catch block
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
				throw new ConnectionIssueException(e);
			}
			
		}
	return result; 
	}
	
	public int update(UpdateStatement update) throws JDException{
		return update(update.toString());
	}
	
	public ResultSet query(String sql) throws JDException{
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		Statement stat = null;
		ResultSet resultset = null;
		try {
			stat = connection.createStatement();
			resultset = stat.executeQuery(sql);
		} catch (SQLException e) {
			throw new JDException("problema mientras se ejecutaba la sentencia", e);

		}
		return resultset;
	}
	
	public ResultSet query(QueryStatement query) throws JDException{
		return query(query.toString());
	}
	
	public int updateAndClose(String sql) throws JDException {
		/*
		 * No se si estara bien, o si sera util, esto hay que LEER MAS!
		 */
		int result = -1;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(locationURL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new JDException("updateAndCLose","problema al conectar con la base de datos", e);
		}
		Statement stat;
		try {
			stat = connection.createStatement();
			result = stat.executeUpdate(sql);
			stat.close();
			connection.close();
		} catch (SQLException e) {
			throw new ConnectionIssueException(e);
		}
		
		return result;
	}
	
	public int updateAndClose(UpdateStatement update) throws JDException{
		return updateAndClose(update.toString());
	}
	
	public CachedRowSet queryAndClose(String sql) throws JDException {
		/*
		 * Lo mismo que para updateAndClose
		 */
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(locationURL, user, password);
		} catch (SQLException e) {
			throw new JDException("updateAndCLose","problema al conectar con la base de datos", e);
			
		}
		Statement stat;
		CachedRowSetImpl crs = null;
		try {
			stat = connection.createStatement();
			ResultSet rs =stat.executeQuery(sql);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			stat.close();
			connection.close();
		} catch (SQLException e) {
			throw new ConnectionIssueException(e);
		
		}
		
		return crs;
	}
	
	public CachedRowSet queryAndClose(QueryStatement query) throws JDException {
		return queryAndClose(query.toString());
	}
	
	
	/**
	 * 
	 */
	@Override
	public void setExceptionHandler(ExceptionHandler handler) {
		this.handler= handler;
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		return handler;
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beginTransaction() throws JDException {
		
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		if ( !isAutoCommit ) return;
		try {
			connection.setAutoCommit(false);
			isAutoCommit = false;
		} catch (SQLException e) {
			throw new JDException("beginTransaction", "nose", e);
		
		}
	}
	
	@Override
	public void endTransaction() throws JDException {
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		if ( isAutoCommit ) return;
		
		try {
			getConnection().commit();
		} catch (SQLException e) {
			throw new JDException("endTransaction", "no se pudo hacer commit", e);
		}
		try {
			getConnection().setAutoCommit(true);
			isAutoCommit = true;
		} catch (SQLException e) {
			throw new JDException("endTransaction", "no se pudo volver a autocommit", e);
			
		}
	}


}
