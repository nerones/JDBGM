package com.crossdb.sql;

import java.util.ArrayList;

/**
 * Implementación base de {@link CreateTableQuery}, de  esta clase deberían heredar todas
 * las implementaciones especificas para algún motor dado.
 *   
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 * 
 * Date: Jun 27, 2002
 * Time: 8:47:24 PM
 *
 */
public abstract class DefaultCreateTableQuery implements CreateTableQuery {

    protected String name;
    protected ArrayList<Column> columns;
    protected boolean auto_defaults = true;
    protected boolean isTemporary = false;

    public DefaultCreateTableQuery() {
        columns = new ArrayList<Column>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void setAutoDefaults(boolean b) {
        auto_defaults = b;
    }
    
    public void setTemporary(boolean istemporary){
    	isTemporary = istemporary;
    }
    public abstract String toString();

    /*
    public void execute(java.sql.Statement stmt) throws SQLException {
        //q = new 	Query(conn);
        //rs = stmt.executeQuery(querystring);
        stmt.executeUpdate(toString());
    }

    public void execute(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        execute(stmt);
        stmt.close();
    }
    */

}
