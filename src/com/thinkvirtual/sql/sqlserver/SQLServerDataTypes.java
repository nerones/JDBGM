/**
 This will basically be a datatype conversion from or two java.sql.Types
 to the equivalent text for the db.

 Need to convert for create tables and for alter tables.
 
 */

package com.thinkvirtual.sql.sqlserver;

import com.crossdb.sql.*;
public class SQLServerDataTypes{
	
	/**
	 */
	public static String getAsString(int type, int size){
		String query1;
		if(type == java.sql.Types.TIMESTAMP || type == java.sql.Types.DATE){
			query1 = " datetime ";
			
		}
		else if(type == java.sql.Types.INTEGER){
			query1 = " INT ";
		}
		else if(type == java.sql.Types.VARCHAR){
			//if(vendor != MS_SQL_SERVER){
			/*if(size> 255){
			 size = 255;
			 }*/
			//}
			query1 = " varchar(" + size + ") ";
		}
		else if(type == java.sql.Types.BIT){
			// sap uses boolean instead
			query1 = " bit "; //getBitRepresentation(vendor); from DatabaseVendors
		}
		else if(type == java.sql.Types.CHAR){
			query1 = " CHAR(" + size + ") ";
		}
		else if(type  == java.sql.Types.LONGVARCHAR){
			query1 = " text ";
			
		}
		else if(type == java.sql.Types.FLOAT){ // sql server thing
			query1 = " float ";
		}
			
		else query1 = " varchar(" + size + ") "; // default for unknown types
		return query1;
		
		
	}
	public static String getAsString(Column col){
		return getAsString(col.getType(), col.getSize());
	}
	
	/**
	 For columns that don't have a size
	 */
	public static String getAsString(int type){
		return getAsString(type, 0);
	}
}


