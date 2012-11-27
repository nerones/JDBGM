package com.nelsonx.postgre;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultAlterTableQuery;

/**
 * Implementación especifica de {@link DefaultAlterTableQuery} para 
 * el DBMS PostgreSQL.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class PostgreSQLAlterTableQuery extends DefaultAlterTableQuery {

	
	PostgreSQLAlterTableQuery() {
		super(new PostgreSQLDataTypes());
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
				ret += "ADD COLUMN " + columnToString(col);
				ret += ",";
			}
			ret = ret.substring(0, ret.length() - 1);
			return ret;
		}
	}

}
