/**
 * Copyright 2011 Nelson Efrain Abraham Cruz
 *
 * This file is part of JDBGM.
 * 
 * JDBGM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDBGM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JDBGM.  If not, see <http://www.gnu.org/licenses/>.
 */
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
public abstract class JDBCManager implements GenericManager {
	
	private static long expirationTime = 300000; // 5 minutos
	private static long initTime = 0;
	protected ExceptionHandler handler;
	//TODO usar un pool de conexiones?
	protected Connection connection;
	protected Statement st;
	protected String location, user, password, locationURL;
	protected boolean isAutoCommit = true;
	protected boolean connectionStarted = false;
	
	
	private boolean isInTransaction(){
		if (isAutoCommit) return false;
		else return true;
	}
	
	private void rollbackIfTransaction(String infoErr) throws JDException{
		if (isInTransaction()) {
			try {
				getConnection().rollback();
			} catch (SQLException e) {
				throw new JDException("No se pudieron deshacer los cambios, " + infoErr, e);
			}
			
		}
	}
	
	public Connection getConnection() throws JDException {
		if ( (connection == null) ) {
			try {
				connection = DriverManager.getConnection(locationURL, user, password);
				initTime = System.currentTimeMillis();
			} catch (SQLException e) {
				// TODO arreglar mensaje de error
				throw new JDException("problema al conectar con la base de datos", e);
			}
			//Si paso demasiado tiempo con la misma conexión (5 minutos) debo revisar que aun sirva
		} else if ( (System.currentTimeMillis() - initTime) > expirationTime) {
		
			try {
				Statement st = connection.createStatement();
				st.executeQuery("SELECT 1");
				//Si ocurre un error al intentar ejecutar una sentencia quiere decir que la conexión ya no sirve
			} catch (SQLException e) {
				try {
					//creo una conexión nueva la anterior murió de vieja
					//connection.close();
					connection = DriverManager.getConnection(locationURL, user, password);
				} catch (SQLException e1) {
					throw new JDException("problema al conectar con la base de datos", e);
				}
			}
			
		}
		//reseteo el tiempo para contar el timeout
		initTime = System.currentTimeMillis();
		return connection;
	}

	
	public void beginConnection() throws JDException {
		try {
			getConnection();
			connectionStarted = true;
			isAutoCommit = true;
		} catch (JDException e) {
			/*
			 * que paso acá? El método getConnection fallo, deberla ser por que no
			 * se puede acceder a la base de datos o bien los parámetros usados en
			 * DriverManager.getConnection están erróneos. 
			 */
			//TODO cambiar el tipo de excepción devuelto
			throw e;
		}
	}
	
	public void endConnection() throws JDException{
		/*
		 * Mejorar el manejo de las excepciones, mas bien hacer algo con la excepciones
		 */
		if ( (connection == null) || (!connectionStarted)) return;
		
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
		 * mientras dure la conexión u obtener un nuevo statement cada vez que se
		 * realiza un update. Además se ha de eliminar este método o al menos volverlo
		 * privado.
		 */
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		Statement stat = null;
		int result = -1;
		try {
			stat = getConnection().createStatement();
			result = stat.executeUpdate(sql);
		} catch (SQLException e) {
			rollbackIfTransaction("problema mientras se ejecutaba la sentencia: "+ sql);
			throw new JDException("problema mientras se ejecutaba la sentencia: "+ sql, e);
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
			stat = getConnection().createStatement();
			resultset = stat.executeQuery(sql);
		} catch (SQLException e) {
			rollbackIfTransaction("problema mientras se ejecutaba la sentencia: "+ sql);
			throw new JDException("problema mientras se ejecutaba la sentencia: "+sql, e);

		}
		return resultset;
	}
	
	public ResultSet query(QueryStatement query) throws JDException{
		return query(query.toString());
	}
	
	public int updateAndClose(String sql) throws JDException {
		/*
		 * No se si estara bien, o si sera útil, esto hay que LEER MAS!
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
	
	@Override
	public void beginTransaction() throws JDException {
		
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		if ( !isAutoCommit ) return;
		try {
			getConnection().setAutoCommit(false);
			isAutoCommit = false;
		} catch (SQLException e) {
			throw new JDException("beginTransaction", "hubo un error desconocido al intentar" +
					"comenzar con una nueva transacción", e);
		
		}
	}
	
	@Override
	public void endTransaction() throws JDException {
		commit();
		try {
			getConnection().setAutoCommit(true);
			isAutoCommit = true;
		} catch (SQLException e) {
			throw new JDException("endTransaction", "no se pudo volver a autocommit", e);
			
		}
	}


	@Override
	public void commit() throws JDException {
		if ( !connectionStarted ) 
			throw new JDException("la conexion no fue inicializada o fue cerrada", null);
		if ( isAutoCommit ) 
			throw new JDException("No se esta dentro de una tranzacción no se puede" +
					"usar commit()", null);
		
		try {
			getConnection().commit();
		} catch (SQLException e) {
			try {
				getConnection().rollback();
			} catch (SQLException e1) {
				throw new JDException("endTransaction","No se pudieron deshacer los cambios" +
						"despues de fallar el intento de hacer permanente los cambios", e1);
			}
			throw new JDException("endTransaction", "no se pudieron hacer permanentes los cambios" +
					"en la base de datos.", e);
		}
	}


}
