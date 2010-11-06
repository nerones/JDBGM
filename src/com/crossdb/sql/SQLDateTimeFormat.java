/**
 * for DATETIME or TIMESTAMP columns, might need a timestamp Format too maybe?
 *
 * This class is a formatter class for representing a valid ANSI SQL Date.
 *
 * This should be used in most crossdb implementations on inserts and updates
 * of dates.
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Oct 24, 2002
 * Time: 3:37:50 PM
 * 
 */
package com.crossdb.sql;
public class SQLDateTimeFormat extends java.text.SimpleDateFormat {

	public SQLDateTimeFormat(){
		super("yyyy-MM-dd HH:mm:ss"); //yyyy.MM.dd G 'at' hh:mm:ss
	}
}