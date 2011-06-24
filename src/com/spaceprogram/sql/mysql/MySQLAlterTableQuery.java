/**
 *
 *
 * This is an initial beta of a class that will represent a query string
 *
 * @author Travis Reeder - travis@spaceprogram.com
 */

package com.spaceprogram.sql.mysql;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultAlterTableQuery;

public class MySQLAlterTableQuery extends DefaultAlterTableQuery {

	public MySQLAlterTableQuery() {
		super(new MySQLDataTypes());
	}

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
