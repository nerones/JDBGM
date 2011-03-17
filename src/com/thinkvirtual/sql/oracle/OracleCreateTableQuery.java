package com.thinkvirtual.sql.oracle;

/**
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.crossdb.sql.CreateTableQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleCreateTableQuery extends DefaultCreateTableQuery {


	Statement stmt;



	/**
	 For debugging purposes only, may not execute exactly as shown.
	 */
	public String toString(){

		String query1;
		String extra = "";
		boolean add_primary_key_fx = false; // used in sap db where primary key is specified in a function
		query1 = " CREATE TABLE " + name + " ( "; //"CREATE TABLE IF NOT EXISTS " + table_name + " ( ";
		for(int j =0; j < columns.size(); j++){
			Column df = (Column)(columns.get(j));
			query1 += df.getName() + " ";
			if(df.getType() == java.sql.Types.DATE){
				query1 += " DATE ";

			}
			else if(df.getType() == java.sql.Types.INTEGER){
				query1 += " INT ";
			}
             else if (df.getType() == java.sql.Types.BIGINT) {
				query1 += " BIGINT ";
			}
			else if(df.getType() == java.sql.Types.VARCHAR){
				//if(vendor != MS_SQL_SERVER){
				if(df.getSize() > 255){
					df.setSize(255);
				}
				//}
				query1 += " varchar2(" + df.getSize() + ") ";
			}
			else if(df.getType() == java.sql.Types.BIT){
				// sap uses boolean instead
				query1 += " NUMBER(1) "; //getBitRepresentation(vendor); from DatabaseVendors
			}
			else if(df.getType()  == java.sql.Types.LONGVARCHAR){
				query1 += " LONG ";
			}
			else if(df.getType() == java.sql.Types.FLOAT){ // sql server thing
				query1 += " float ";
			}

			else query1 += " varchar2(" + df.getSize() + ") "; // default for unknown types

			if(df.isAutoIncrement()){
				query1 += " PRIMARY KEY ";
				// create sequence for this table now
				extra += "\n-- " + createSequenceString(df);

			}

			if(df.getDefaultValue() != null){
				// "datetime")){
				// Can't use functions like Now() in defaults in mysql
				query1 += " DEFAULT " + df.getDefaultValue();



			}
			if(df.isNullable() == 0){
				query1 += " NOT NULL ";
			}

			if(j < columns.size()-1){
				query1 += ", ";
			}
		}
		query1 += " ) " + extra;

		return query1;
	}


	

    /**
     *
     * @param stmt
     * @throws SQLException
     */
	public void execute(java.sql.Statement stmt) throws SQLException {
		//q = new 	Query(conn);
		this.stmt = stmt;
		String query1;
		boolean add_primary_key_fx = false; // used in sap db where primary key is specified in a function
		query1 = " CREATE TABLE " + name + " ( "; //"CREATE TABLE IF NOT EXISTS " + table_name + " ( ";
		for(int j =0; j < columns.size(); j++){
			Column df = (Column)(columns.get(j));
			query1 += df.getName() + " ";
			if(df.getType() == java.sql.Types.TIMESTAMP || df.getType() == java.sql.Types.DATE){
				query1 += " DATE ";

			}
			else if(df.getType() == java.sql.Types.INTEGER){
				query1 += " INT ";
			}
			else if(df.getType() == java.sql.Types.VARCHAR){
				//if(vendor != MS_SQL_SERVER){
				/*if(df.getSize() > 255){
					df.setSize(255);
				 }*/
				//}
				query1 += " varchar(" + df.getSize() + ") ";
			}
			else if(df.getType() == java.sql.Types.BIT){
				// sap uses boolean instead
				query1 += " NUMBER(1) "; //getBitRepresentation(vendor); from DatabaseVendors
			}
			else if(df.getType() == java.sql.Types.CHAR){
				query1 += " CHAR(" + df.getSize() + ") ";
			}
			else if(df.getType()  == java.sql.Types.LONGVARCHAR){
				query1 += " LONG ";
			}
			else if(df.getType() == java.sql.Types.FLOAT){ // sql server thing
				query1 += " float ";
			}

			else query1 += " varchar(" + df.getSize() + ") "; // default for unknown types

			if(df.isAutoIncrement()){
				query1 += " PRIMARY KEY ";
				// create sequence for this table now
				createSequence(df);

			}

			if(df.getDefaultValue() != null){
				// "datetime")){
				// Can't use functions like Now() in defaults in mysql
				query1 += " DEFAULT " + df.getDefaultValue();



			}
			if(df.isNullable() == 0){
				query1 += " NOT NULL ";
			}


			if(j < columns.size()-1){
				query1 += ", ";
			}
		}
		query1 += " ) ";
		stmt.executeUpdate(query1);
		//return query1;
	}

	void createSequence(Column col) throws SQLException{
		String seq_name = col.getSequence();
		if(seq_name == null){
			// default to table_name + col_name + '_seq'
			seq_name = name + /*"_" +col.getName() + */ "_seq"; // with col.getName() it is too long..
		}
		String query1 = "CREATE SEQUENCE " + seq_name;
		stmt.executeUpdate(query1);


	}
	String createSequenceString(Column col) {
		String seq_name = col.getSequence();
		if(seq_name == null){
			// default to table_name + col_name + '_seq'
			seq_name = name + /*"_" + col.getName() + */"_seq";
		}
		String query1 = "CREATE SEQUENCE " + seq_name;
		return query1;


	}


}
