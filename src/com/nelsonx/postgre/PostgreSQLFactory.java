package com.nelsonx.postgre;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.UpdateQuery;
import com.crossdb.sql.WhereClause;

public class PostgreSQLFactory extends SQLFactory {

	private PostgreSQLFormatter formatter;
	
	public PostgreSQLFactory() {
		formatter = new PostgreSQLFormatter();
	}
	
	@Override
	public InsertQuery getInsertQuery() {
		return new PostgreSQLInsertQuery();
	}

	@Override
	public SelectQuery getSelectQuery() {
		return new PostgreSQLSelectQuery(formatter);
	}

	@Override
	public UpdateQuery getUpdateQuery() {
		return new PostgreSQLUpdateQuery(formatter);
	}

	@Override
	public DeleteQuery getDeleteQuery() {
		return new PostgreSQLDeleteQuery(formatter);
	}

	@Override
	public AlterTableQuery getAlterTableQuery() {
		return new PostgreSQLAlterTableQuery();
	}

	@Override
	public CreateTableQuery getCreateTableQuery() {
		return new PostgreSQLCreateTableQuery();
	}

	@Override
	public WhereClause getWhereClause() {
		return new WhereClause(formatter);
	}

}
