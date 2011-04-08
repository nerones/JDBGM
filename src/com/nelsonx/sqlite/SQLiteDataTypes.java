package com.nelsonx.sqlite;

import com.crossdb.sql.Column;

public class SQLiteDataTypes {
	// TODO fix data types
	public static String getAsString(int type, int size) {
        String query1;
        switch (type) {
            case java.sql.Types.BIGINT:
                query1 = "INTEGER";
                break;
            //TODO no hay binary en sqlite
            case java.sql.Types.BINARY:
                query1 = "BINARY";
                break;
            case java.sql.Types.BIT:
            	//TODO boolean mapeado a integer 1 = true; 0 = false
            case java.sql.Types.BOOLEAN:
                query1 = "INTEGER";
                break;
            case java.sql.Types.CHAR:
                query1 = "TEXT";
                break;
            case java.sql.Types.DATE:
                query1 = "TEXT";
                break;
            case java.sql.Types.DECIMAL:
                query1 = "NUMERIC";
                break;
            case java.sql.Types.DOUBLE:
                query1 = "REAL";
                break;
            case java.sql.Types.FLOAT:
                query1 = "REAL";
                break;
            case java.sql.Types.INTEGER:
                query1 = "INTEGER";
                break;
            case java.sql.Types.JAVA_OBJECT:
                query1 = " OBJECT ";
                break;
            case java.sql.Types.LONGVARBINARY:
                query1 = " MEDIUMBLOB ";
                break;
            case java.sql.Types.LONGVARCHAR:
                query1 = "TEXT";
                break;
            case java.sql.Types.NUMERIC:
                query1 = "NUMERIC";
                break;
            case java.sql.Types.SMALLINT:
                query1 = "INTEGER";
                break;
            case java.sql.Types.TIME:
                query1 = " TIME ";
                break;
            case java.sql.Types.TIMESTAMP:
                query1 = " TIMESTAMP ";
                break;
            case java.sql.Types.TINYINT:
                query1 = "INTEGER";
                break;
            case java.sql.Types.VARBINARY:
                query1 = " VARCHAR BINARY ";
                break;
            case java.sql.Types.VARCHAR:
            default:
                query1 = "TEXT";
        }
        return query1;


    }

    public static String getAsString(Column col) {
        return getAsString(col.getType(), col.getSize());
    }

    /**
     For columns that don't have a size
     */
    public static String getAsString(int type) {
        return getAsString(type, 0);
    }

}
