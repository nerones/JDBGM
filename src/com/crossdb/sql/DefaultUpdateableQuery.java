/**
 * Queries that you set column values, Insert and Update, should extend this
 *
 * TODO: Would be nice to be able to setup a query like this then just call doUpdate or doInsert to do either?  Or getInsertQuery or getUpdateQuery from UpdateableQuery
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Sep 4, 2002
 * Time: 12:21:44 AM
 * 
 */
package com.crossdb.sql;

import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.math.BigDecimal;

public class DefaultUpdateableQuery implements UpdateableQuery
{
    protected List columns; // = new ArrayList(); // SELECT columns
	//protected List values;

    public DefaultUpdateableQuery(){


		columns = new ArrayList();
		//values = new ArrayList();

	}

//	protected int no_alter_values = 0; // counts the number of no alter values

    public void addColumn(String column, String value){
		ColumnValue c = new ColumnValue(column, value);
		columns.add(c);
		//values.add(value);
	}
	public void addColumn(String column, float value){
		ColumnValue c = new ColumnValue(column, new Float(value));
		columns.add(c);
		//values.add(new Float(value));
	}
	public void addColumn(String column, double value){
		ColumnValue c = new ColumnValue(column, new Double(value));
		columns.add(c);
		//values.add(new Double(value));
	}
	public void addColumn(String column, int value){
		ColumnValue c = new ColumnValue(column, new Integer(value));
		columns.add(c);
		//values.add(new Integer(value));
	}
	public void addColumn(String column, java.util.Date value){
		ColumnValue c = new ColumnValue(column, value);
		columns.add(c);
		//values.add(value);
	}
	public void addColumn(String column, boolean value){
		columns.add(new ColumnValue(column, new Boolean(value)));
		//values.add(new Boolean(value));
	}

    public void addColumn(String column, BigDecimal value) {
        columns.add(new ColumnValue(column, value));
    }

    public void addColumnNoAlter(String column, String value){
        ColumnValue c = new ColumnValue(column, value);
        c.setNoAlter(true);
		columns.add(c);

		//values.add(0,value);
		//no_alter_values++;

	}

    /**
     *  Add the list of columns to the current list in the query.
     */
    public void appendColumns(List columns){
        this.columns.addAll(columns);

    }

    /**
     *
     * @return List of ColumnValue objects
     */
    public List getColumns(){
        return columns;
    }

    
    /* *
     * Dummy function so that it can implement ExecuteUpdate
     * @param conn
     * @return
     * @throws SQLException
     * /
    public int execute(Connection conn) throws SQLException {
        return 0;
    }

    /**
     * Dummy function so that it can implement ExecuteUpdate
     * @param stmt
     * @return
     * @throws SQLException
     * /
    public int execute(Statement stmt) throws SQLException {
        return 0;
    }



    /**
     * int column references
     */
   /*public void addColumn(int column, String value){
		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}
	public void addColumn(int column, float value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Float(value));
	}
	public void addColumn(int column, double value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Double(value));
	}
	public void addColumn(int column, int value){
		Column c = new Column(column);
		columns.add(c);
		values.add(new Integer(value));
	}
	public void addColumn(int column, java.util.Date value){
		Column c = new Column(column);
		columns.add(c);
		values.add(value);
	}
	public void addColumn(int column, boolean value){
		columns.add(new Column(column));
		values.add(new Boolean(value));
	}*/


}
