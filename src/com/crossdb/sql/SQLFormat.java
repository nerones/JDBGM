/**
 *
 * For formatting and parsing strings.
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: May 3, 2002
 * Time: 12:16:55 AM
 * 
 */
package com.crossdb.sql;

public class SQLFormat {
	/**
	 * Escapes a string for using with db,
	 *
	 * replaces single quotes with double singles.
	 */
	public static String escape(String s) {
		StringBuffer sb = new StringBuffer(s);

		char c;
		for (int i = 0; i < sb.length(); i++) {
			c = sb.charAt(i);
			if (c == '\'') {
				sb.insert(i, '\'');
				i += 1;
			} else if (c == '\\') {
				sb.insert(i, '\\');
				i += 1;
			}
		}
		return sb.toString();
		/*int index1 = s.indexOf("'");
		String s1;
		String s2;
		boolean is_string = true;
		//String newstr;
		while(index1 >= 0){

			s1 = s.substring(0,index1); // first half
			s2 = s.substring(index1); // second half
			//System.out.println("\n\ns2 = \n" + s2);
			s = s1 + "'" + s2;
			//index = index1;
			index1 = s.indexOf("'", index1 + 1);
			//System.out.println(s);
		}
		return s;*/
	}
}
