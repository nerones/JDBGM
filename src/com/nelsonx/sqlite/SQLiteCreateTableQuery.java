package com.nelsonx.sqlite;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.spaceprogram.sql.mysql.MySQLDataTypes;

public class SQLiteCreateTableQuery extends DefaultCreateTableQuery {

	@Override
	public String toString() {
		String query1;
		query1 = " CREATE TABLE " + name + " ( "; // "CREATE TABLE IF NOT EXISTS "
													// + table_name + " ( ";
		for (int j = 0; j < columns.size(); j++) {
			Column df = (Column) (columns.get(j));
			query1 += df.getName() + " ";

			query1 += MySQLDataTypes.getAsString(df);

			if (df.isAutoIncrement()) {
				query1 += " auto_increment primary key NOT NULL";

			} else {

				if (df.isNullable() == 1) {
					query1 += " NULL ";
				} else
					query1 += " NOT NULL ";
				if (df.getDefaultValue() != null) {
					if (!(df.getType() == java.sql.Types.TIMESTAMP)) {// "datetime")){
						// Can't use functions like Now() in defaults in mysql
						query1 += " DEFAULT " + df.getDefaultValue();
					}

				}
			}

			if (j < columns.size() - 1) {
				query1 += ", ";
			}
		}
		// we add the foreign key or primary key definition if we have then

		query1 += " ) ";


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
