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
	 * <p>Realiza una acción de actualización sobre la capa de persistencia </p> 
	 * Pudiendo
	 * ser crear, editar ó agregar datos a una estructura de datos, en una RDBMS
	 * serían las acciones echas por las comandos INSERT, UPDATE Y DELETE definidos
	 * en SQL. 
	 * @param sql La sentencia a ser ejecutada sobre la capa de persistencia
	 * @return el numero de filas afectadas en la operación realizada.
	 */
	int update(String sql);
	
	/**
	 * <p>Realiza una consulta sobre la capa de persistencia con un comando que es
	 * reconocido por la capa de persistencia.</p>
	 *  En una RDBMS seria una sentencia
	 * SELECT de SQL.
	 * @param sql Es la sentencia a ser ejecutada sobre la capad de persistencia
	 * @return a {@link ResultSet} whit the result of the query
	 */
	ResultSet query(String sql);
	
	/**
	 * <p>
	 * Actúa igual que <code> update(String sql) </code> realiza una actualización
	 * sobre la capa de persistencia. </p> 
	 * 
	 * Pero en este caso el método obtiene una
	 * nueva conexión y una ves terminada la transacción la conexión es cerrada
	 * para liberar los recursos de la capa de persistencia. Es decir este método
	 * obtiene su propia conexión para realizar la acción definida por el parámetro
	 * y una vez finalizada esta acción cierra la conexión con la capa de persistencia.
	 * 
	 * @param sql La acción a ser realizada sobre la capa de persistencia
	 * @return El numero de filas afectadas.
	 * @see #update(String sql)
	 * 
	 */
	int updateAndClose(String sql);
	
	/**
	 * <p>Actúa igual que <code> query(String sql) </code> realiza una consulta
	 * sobre la capa de persistencia.</p>
	 * 
	 * Pero en este caso el método obtiene una
	 * nueva conexión y una ves terminada la transacción la conexión es cerrada
	 * para liberar los recursos de la capa de persistencia. Es decir este método
	 * obtiene su propia conexión para realizar la acción definida por el parámetro
	 * y una vez finalizada esta acción cierra la conexión con la capa de persistencia.
	 * @param sql La consulta a realizarse sobre la capa de persistencia
	 * @return a {@link CachedRowSet} whit te result of the query
	 * @see #update(String)
	 */
	CachedRowSet queryAndClose(String sql);
	
	/**
	 * Establece la interfaz que estará encargada de gestionar las excepciones
	 * que puedan producir cualquiera de los métodos definidos en esta interfaz
	 * 
	 * @see ExceptionHandler
	 */
	void setExceptionHandler();
	
	/**
	 * Obtiene la interfaz encargada de gestionar las excepciones que puedan
	 * producir cualquiera de los métodos definidos en la interfaz.   
	 */
	void getExceptionHandler();
	
	/**
	 * Demarca el inicio de una secuencia de acciones sobre la capa de persistencia
	 * tratando de realizar todas las acciones como una única transacción por lo
	 * cual ninguna acción tendrá un efecto real hasta que la transacción halla
	 * finalizado, indicando esta situación con el método <code>endTransaction()</code>
	 * 
	 * @see #endTransaction()
	 */
	void beginTransaction();
	
	/**
	 * Demarca el final de una transacción, intenta hacer permanente los cambios
	 * realizados sobre la capa de persistencia.
	 */
	void endTransaction();
	
	

}
