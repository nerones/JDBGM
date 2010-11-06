/**
 * For helping with oracle issues.
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 4:50:10 PM
 * 
 */
package com.thinkvirtual.sql.oracle;

public class OracleHelper {
	static final String placeHolder = " ";

	/**
	 * processes a string before inserting or updating.
	 *
	 * 1. inserts a space if empty string to avoid oracle treating it as null
	 *
	 * @see OracleInsertQuery#execute(Statement)
	 * @see OracleUpdateQuery#execute(Statement)
	 */
	public static String processString(String s){
		if(s.length() == 0){ // FIX ORACLE EMPTY STRING PROBLEM
			return placeHolder;
		}
		else return s;
	}
}
