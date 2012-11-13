package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultAlterTableQuery;

public class PostgreSQLAlterTableQuery extends DefaultAlterTableQuery {

	/**
	 * @param datatype
	 */
	PostgreSQLAlterTableQuery() {
		super(new PostgreSQLDataTypes());
		// TODO Auto-generated constructor stub
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
