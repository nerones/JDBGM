/**
 * 
 */
package com.nelsonx.jdbgm;

import com.crossdb.sql.SQLFactory;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class ManagerFactory {
	
	public static final int MYSQL_DB = 1;
	public static final int SQLITE_DB = 2;
	public static final int POSTGRE_DB = 3;
	public static int currentVendor; 
	public static GenericManager manager;
	public static SQLFactory sqlFactory;
	public static boolean isRegistered = false;
	
	
	public static GenericManager getManager(int vendor,String user, String location, String password) throws JDException{
		switch (vendor) {
		case MYSQL_DB:
			manager = new MySqlManager(location, user, password);
			break;
		case SQLITE_DB:
			manager = new SQLiteManager(location, user, password);
			break;
		case POSTGRE_DB:
			manager = new PostgreSQLManager(location, user, password);
			break;
		default:
			return null;
		}
		isRegistered = true;
		currentVendor = vendor;
		sqlFactory = SQLFactory.getFactory();
		return manager;
	}
	
	public static SQLFactory getSQLFactory(){
		return sqlFactory;
	}

}
