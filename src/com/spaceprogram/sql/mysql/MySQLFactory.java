package com.spaceprogram.sql.mysql;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.UpdateQuery;
import com.crossdb.sql.WhereClause;


public class MySQLFactory extends SQLFactory {
	
	private MySQLFormatter formatter;
	
	public MySQLFactory() {
		formatter = new MySQLFormatter();
	}
	
	public InsertQuery getInsertQuery(){
		return new MySQLInsertQuery();
	}
	
	public SelectQuery getSelectQuery() {
		return new MySQLSelectQuery(formatter);
	}
	
	public UpdateQuery getUpdateQuery(){	
		return new MySQLUpdateQuery(formatter);
	}
	
	public DeleteQuery getDeleteQuery(){
		return new MySQLDeleteQuery(formatter);
	}
	
	public CreateTableQuery getCreateTableQuery(){
		return new MySQLCreateTableQuery();	 
	}

    public AlterTableQuery getAlterTableQuery(){
    	return new MySQLAlterTableQuery();
	}
    
	@Override
	public WhereClause getWhereClause() {
		return new WhereClause(formatter);
	}

}
