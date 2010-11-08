package com.spaceprogram.sql.hsqldb;

import com.crossdb.sql.*;


public class HsqldbFactory implements SpecificSQLFactory {
	
	public InsertQuery getInsertQuery(){
		HsqldbInsertQuery iq = new HsqldbInsertQuery();
		return iq;
	}
	
	public SelectQuery getSelectQuery() {
		HsqldbSelectQuery sq = new HsqldbSelectQuery();
		return sq;
	}
	public UpdateQuery getUpdateQuery(){
		HsqldbUpdateQuery uq = new HsqldbUpdateQuery();
		return uq;
	}
	public DeleteQuery getDeleteQuery(){
		HsqldbDeleteQuery dq = new HsqldbDeleteQuery();
		return dq;
	}
	public CreateTableQuery getCreateTableQuery(){
		HsqldbCreateTableQuery ctq = new HsqldbCreateTableQuery();
		return ctq;
	}

    public IWhereClause getWhereClause() {
        return new WhereClause();
    }

    public void setSequenceSuffix(String suffix) {
    }

    public AlterTableQuery getAlterTableQuery(){
		
		HsqldbAlterTableQuery atq = new HsqldbAlterTableQuery();
		return atq;
	}

	
	
	
}
