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
import com.crossdb.sql.DefaultInsertQuery;
import com.crossdb.sql.Formatter;

/**
 * Implementación especifica de {@link DefaultInsertQuery} para el DBMS SQLite.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class SQLiteInsertQuery extends DefaultInsertQuery {

	SQLiteInsertQuery(Formatter formatter) {
		super(formatter);
	}

	@Override
	public String toString() {
		String query2 = "INSERT INTO " + table ;//+ " ( ";
		if (isFromDefault()){
			query2 += " DEFAULT VALUES";
			return query2;
		}
		query2 += " (";
		String query2b = ") VALUES (";
		for(Column col: columns){
			query2 += col.getName() + ", ";
			query2b += getFormatter().valueAsString(col) + ", ";
		}
		query2 = query2.substring(0,query2.length() - 2);
		query2b = query2b.substring(0,query2b.length() - 2);
		query2b += ")";
		
		if (isFromSelect()){
			query2 += ") " + getSelectStmt().toString();
			return query2;
		} else {

			return query2 + query2b;
		}		
	}

}