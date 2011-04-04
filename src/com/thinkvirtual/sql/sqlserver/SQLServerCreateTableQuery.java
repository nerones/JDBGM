/*

 Generic column class.

 Note: This was taken from DataField.
 */

package com.thinkvirtual.sql.sqlserver;

/**
 *
 * @author  prophecy
 * @version
 */

import java.util.*;

import com.crossdb.sql.Column;

import java.sql.SQLException;
import java.sql.Connection;

public class SQLServerCreateTableQuery implements com.crossdb.sql.CreateTableQuery {

    String name;
    List columns;
    boolean auto_defaults = true;

    public SQLServerCreateTableQuery() {
        columns = new ArrayList();
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

    public String toString() {

        String query1;
        boolean add_primary_key_fx = false; // used in sap db where primary key is specified in a function
        query1 = " CREATE TABLE " + name + " ( "; //"CREATE TABLE IF NOT EXISTS " + table_name + " ( ";
        for (int j = 0; j < columns.size(); j++) {
            Column df = (Column) (columns.get(j));
            query1 += df.getName() + " ";
            String default_value = "";
            if (df.getType() == java.sql.Types.TIMESTAMP || df.getType() == java.sql.Types.DATE) {
                query1 += " datetime ";
                default_value = " getDate() ";

            }
            else if (df.getType() == java.sql.Types.INTEGER) {
                query1 += " INT ";
                default_value = " 0 ";
            }
            else if (df.getType() == java.sql.Types.VARCHAR) {
                //if(vendor != MS_SQL_SERVER){
                /*if(df.getSize() > 255){
                 df.setSize(255);
                 }*/
                //}
                query1 += " varchar(" + df.getSize() + ") ";
                default_value = " '' ";
            }
            else if (df.getType() == java.sql.Types.BIT) {
                // sap uses boolean instead
                query1 += " bit "; //getBitRepresentation(vendor); from DatabaseVendors
                default_value = " 0 ";
            }
            else if (df.getType() == java.sql.Types.CHAR) {
                query1 += " CHAR(" + df.getSize() + ") ";
                default_value = " ' ' ";
            }
            else if (df.getType() == java.sql.Types.LONGVARCHAR) {
                query1 += " text ";
                default_value = " '' ";
            }
            else if (df.getType() == java.sql.Types.FLOAT) { // sql server thing
                query1 += " float ";
                default_value = " 0.0 ";
            }

            else {
                query1 += " varchar(" + df.getSize() + ") "; // default for unknown types
                default_value = " '' ";
            }

            if (df.isAutoIncrement()) {
                query1 += " IDENTITY (1, 1) primary key ";
            }

            if (df.isNullable() == 1 && df.isAutoIncrement() == false) {
                query1 += " NULL ";
            }
            else
                query1 += " NOT NULL ";


            if (df.isNullable() == 1) {
                query1 += " NULL ";
            }
            else
                query1 += " NOT NULL ";
            if (df.getDefaultValue() != null) {
                if (df.getType() == java.sql.Types.VARCHAR) {
                    query1 += " DEFAULT '" + df.getDefaultValue() + "'";
                }
                else {
                    query1 += " DEFAULT " + df.getDefaultValue();
                }
            }
            else if (auto_defaults) { // set defaults automatically

                if (df.isNullable() == 0) { //not nullable
                    if (!df.isAutoIncrement()) {


                        query1 += " DEFAULT " + default_value;
                    }

                }

            }

            if (j < columns.size() - 1) {
                query1 += ", ";
            }

        }
        query1 += " ) ";
        return query1;
    }

    public void execute(java.sql.Statement stmt) throws SQLException {
        //q = new 	Query(conn);
        //rs = stmt.executeQuery(querystring);
        stmt.executeUpdate(toString());
    }

    public void execute(Connection conn) throws SQLException {
        java.sql.Statement stmt = conn.createStatement();
        execute(stmt);
    }

	/* (non-Javadoc)
	 * @see com.crossdb.sql.CreateTableQuery#setTemporary(boolean)
	 */
	@Override
	public void setTemporary(boolean istemporary) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.crossdb.sql.CreateTableQuery#isCompositePK()
	 */
	@Override
	public boolean isCompositePK() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.crossdb.sql.CreateTableQuery#getCompositePK()
	 */
	@Override
	public String getCompositePK() {
		// TODO Auto-generated method stub
		return null;
	}
}
