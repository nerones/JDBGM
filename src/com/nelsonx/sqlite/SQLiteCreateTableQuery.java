package com.nelsonx.sqlite;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.crossdb.sql.TableConstraint;

public class SQLiteCreateTableQuery extends DefaultCreateTableQuery {

	
	public SQLiteCreateTableQuery() {
		super(new SQLiteDataTypes());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String columnToString(Column column) {
		
		String columnAsString = column.getName() + " ";
		columnAsString += datatype.getAsString(column);
		if (column.isNullable() == 0) columnAsString += " NOT NULL";
		//TODO revisar valor por defecto y el tipo de dato
		if (column.getColumnDefaultValue() != null) columnAsString += " DEFAULT " + column.getColumnDefaultValue();
		if (column.isAutoIncrementPK() && isHaveAutoincrementPrimaryKey()) columnAsString += " PRIMARY KEY AUTOINCREMENT";
		
		return columnAsString;
	}
	
	public String sentenceAsSQL() {

		//String query1;
		
		String createAsString = "CREATE";
		if (isTemporary) createAsString += " TEMPORARY";
		createAsString += " TABLE ";
		if (selectStatementSource != null) return createAsString + tableName + " AS " + selectStatementSource.toString();
		createAsString +=  tableName + " ( ";
		for (Column column : columns){
			createAsString += columnToString(column) + ", ";
		}
		
		for (TableConstraint tableConstraint : tableConstraints){
			createAsString += tableConstraint.toString() + ", ";
		}
		//Elimino el ultimo ", " agregado
		createAsString = createAsString.substring(0, createAsString.length()-2);
		return createAsString + " )";
	}



}
