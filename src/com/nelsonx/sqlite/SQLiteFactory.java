package com.nelsonx.sqlite;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.UpdateQuery;
import com.crossdb.sql.WhereClause;

public class SQLiteFactory extends SQLFactory {

	private SQLiteFormatter formatter;
	
	public SQLiteFactory() {
		formatter = new SQLiteFormatter();
	}
	
	@Override
	public InsertQuery getInsertQuery() {
		return new SQLiteInsertQuery();
	}

	@Override
	public SelectQuery getSelectQuery() {
		return new SQLiteSelectQuery(formatter);
	}

	@Override
	public UpdateQuery getUpdateQuery() {
		return new SQLiteUpdateQuery(formatter);
	}

	@Override
	public DeleteQuery getDeleteQuery() {
		return new SQLiteDeleteQuery(formatter);
	}

	@Override
	public AlterTableQuery getAlterTableQuery() {
		return new SQLiteAlterTableQuery();
	}

	@Override
	public CreateTableQuery getCreateTableQuery() {
		return new SQLiteCreateTableQuery();
	}

	@Override
	public WhereClause getWhereClause() {
		return new WhereClause(formatter);
	}

	

}
