/**
 * 
 */
package com.nelsonx.jdbgm.core;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.rowset.CachedRowSet;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public interface GenericManager {
	
	/**
	 * 
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
	 * 
	 * @param sql
	 * @return the number of affected rows
	 */
	int update(String sql);
	
	/**
	 * 
	 * @param sql
	 * @return a {@link ResultSet} whit the result of the query
	 */
	ResultSet query(String sql);
	
	/**
	 * 
	 * @param sql
	 * @return the number of affected rows
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

}
