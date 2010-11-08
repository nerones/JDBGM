package com.spaceprogram.sql.db2;

import com.crossdb.sql.*;

public class DB2Factory implements SpecificSQLFactory {

	public InsertQuery getInsertQuery(){
		DB2InsertQuery iq = new DB2InsertQuery();
		return iq;
	}

	public SelectQuery getSelectQuery() {
		DB2SelectQuery sq = new DB2SelectQuery();
		return sq;
	}
	public UpdateQuery getUpdateQuery(){
		DB2UpdateQuery uq = new DB2UpdateQuery();
		return uq;
	}
	public DeleteQuery getDeleteQuery(){
		DB2DeleteQuery dq = new DB2DeleteQuery();
		return dq;
	}
	public CreateTableQuery getCreateTableQuery(){
		DB2CreateTableQuery ctq = new DB2CreateTableQuery();
		return ctq;
	}

    public IWhereClause getWhereClause() {
        return new WhereClause();
    }

    public void setSequenceSuffix(String suffix) {
    }

    public AlterTableQuery getAlterTableQuery(){

		DB2AlterTableQuery atq = new DB2AlterTableQuery();
		return atq;
	}




}
