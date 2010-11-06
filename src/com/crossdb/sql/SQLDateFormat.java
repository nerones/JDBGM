/**
 *
 * for java.sql.Types.DATE columns
 *
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Apr 27, 2002
 * Time: 7:27:27 PM
 * 
 */
package com.crossdb.sql;

public class SQLDateFormat extends java.text.SimpleDateFormat {

	public SQLDateFormat(){
		super("yyyy-MM-dd"); //yyyy.MM.dd G 'at' hh:mm:ss
	}
}
