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
package com.nelsonx.jdbgm;

import com.crossdb.sql.SQLFactory;

/**
 * Clase fabrica de las clases que manejan el acceso a la base de datos.
 * 
 * @author Nelson Efrain A. Cruz
 * @since 0.5
 */
public class ManagerFactory {
	/**
	 * Identifica el DBMS proveído por MySQl.
	 */
	public static final int MYSQL_DB = 1;
	/**
	 * Identifica el DBMS proveído por SQLite.
	 */
	public static final int SQLITE_DB = 2;
	/**
	 * Identifica el DBMS proveído por PostgreSQL.
	 */
	public static final int POSTGRE_DB = 3;
	/**
	 * Almacena al identificador de el proveedor del DBMS, que esta identificado
	 * por las constantes definidas en esta clase.
	 */
	public static int currentVendor; 
	/**
	 * Almacena la referencia hacia la instancia de GenericManager que se creo, 
	 * de modo que siempre se use la misma.
	 */
	private static GenericManager manager;
	/**
	 * La referencia a la instancia de SQLFactory, fabrica de sentencias que
	 * corresponde a el DBMS que se haya registrado (se este usando)
	 */
	private static SQLFactory sqlFactory;
	/**
	 * Bandera que indica si se registro algún DBMS.
	 */
	public static boolean isRegistered = false;
	
	/**
	 * Método que registra el DBMS que se esta por usar para poder crear la instancia
	 * correcta de GenericManager. Solo se puede registrar el DBMS una sola vez
	 * durante la vida de la clase, si se intenta registrar por segunda vez el
	 * método producirá un error de tiempo de ejecución. Implementación (similar)
	 * a el Patrón <code> Factory method</code>
	 * 
	 * @param vendor El identificador de el proveedor (vendor) del DBMS. 
	 * @param user El nombre de usuario que se debe usar para acceder a el DBMS.
	 * @param location La ubicación de el DBMS, un URI.
	 * @param password La contraseña que se debe utilizar para acceder a el DBMS.
	 * @return La instancia de la implementación adecuada de GenericManager.
	 * @throws JDException Si es que no se pudo realizar la conexión con el DBMS.
	 */
	public static GenericManager getManager(int vendor,String user, String location, String password) throws JDException{
		if (isRegistered) throw new RuntimeException("No se puede registrar mas de una vez el DBMS");
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
			throw new RuntimeException("Unkonw vendor for DBMS vendor");
		}
		isRegistered = true;
		currentVendor = vendor;
		sqlFactory = SQLFactory.getFactory();
		return manager;
	}
	
	/**
	 * Este método se puede usar una vez que se a registrado un DBMS con {@link #getManager(int, String, String, String)}
	 * para obtener una referencia a la implementación de GenericManager que se haya creado. Si no se registro
	 * ningún DBMS el método producirá un error.
	 * @return La instancia de la implementación adecuada de GenericManager.
	 */
	public static GenericManager getManager(){
		if (!isRegistered) throw new RuntimeException("Imposible obtener una instancia de GenericManager" +
				" no se a registrado el DBMS");
		return manager;
	}
	/**
	 * Obtiene la implementación adecuada de la fabrica de sentencias {@link SQLFactory}.
	 * @return Una instancia de la fabrica de sentencias acorde al DBMS registrado.
	 * @see SQLFactory
	 */
	public static SQLFactory getSQLFactory(){
		return sqlFactory;
	}

}
