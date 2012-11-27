package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.crossdb.sql.TableConstraint;

/**
 * Implementación especifica de {@link DefaultCreateTableQuery} para el DBMS
 * PostgreSQL.
 * 
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class PostgreSQLCreateTableQuery extends DefaultCreateTableQuery {

	PostgreSQLCreateTableQuery() {
		super(new PostgreSQLDataTypes());
	}

	@Override
	public String columnToString(Column column) {
		
		String columnAsString = column.getName() + " ";
		if (column.isAutoIncrementPK() && isHaveAutoincrementPrimaryKey())  return columnAsString += "SERIAL PRIMARY KEY";
		columnAsString += datatype.getAsString(column);
		if (column.isNullable() == 0) columnAsString += " NOT NULL";
		//TODO revisar valor por defecto y el tipo de dato
		if (column.getColumnDefaultValue() != null) columnAsString += " DEFAULT " + column.getColumnDefaultValue();
		
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
