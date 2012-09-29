/**
 */
package com.crossdb.sql;

/**
 * Implementación base de la interfaz {@link DeleteQuery} de la cual se debe heredar
 * para hacer cualquier implementación especifica para algún motor de base de datos.
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 *
 */
public abstract class DefaultDeleteQuery implements DeleteQuery {

	protected String table;

	protected WhereClause wclause = new WhereClause();

	public DefaultDeleteQuery() {

	}


	public void setTable(String table) {
		this.table = table;
	}
	
	public WhereClause addWhere(){
		if (wclause != null) wclause = new WhereClause();
		return wclause;
	}

	
	

	public String toString() {
		String ret = "DELETE FROM " + table;
		if (wclause != null) {
		ret += wclause.toString();//" WHERE ";
		}
		return ret;
	}
	
	
}
