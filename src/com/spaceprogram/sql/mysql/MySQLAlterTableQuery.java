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
package com.spaceprogram.sql.mysql;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultAlterTableQuery;

/**
 * Implementación especifica de la clase {@link DefaultAlterTableQuery} para el
 * DBMS MySQL.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class MySQLAlterTableQuery extends DefaultAlterTableQuery {

	MySQLAlterTableQuery() {
		super(new MySQLDataTypes());
	}

	public String toString() {
		String ret = "ALTER TABLE " + table + " ";
		if (isAlterTableName()) {
			ret += "RENAME TO " + newTableName;
			return ret;
		} else {
			for (Column col : adds) {
				ret += "ADD COLUMN " + columnToString(col);
				ret += ",";
			}
			ret = ret.substring(0, ret.length() - 1);
			return ret;
		}
	}

}