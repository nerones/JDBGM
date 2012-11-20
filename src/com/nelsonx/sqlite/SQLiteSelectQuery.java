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
package com.nelsonx.sqlite;

import com.crossdb.sql.DefaultSelectQuery;
import com.crossdb.sql.Formatter;

/**
 * Implementación especifica de {@link DefaultSelectQuery} para el DBMS SQLite.
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class SQLiteSelectQuery extends DefaultSelectQuery {

	SQLiteSelectQuery(Formatter formatter) {
		super(formatter);
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
