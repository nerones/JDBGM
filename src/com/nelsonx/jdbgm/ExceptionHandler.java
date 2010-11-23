/**
 * 
 */
package com.nelsonx.jdbgm;

import java.sql.SQLException;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public interface ExceptionHandler {

	void connectionError(SQLException e);

	void commitError();

	void AutoCommitError(SQLException e);

	void closeError(SQLException e);

}
