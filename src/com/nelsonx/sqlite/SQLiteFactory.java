package com.nelsonx.sqlite;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.IWhereClause;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.SpecificSQLFactory;
import com.crossdb.sql.UpdateQuery;

public class SQLiteFactory implements SpecificSQLFactory {

	@Override
	public InsertQuery getInsertQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SelectQuery getSelectQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UpdateQuery getUpdateQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeleteQuery getDeleteQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlterTableQuery getAlterTableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateTableQuery getCreateTableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWhereClause getWhereClause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSequenceSuffix(String suffix) {
		// TODO Auto-generated method stub

	}

}