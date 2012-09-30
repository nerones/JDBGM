package com.spaceprogram.sql.mysql;

/** This is an initial beta of a class that will represent a query string */

import com.crossdb.sql.DefaultSelectQuery;
import com.crossdb.sql.Functions;

public class MySQLSelectQuery extends DefaultSelectQuery {


	public MySQLSelectQuery(){
		super();
	}

	public String sentenceAsSQL(){
		StringBuilder ret = new StringBuilder(1024);
        ret.append("SELECT");
        if(isDistinct()){
            ret.append(" DISTINCT");
        }
		if(columns == null || columns.length() == 0){
			ret.append(" *");
		}
		else ret.append(' ').append(columns);
		ret.append(" FROM ").append(table);
		if (join != null) ret.append(join.toString());
		if (wclause != null) ret.append(wclause.toString());		
		if(group_by != null) {
			ret.append(" GROUP BY ");
			ret.append(group_by);
		}
		if(havingExpresion != null) {
			ret.append(" HAVING ");
			ret.append(havingExpresion);
		}
		if(order_by != null ){
			ret.append(" ORDER BY ").append(order_by);
		}
		if(rowlimit > -1){
			ret.append(" LIMIT ").append(rowlimit);
			if ( rowoffset > -1) ret.append(" OFFSET ").append(rowoffset);
		}
		if (union != null) {
			ret.append(" UNION ");
			if (isUnionAll) ret.append(" ALL ");
			ret.append(union.toString());
		}
		return ret.toString();
	}

	/* (non-Javadoc)
	 * @see com.crossdb.sql.DefaultSelectQuery#getFunction(int)
	 */
	@Override
	protected String getFunction(int functionId) {
		// TODO ver si es necesario heredar de la clase Function para obtener el nombre de la función
		return Functions.getFunctionName(functionId);
	}



}
