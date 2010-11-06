/**
 * java.sql.Time formatting
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Oct 24, 2002
 * Time: 3:21:21 PM
 * 
 */
package com.crossdb.sql;
public class SQLTimeFormat extends java.text.SimpleDateFormat {

	public SQLTimeFormat(){
		super("HH:mm:ss"); //yyyy.MM.dd G 'at' hh:mm:ss
	}
}
