/**
 * This resultset will format results to be consistent with crossdb spec.
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: May 30, 2002
 * Time: 2:29:42 PM
 * 
 */
package com.thinkvirtual.sql.sybase;

import com.crossdb.sql.DefaultResultSet;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Calendar;

public class SybaseResultSet extends DefaultResultSet {
	public SybaseResultSet(ResultSet rs) {
		super(rs);
	}

	/**
	 * SQL Server returns date with time.  to follow spec we'll
	 * just return date with 0:00 time
	 *
	 * To conform with the definition of SQL DATE, the millisecond values wrapped by a java.sql.Date instance must be 'normalized' by setting the hours, minutes, seconds, and milliseconds to zero in the particular time zone with which the instance is associated.
	 */
	public Date getDate(int columnIndex) throws SQLException {
		Date d = super.getDate(columnIndex);
		return normalize(d);

	}

	/**
	 * @see #getDate(int)
	 */
	public Date getDate(String columnName) throws SQLException {
		Date d = super.getDate(columnName);
		return normalize(d);
	}

	Date normalize(Date d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new Date(cal.getTime().getTime());

	}
}
