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
package com.spaceprogram.sql.mysql;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.UpdateQuery;
import com.crossdb.sql.WhereClause;

/**
 * Implementación especifica de {@link SQLFactory} para el DBMS MySQL.
 * 
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class MySQLFactory extends SQLFactory {
	
	private MySQLFormatter formatter;
	
	public MySQLFactory() {
		formatter = new MySQLFormatter();
	}
	
	public InsertQuery getInsertQuery(){
		return new MySQLInsertQuery();
	}
	
	public SelectQuery getSelectQuery() {
		return new MySQLSelectQuery(formatter);
	}
	
	public UpdateQuery getUpdateQuery(){	
		return new MySQLUpdateQuery(formatter);
	}
	
	public DeleteQuery getDeleteQuery(){
		return new MySQLDeleteQuery(formatter);
	}
	
	public CreateTableQuery getCreateTableQuery(){
		return new MySQLCreateTableQuery();	 
	}

    public AlterTableQuery getAlterTableQuery(){
    	return new MySQLAlterTableQuery();
	}
    
	@Override
	public WhereClause getWhereClause() {
		return new WhereClause(formatter);
	}

}
