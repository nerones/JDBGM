package com.thinkvirtual.sql.sybase;

import com.crossdb.sql.*;

/** This is an initial beta of a class that will represent a query string */

//import java.util.List;
//import java.sql.*;

public class SybaseFactory implements SQLFactory {
	
	String schema;
	
	public InsertQuery getInsertQuery(){
		SybaseInsertQuery iq = new SybaseInsertQuery();
		return iq;
	}
	
	public SelectQuery getSelectQuery() {
		SybaseSelectQuery sq = new SybaseSelectQuery();
		/*if(schema != null){
			sq.setSchema(schema);
		}*/
		return sq;
	}
	public UpdateQuery getUpdateQuery(){
		SybaseUpdateQuery uq = new SybaseUpdateQuery();
		return uq;
	}
	public DeleteQuery getDeleteQuery(){
		SybaseDeleteQuery dq = new SybaseDeleteQuery();
		return dq;
	}
	public CreateTableQuery getCreateTableQuery(){
		SybaseCreateTableQuery ctq = new SybaseCreateTableQuery();
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

		SybaseAlterTableQuery atq = new SybaseAlterTableQuery();
		return atq;
	}
}
