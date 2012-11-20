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
import com.crossdb.sql.DefaultAlterTableQuery;

/**
 * Implementación especifica de la clase {@link DefaultAlterTableQuery} para el
 * DBMS SQLite.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class SQLiteAlterTableQuery extends DefaultAlterTableQuery {

	SQLiteAlterTableQuery() {
		super(new SQLiteDataTypes());
	}

	@Override
	public String toString() {
		String ret = "ALTER TABLE " + table + " ";
		if (isAlterTableName()) {
			ret += "RENAME TO " + newTableName;
			return ret;
		} else {
			for (int i = 0; i < adds.size(); i++) {
				Column col = (Column) (adds.get(i));
				ret += "ADD " + columnToString(col);
				ret += ",";
			}
			ret = ret.substring(0, ret.length() - 1);
			return ret;
		}
	}

}
