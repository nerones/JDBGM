/**
 * This will allow us to fix inconsistencies between databases.
 *
 * All implementations should return a class implementing CrossdbResultSet
 *
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */
package com.crossdb.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public interface CrossdbResultSet extends ResultSet {

    /**
     * This is a convenience method that I think should be part of the java.sql spec so you can
     * get java.util.Date object directly.
     *
     * All this will do is either add up getDate + getTime and return a new Date object
     * OR
     * Cast getTimestamp to Date object
     *
     * @param columnName
     * @return java.util.Date object with the columns date in it
     * @throws SQLException
     */
    java.util.Date getDateTime(String columnName) throws SQLException;

    /**
     * @see #getDateTime(String)
     *
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    java.util.Date getDateTime(int columnIndex) throws SQLException;

    /**
     * this is for crossdb to know whether to close the statement when the resultset is closed, not to
     * be used by app programmer
     * @param stmt
     */
    void setStatementCreated(Statement stmt);

    /**
     * this is for crossdb to know whether to close the statement when the resultset is closed, not to
     * be used by app programmer
     * @return
     */
    boolean isStatementCreated();


}




