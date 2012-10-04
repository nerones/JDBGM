package com.spaceprogram.sql.mysql;

import com.crossdb.sql.*;

/** This is an initial beta of a class that will represent a query string */

//import java.util.List;
//import java.sql.*;

public class MySQLFactory implements SpecificSQLFactory {
	
	public InsertQuery getInsertQuery(){
		MySQLInsertQuery iq = new MySQLInsertQuery();
		return iq;
	}
	
	public SelectQuery getSelectQuery() {
		MySQLSelectQuery sq = new MySQLSelectQuery();
		return sq;
	}
	public UpdateQuery getUpdateQuery(){
		MySQLUpdateQuery uq = new MySQLUpdateQuery();
		return uq;
	}
	public DeleteQuery getDeleteQuery(){
		MySQLDeleteQuery dq = new MySQLDeleteQuery();
		return dq;
	}
	public CreateTableQuery getCreateTableQuery(){
		MySQLCreateTableQuery ctq = new MySQLCreateTableQuery();
		return ctq;
	}

    public AlterTableQuery getAlterTableQuery(){
		
		MySQLAlterTableQuery atq = new MySQLAlterTableQuery();
		return atq;
	}

	
	
	
}
