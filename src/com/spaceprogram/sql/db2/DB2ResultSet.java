package com.spaceprogram.sql.db2;


import com.crossdb.sql.DefaultResultSet;

import java.sql.*;

public class DB2ResultSet extends DefaultResultSet implements ResultSet {



	public DB2ResultSet(ResultSet rs){
		super(rs);
	}

/*	public Date getDate(String columnName) throws SQLException
	{
		java.util.Date ret;
		java.sql.Date rs_date = rs.getDate(columnName);
		java.sql.Time rs_time = rs.getTime(columnName);
		long fulldate = rs_date.getTime() + rs_time.getTime();
		ret = new java.util.Date(fulldate);
		// set zone_offset to zero
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(ret);
		cal.set(java.util.Calendar.ZONE_OFFSET, 0);
		rs_date.setTime(cal.getTime().getTime());
		return rs_date;
	}*/

	
}
