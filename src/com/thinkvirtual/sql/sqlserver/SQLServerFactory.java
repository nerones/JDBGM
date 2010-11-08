package com.thinkvirtual.sql.sqlserver;

import com.crossdb.sql.*;

/** This is an initial beta of a class that will represent a query string */

//import java.util.List;
//import java.sql.*;

public class SQLServerFactory implements SpecificSQLFactory {
	
	String schema;
	
	public InsertQuery getInsertQuery(){
		SQLServerInsertQuery iq = new SQLServerInsertQuery();
		return iq;
	}
	
	public SelectQuery getSelectQuery() {
		SQLServerSelectQuery sq = new SQLServerSelectQuery();
		/*if(schema != null){
			sq.setSchema(schema);
		}*/
		return sq;
	}
	public UpdateQuery getUpdateQuery(){
		SQLServerUpdateQuery uq = new SQLServerUpdateQuery();
		return uq;
	}
	public DeleteQuery getDeleteQuery(){
		SQLServerDeleteQuery dq = new SQLServerDeleteQuery();
		return dq;
	}
	public CreateTableQuery getCreateTableQuery(){
		SQLServerCreateTableQuery ctq = new SQLServerCreateTableQuery();
		return ctq;
	}

    public IWhereClause getWhereClause() {
        return new WhereClause();
    }

    public void setSequenceSuffix(String suffix) {
    }

    public void setSchema(String schema){
		this.schema = schema;
	}

	public AlterTableQuery getAlterTableQuery(){

		SQLServerAlterTableQuery atq = new SQLServerAlterTableQuery();
		return atq;
	}
}
