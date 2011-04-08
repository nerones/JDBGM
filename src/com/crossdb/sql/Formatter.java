package com.crossdb.sql;

import java.util.Date;

/**
 *
 * This will be on an implementation basis, the Formatter is retrieved/created
 * from the factory for the specific implementation.
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 10, 2002
 * Time: 9:43:43 PM
 * 
 */
public interface Formatter {
	/** Formats a date to be used in a query string. */
	 String format(Date d);

     /**
     * escapes the string to be inserted into db
     * @param s
     * @return
     */
    String format(String s);
}
