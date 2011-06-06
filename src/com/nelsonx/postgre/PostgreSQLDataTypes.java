package com.nelsonx.postgre;

import com.crossdb.sql.DataTypes;

public class PostgreSQLDataTypes extends DataTypes{
	
	public String getAsString(int type, int size) {
		String query1;
		switch (type) {
		case java.sql.Types.BIGINT:
			query1 = "BIGINT";
			break;
		// TODO binary to blob, correct?
		case java.sql.Types.BINARY:
			query1 = "BLOB";
			break;
		// todo boolean and bit to tinyint(1)
		case java.sql.Types.BIT:
		case java.sql.Types.BOOLEAN:
			query1 = "TINYINT(1)";
			// query1 = " BIT ";
			break;
		case java.sql.Types.CHAR:
			query1 = "CHAR(" + size + ")";
			break;
		case java.sql.Types.DATE:
			query1 = "DATE";
			break;
		case java.sql.Types.DECIMAL:
			query1 = "DECIMAL";
			break;
		case java.sql.Types.DOUBLE:
			query1 = "DOUBLE";
			break;
		case java.sql.Types.FLOAT:
			query1 = "FLOAT";
			break;
		case java.sql.Types.INTEGER:
			query1 = "INTEGER";
			break;
		// java_object?
		case java.sql.Types.JAVA_OBJECT:
			query1 = "OBJECT";
			break;
		case java.sql.Types.LONGVARBINARY:
			query1 = "MEDIUMBLOB";
			break;
		case java.sql.Types.LONGVARCHAR:
			query1 = "TEXT";
			break;
		case java.sql.Types.NUMERIC:
			query1 = "NUMERIC";
			break;
		case java.sql.Types.SMALLINT:
			query1 = "SMALLINT";
			break;
		case java.sql.Types.TIME:
			query1 = "TIME";
			break;
		case java.sql.Types.TIMESTAMP:
			query1 = "TIMESTAMP";
			break;
		case java.sql.Types.TINYINT:
			query1 = "TINYINT";
			break;
		case java.sql.Types.VARBINARY:
			query1 = "BLOB";
			break;
		case java.sql.Types.VARCHAR:
		default:
			query1 = "VARCHAR(" + size + ")";
		}
		return query1;

	}

}
