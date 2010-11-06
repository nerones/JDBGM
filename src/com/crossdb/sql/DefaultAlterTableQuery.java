/**
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 8:52:42 PM
 * 
 */
package com.crossdb.sql;

import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

public abstract class DefaultAlterTableQuery implements AlterTableQuery{

	protected String query1;

	protected String table;


	protected List drops;
	protected List adds;



	WhereClause wclause = new WhereClause();

	public DefaultAlterTableQuery(){
		query1 = "";
		drops = new ArrayList();
		adds = new ArrayList();
	}




	public void setTable(String table){
		this.table = table;
	}

	public void addColumn(Column col){
		adds.add(col);

	}
	public void dropColumn(String colname){
		drops.add(colname);
	}

	public abstract String toString();



	public int execute(Statement stmt) throws SQLException{

		return stmt.executeUpdate(toString());
	}
	public int execute(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;


	}
}
