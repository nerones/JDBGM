/**
 * NOTE: this class is not needed because JDBGM will manage the execute of any
 * sql statement.
 * The interface that all update queries extend:
 * insert, update, delete (maybe alter?)
 * Any query that does Statment.executeUpdate on execute.
 *
 * These types of queries can be used for batch updates
 *
 *
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Aug 14, 2002
 * Time: 1:37:37 PM
 * 
 */
package com.crossdb.sql;

import java.sql.Connection;
import java.sql.SQLException;

@Deprecated
public interface ExecuteUpdate
{




   /**
	Returns the number of rows affected
	 */
	int execute(Connection conn)throws SQLException ;
	/**
	Uses stmt to execute the query.  This is so you can keep reusing the same
	statement.  Be sure to use new statements if you want more than one resultset
	open at the same time.
	 */
	int execute(java.sql.Statement stmt) throws SQLException ;
}
