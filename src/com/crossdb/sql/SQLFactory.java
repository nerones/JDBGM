/**
 * 
 */
package com.crossdb.sql;

import com.spaceprogram.sql.mysql.MySQLFactory;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class SQLFactory {
	
	public static final int MYSQL_DB = 1;
	public static final int SQLITE_DB = 2;
	public static final int POSTGRE_DB = 3;
	
	public static SpecificSQLFactory getFactory(int vendor){
		switch (vendor) {
		case MYSQL_DB:
			return new MySQLFactory();
		default:
			return null;
		}
	}

}
