/**
 * 
 * @author Travis reeder
 * Date: Jan 13, 2003
 * Time: 3:37:08 PM
 * @version 0.1
 */
package com.crossdb.sql;

import java.util.Date;

public class DefaultFormatter implements Formatter {
   /** Returns a string for inserting into db. */
    public String format(Date d){

        SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
		return sqldf.format(d);
	}

    /**
     * escapes the string to be inserted into db
     * @param s
     * @return
     */
    public String format(String s) {
        return "'" + SQLFormat.escape(s) + "'";
    }
}
