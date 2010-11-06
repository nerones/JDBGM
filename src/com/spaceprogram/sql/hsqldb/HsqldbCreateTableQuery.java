package com.spaceprogram.sql.hsqldb;

/**
 *
 * @author  prophecy
 * @version
 */

/** <p>Revision 1: add primary keys and foreign keys support</p>
@author Jorge P&eacute;rez Burgos
@author jorge.perez@adaptia.net
@author http://www.adaptia.net
@author	(C) 2003
@version 0.2
*/

import com.crossdb.sql.*;
import java.util.*;


public class HsqldbCreateTableQuery extends DefaultCreateTableQuery implements CreateTableQuery {
	
	boolean haveIdentityField=false;
	
	public String getConstraintExpression(){
		String expression="";
		String PKExpression=null;
		String FKExpression=null;
		Map foreignTables=new HashMap();
		String FKExpressionTemp=null;
		int indexReferencesKeyword=0;
		
		for(int j=0;j<columns.size();j++){
			Column df = (Column)(columns.get(j));
			if(!haveIdentityField){
				if (df.isPrimaryKey()){
					if(PKExpression==null){
						PKExpression=" PRIMARY KEY ("+df.getName();
					}else{ 
						PKExpression+=", "+df.getName();
					}
				}
			}
			
			if(df.isForeignKey()){
				FKExpressionTemp=(String)foreignTables.get(df.getForeignTable());
				if(FKExpressionTemp==null){
					foreignTables.put(df.getForeignTable()," FOREIGN KEY ("+df.getName()+") REFERENCES "+df.getForeignTable()+" ("+df.getForeignPrimaryKey());
				}else{
					indexReferencesKeyword=FKExpressionTemp.indexOf(") REFERENCES");
					FKExpressionTemp=FKExpressionTemp.substring(0,indexReferencesKeyword)+", "+df.getName()+
												FKExpressionTemp.substring(indexReferencesKeyword,FKExpressionTemp.length());
					foreignTables.put(df.getForeignTable(),FKExpressionTemp+", "+df.getForeignPrimaryKey());
				}
			}
		}
		
		if(PKExpression!=null){
			PKExpression+=")";
			expression=","+PKExpression;
		}
		
		if(foreignTables.size()!=0){
			Iterator iterator=foreignTables.values().iterator();
			
			while(iterator.hasNext()){
				expression+=", "+(String)iterator.next()+")";
			}
			
		}
		
		return expression;
	}

	public String toString(){
		
		String query1;
		boolean constraintDefinition=false;
		//In HypersonicSQL databases if one field is declared IDENTITY (autoincrement feature in crossdb)
		//this field will be the primary key of the table and will be unique. there must be only identity field per table
		//in HypersonicSQL databases.
		haveIdentityField=false;
		
		query1 = " CREATE TABLE " + name + " ( "; //"CREATE TABLE IF NOT EXISTS " + table_name + " ( ";
		for(int j =0; j < columns.size(); j++){
			Column df = (Column)(columns.get(j));
			query1 += df.getName() + " ";

			query1 += HsqldbDataTypes.getAsString(df);

			if(df.isAutoIncrement()){
				haveIdentityField=true;
				query1 += " IDENTITY ";
			}
			else{
				if(df.isNullable() == 1){
					query1 += " NULL ";
				}
				else query1 += " NOT NULL ";
				if(df.getDefaultValue() != null){
					if(!(df.getType() == java.sql.Types.TIMESTAMP)){// "datetime")){
						// Can't use functions like Now() in defaults in mysql
						query1 += " DEFAULT " + df.getDefaultValue();
					}
				}
			}
			
			// if we have a table with primary or foreign keys ...
			if(!constraintDefinition){
				if((df.isPrimaryKey()&&!haveIdentityField) || df.isForeignKey())
					constraintDefinition=true;
			}
			
			if(j < columns.size()-1){
				query1 += ", ";
			}
		}
		// we add the foreign key or primary key definition if we have then
		if(constraintDefinition)
			query1 += getConstraintExpression();
		query1 += " ) ";
		return query1;
	}

}
