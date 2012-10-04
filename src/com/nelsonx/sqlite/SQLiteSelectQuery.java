package com.nelsonx.sqlite;

import com.crossdb.sql.DefaultSelectQuery;
import com.crossdb.sql.Formatter;

public class SQLiteSelectQuery extends DefaultSelectQuery {

	public SQLiteSelectQuery(Formatter formatter) {
		super(formatter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sentenceAsSQL() {
		//TODO revisar contra la documentación de sqlite
		StringBuilder ret = new StringBuilder(1024);
        ret.append("SELECT");
        if(isDistinct()){
            ret.append(" DISTINCT");
        }
		if(columns == null || columns.length() == 0){
			ret.append(" *");
		} else ret.append(' ').append(columns);
		if (table != null){
			ret.append(" FROM ").append(table);
		} else {
			ret.append(" FROM (").append(tableFromSelect.toString()).append(") AS ").append(aliasForTableFromSelect);
		}
		if (join != null) ret.append(join.toString());
		if (wclause != null) ret.append(wclause.toString());		
		if(groupByExpresion != null) {
			ret.append(" GROUP BY ");
			ret.append(groupByExpresion);
		}
		if(havingExpresion != null) {
			ret.append(" HAVING ");
			ret.append(havingExpresion);
		}
		
		if (union != null) {
			ret.append(" UNION ");
			if (isUnionAll) ret.append(" ALL ");
			ret.append(union.toString());
		}
		
		if(orderByExpresion != null ){
			ret.append(" ORDER BY ").append(orderByExpresion);
		}
		if(rowlimit > -1){
			ret.append(" LIMIT ").append(rowlimit);
			if ( rowoffset > -1) ret.append(" OFFSET ").append(rowoffset);
		}
		
		return ret.toString();
	}

}
