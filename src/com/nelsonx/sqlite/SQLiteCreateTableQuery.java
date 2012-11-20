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

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.crossdb.sql.TableConstraint;

/**
 * Implementación especifica de la clase {@link DefaultCreateTableQuery} para el
 * DBMS SQLite.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class SQLiteCreateTableQuery extends DefaultCreateTableQuery {

	
	SQLiteCreateTableQuery() {
		// TODO hacer que la clase use un DataType que venga desde parametro
		super(new SQLiteDataTypes());
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