/**
 * 
 */
package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.rowset.CachedRowSet;

/**
 * A set of methods to manage a persistence layer implemented whit a 
 * generic RDBMS. These methods act as a basis for implementing a specific manager 
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 */
public interface GenericManager {
	
	/**
	 * Obtiene una conexión con la base de datos (capa de persistencia) 
	 * @return A connection whit the persistence layer
	 */
	Connection getConnection();
	
	/**
	 * 
	 */
	void beginConnection();
	
	/**
	 * 
	 */
	void endConnection();
	
	/**
	 * Realiza una acción de actualización sobre la capa de persistencia pudiendo
	 * ser crear, editar ó agregar datos a una estructura de datos, en una RDBMS
	 * serían las acciones echas por las comandos INSERT, UPDATE Y DELETE definidos
	 * en SQL. 
	 * @param sql La sentencia a ser ejecutada sobre la capa de persistencia
	 * @return el numero de filas afectadas en la operación realizada.
	 */
	int update(String sql);
	
	/**
	 * Realiza una consulta sobre la capa de persistencia con un comando que es
	 * reconocido por la capa de persistencia. En una RDBMS seria una sentencia
	 * SELECT de SQL.
	 * @param sql Es la sentencia a ser ejecutada sobre la capad de persistencia
	 * @return a {@link ResultSet} whit the result of the query
	 */
	ResultSet query(String sql);
	
	/**
	 * Actua ig
	 * @param sql
	 * @return the number of affected rows
	 * @see com.nelsonx.jdbgm.GenericManager#update(String sql)
	 */
	int updateAndClose(String sql);
	
	/**
	 * 
	 * @param sql
	 * @return a {@link CachedRowSet} whit te result of the query
	 */
	CachedRowSet queryAndClose(String sql);
	
	/**
	 * 
	 */
	void setExceptionHandler();
	
	/**
	 * 
	 */
	void getExceptionHandler();
	
	void beginTransaction();
	
	void endTransaction();
	
	

}
