package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DataTypes;
import com.crossdb.sql.DefaultAlterTableQuery;
import com.spaceprogram.sql.mysql.MySQLDataTypes;

public class PostgreSQLAlterTableQuery extends DefaultAlterTableQuery {

	/**
	 * @param datatype
	 */
	public PostgreSQLAlterTableQuery() {
		super(new PostgreSQLDataTypes());
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String ret = "ALTER TABLE " + table + " ";

		for (int i = 0; i < adds.size(); i++) {
			Column col = (Column) (adds.get(i));
			ret += "ADD " + columnToString(col);
			ret += ",";
		}
		ret = ret.substring(0, ret.length() - 1);
		return ret;
	}

}
