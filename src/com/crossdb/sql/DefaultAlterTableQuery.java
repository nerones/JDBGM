/**
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 8:52:42 PM
 * 
 */
package com.crossdb.sql;

import java.util.ArrayList;
/**
 * Implementación base de {@link AlterTableQuery} de la cual debe heredar cualquier
 * implementación especifica para algún DBMS.
 * @author Nelson Efrain A. Cruz
 *
 */
public abstract class DefaultAlterTableQuery implements AlterTableQuery {

	protected String query1;
	protected String table;
	protected String newTableName;
	protected ArrayList<Column> adds;
	protected DataTypes datatype;

	/**
	 * Constructor que toma el conversor de tipos de datos para poder mapear adecuadamente
	 * los tipos de datos genericos a los de un DBMS en concreto.
	 * @param datatype un conversor de tipos de datos especifico para algún DBMS.
	 */
	public DefaultAlterTableQuery(DataTypes datatype) {
		query1 = "";
		adds = new ArrayList<Column>();
		this.datatype = datatype;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addColumn(Column col) {
		adds.add(col);

	}

	public void newTableName(String table){
		newTableName = table;
	}
	
	public String columnToString(Column column){
		String ret = column.getName() + " "
		+ datatype.getAsString(column);
		if (column.isNullable() == 0) {
			ret += " NOT NULL ";
		}
		/**
		 * should this shiza be in MySQLDataTypes??
		 * 
		 */
		if (column.getDefaultValue() != null) {
			if (column.getType() == java.sql.Types.VARCHAR
					|| column.getType() == java.sql.Types.CHAR) {
				ret += " DEFAULT '" + column.getDefaultValue() + "' ";
			} else
				ret += " DEFAULT " + column.getDefaultValue();
		}
		return ret;
		
	}
	
	public abstract String toString();
	
}
