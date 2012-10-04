
package com.crossdb.sql;

/**
 * default update query that other implementations can  and must extend
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.3
 */
public abstract class DefaultUpdateQuery extends UpdateQuery {

	protected String table;

	protected WhereClause wclause = null;
	//List order_by; // ORDER BY order_by
	//int limit[]; // 2 max that will be offset, count


	public DefaultUpdateQuery(Formatter formatter) {
		super(formatter);
	}


	public void setTable(String table) {
		this.table = table;
	}
	
	public WhereClause addWhere(){
		if (wclause == null) wclause = new WhereClause(formatter);
		return wclause;
	}

    public void addToColumn(String column, int valueToAdd) {
        addColumnNoAlter(column, column + " + (" + valueToAdd + ")");
    }

    public void addToColumn(String column, double valueToAdd) {
        addColumnNoAlter(column, column + " + (" + valueToAdd + ")");
    }

    public void incrementColumn(String column) {
        addToColumn(column, 1);
    }
    //TODO falta probar con los tipos de datos.
    
    
    
}
