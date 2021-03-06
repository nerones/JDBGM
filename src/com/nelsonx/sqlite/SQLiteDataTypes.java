/**
 * Copyright 2011 Nelson Efrain Abraham Cruz
 *
 * This file is part of JDBGM.
 * 
 * JDBGM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDBGM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JDBGM.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.nelsonx.sqlite;

import java.sql.Types;

import com.crossdb.sql.DataTypes;

/**
 * Implementación especifica de la clase {@link DataTypes} para el
 * DBMS SQLite.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class SQLiteDataTypes extends DataTypes{
	
	public String getAsString(int type, int size ){
    	
    	switch (type) {
		case Types.SMALLINT:
			return "INTEGER";
		case Types.INTEGER:
			return "INTEGER";
		case Types.BIGINT:
			return "INTEGER";
		
		case Types.REAL:
			return "REAL";
		case Types.DOUBLE:
			return "REAL";
		
		case Types.NUMERIC:
			return "NUMERIC";
		case Types.DECIMAL:
			return "NUMERIC";
		case Types.TIMESTAMP:
			return "NUMERIC";
		case Types.DATE:
			return "NUMERIC";
		case Types.TIME:
			return "NUMERIC";
		
		case Types.CHAR:
			return "TEXT";
		case Types.VARCHAR:
			return "TEXT";
		//TODO missing type TEXT
		
		case Types.BOOLEAN:
			return "TINYINT";

		default:
			throw new RuntimeException("No se reconoce el valor para el parametro type " +
					"No se puede encontrar el tipo de dato");
		}
    }
	
	@Deprecated
	public String getAsString_OLD(int type, int size) {
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
}