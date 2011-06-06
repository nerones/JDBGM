package com.nelsonx.postgre;

import java.util.ArrayList;

import com.crossdb.sql.Column;
import com.crossdb.sql.DataTypes;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.spaceprogram.sql.mysql.MySQLDataTypes;

public class PostgreSQLCreateTableQuery extends DefaultCreateTableQuery {

	/**
	 * @param datatype
	 */
	public PostgreSQLCreateTableQuery() {
		super(new PostgreSQLDataTypes());
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String query1;
		query1 = "CREATE TABLE " + name + " ( "; // "CREATE TABLE IF NOT EXISTS "
													// + table_name + " ( ";
		for (int j = 0; j < columns.size(); j++) {
			Column df = (Column) (columns.get(j));
			query1 += df.getName() + " ";

			query1 += datatype.getAsString(df);

			// if (df.isAutoIncrement()) {
			// //una tabla de mysql solo puede tener una columna auto_increment
			// query1 += " AUTO_INCREMENT";// primary key NOT NULL";
			if (df.isUnique()) query1 += " UNIQUE";
			if (df.isPrimaryKey() && !isCompositePK()){
				query1 += " PRIMARY KEY";
				//TODO auto increment omitido por ahora
				//System.out.println();
				//if (df.isAutoIncrement()) query1 += " AUTO_INCREMENT";
			} 
			if (df.isNullable() == 0)
				query1 += " NOT NULL";
			if (df.getDefaultValue() != null) {
				if (!(df.getType() == java.sql.Types.TIMESTAMP)) {// "datetime")){
					// Can't use functions like Now() in defaults in mysql
					query1 += " DEFAULT " + df.getDefaultValue();
				}

			}

			if (j < columns.size() - 1) {
				query1 += ", ";
			}

		}
		if (isCompositePK())
			query1 += ", PRIMARY KEY (" + getCompositePK()+")";
		if (hasFK) {
			ArrayList<String[]> fks = getForeignKeys();
			for (String[] strings : fks) {
				query1 += ", FOREIGN KEY ("+strings[1]+") REFERENCES "+strings[0]+"("+strings[2]+")";
			}
		}
		// we add the foreign key or primary key definition if we have then

		query1 += " )";


            // MYSQL already does this so skip it.
            /*else if(auto_defaults){ // set defaults automatically

             if(df.isNullable() == 0){ //not nullable
             if(!df.isAutoIncrement()){
             if(vendor != MY_SQL){

             if(df.getType().equalsIgnoreCase("bit")){
             if(vendor == SAPDB){
             query1 += " DEFAULT FALSE ";
             }
             else{
             query1 += " DEFAULT (0) ";
             }

             }
             else if(df.getType().equalsIgnoreCase("int")){
             if(vendor == SAPDB){
             query1 += " DEFAULT 0 ";
             }
             else{
             query1 += " DEFAULT (0) ";
             }
             }
             else if(df.getType().equalsIgnoreCase("datetime")){
             if(vendor == SAPDB){
             query1 += " DEFAULT TIMESTAMP ";
             }
             else{
             query1 += " DEFAULT (getDate()) ";
             }
             }

             }
             }
             else{ // is autoinc
             // sapdb needs to set default on primary keys
             if(vendor == SAPDB){
             query1 += " DEFAULT SERIAL ";
             }
             }
             }


             }
             */

       
        return query1;
	}

}
