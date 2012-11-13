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
package com.crossdb.sql;

import com.nelsonx.jdbgm.GenericManager;
import com.nelsonx.jdbgm.ManagerFactory;
import com.nelsonx.postgre.PostgreSQLFactory;
import com.nelsonx.sqlite.SQLiteFactory;
import com.spaceprogram.sql.mysql.MySQLFactory;
 

/**
 * Implementación del patron {@code Abstract Factory} para los diferentes tipos
 * de implementaciones especificas de las sentencias. Para crear la fabrica adecuada
 * primero se debe tener que haber registrado el tipo de motor con el que se quiere
 * trabajar mediante la clase {@link GenericManager}, si no se hace esto esta fabrica
 * no podrá decidir la implementación que debe instanciar por lo que se producirá un
 * error de tiempo de compilación. El método de clase {@link #getSelectQuery()} es el
 * encargado de decidir que implementación a de crear por lo que lo único que deben implementar
 * las clases hijas de estas son los otros métodos get.
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 */
public abstract class SQLFactory {
	
	/**
	 * Contiene la instancia de la implementación de esta clase que se esta usando
	 * la intención es que sea única.
	 */
	private static SQLFactory currentFactory;
	/**
	 * Indica si el modo debug esta activo, por ahora solo servira para poder usar
	 * esta fabrica sin haber registrado un motor.
	 */
	private static boolean debugMode = false;
	/**
	 * Obtiene la implementación de SQLFactory que se le indique por parametro a
	 * fines de realizar pruebas sin necesidad de tener que registrar un DBMS.
	 * Si se intenta usar {@link #getFactory()} antes de llamar a {@link #debugModeOff()}
	 * se producirá un error.
	 * @param vendor
	 * @return
	 */
	public static SQLFactory debugGetFactory(int vendor){
		debugMode = true;
		switch (vendor) {
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
	
	public static void debugModeOff(){
		currentFactory = null;
		debugMode = false;
	}
	
	
	/**
	 * Crea si es que no existe la instancia de la fabrica adecuada revisando cual es
	 * el DBMS que se registro en {@link GenericManager} y dependiendo de ello elige el tipo
	 * de la instancia que debe crear, si es que no se registro ningún motor todavía este método producirá
	 * un error de tiempo de ejecución.
	 * 
	 * @return Una implementación de esta clase.
	 */
	public static final SQLFactory getFactory(){
		if (!ManagerFactory.isRegistered) throw new RuntimeException("No se registro el manejador de conexiones, " +
				"no se puede crear la fabrica de objetos");
		if (debugMode) throw new RuntimeException("El modo debug esta activo no se puede continuar");
		//Si el método ya fue llamado, siempre entregara el mismo objeto
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
	
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code INSERT}.
	 */
	public abstract InsertQuery getInsertQuery();
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code SELECT}.
	 */
	public abstract SelectQuery getSelectQuery();
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code UPDATE}.
	 */
	public abstract UpdateQuery getUpdateQuery();
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code DELETE}.
	 */
	public abstract DeleteQuery getDeleteQuery();
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code ALTER TABLE}.
	 */
	public abstract AlterTableQuery getAlterTableQuery();
	/**
	 * Crea una objeto que representa una sentencia del tipo {@code CREATE TABLE}.
	 */
	public abstract CreateTableQuery getCreateTableQuery();
	/**
	 * Crea una objeto que representa una clausula del tipo {@code WHERE...}.
	 */
	public abstract WhereClause getWhereClause();

}
