/**
 * 
 */
package com.crossdb.sql;

import com.nelsonx.jdbgm.ManagerFactory;
import com.nelsonx.postgre.PostgreSQLFactory;
import com.nelsonx.sqlite.SQLiteFactory;
import com.spaceprogram.sql.mysql.MySQLFactory;
 

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public abstract class SQLFactory {
	
	private static SQLFactory currentFactory; 
	
	public static SQLFactory getFactory(){
		if (!ManagerFactory.isRegistered) throw new RuntimeException("No se registro el manejador de conexiones, " +
				"no se puede crear la fabrica de objetos");
		//Si el método ya fue llamado por primera vez siempre entregara el mismo objeto
		if (currentFactory != null) return currentFactory;
		//Si todavía no se ejecuto este método se revisa que tipo de fabrica se debe crear
		else {
			switch (ManagerFactory.currentVendor) {
			case ManagerFactory.MYSQL_DB:
				currentFactory = new MySQLFactory(); 
				return currentFactory;
			case ManagerFactory.SQLITE_DB:
				currentFactory = new SQLiteFactory(); 
				return currentFactory;
			case ManagerFactory.POSTGRE_DB:
				currentFactory = new PostgreSQLFactory(); 
				return currentFactory;
			default:
				throw new RuntimeException("Identificador de base de datos desconocido, no existe el valor "+ManagerFactory.currentVendor);
			}
		}
	}
	
	public abstract InsertQuery getInsertQuery();
	public abstract SelectQuery getSelectQuery();
	public abstract UpdateQuery getUpdateQuery();
	public abstract DeleteQuery getDeleteQuery();
	public abstract AlterTableQuery getAlterTableQuery();
	public abstract CreateTableQuery getCreateTableQuery();
	public abstract WhereClause getWhereClause();

}
