/**
 * UpdateQuery for mysql
 *
 * @author Travis Reeder - travis@spaceprogram.com
 */

package com.spaceprogram.sql.mysql;



import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultUpdateQuery;

public class MySQLUpdateQuery extends DefaultUpdateQuery {


	public MySQLUpdateQuery() {

		super(new MySQLFormatter());
	}

//	protected String columnValueToString(Column column){
//		SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
//		Object val = column.getColumnValue();  //values.get(m);
//		String in_val;
//		if (val == null) {
//			in_val = null;
//		}
//		else if (val instanceof String) { // then sql escape and put quotes around it
//			if (column.isNoAlter()) {
//				in_val = (String) val;
//			}
//			else {
//				in_val = "'" + SQLFormat.escape((String) val) + "'";
//			}
//		}
//		else if (val instanceof java.util.Date) {
//			in_val = "'" + sqldf.format(val) + "'";
//		}
//		else if (val instanceof Boolean) {
//			Boolean b = (Boolean) val;
//			if (b.booleanValue()) {
//				// true, so 1
//				in_val = "1";
//			}
//			else
//				in_val = "0";
//
//		}
//		else {
//			in_val = val.toString();
//		}
//		return in_val;
//	}

	public String toString() {
		String updateAsString = "UPDATE " + table + " SET ";
		for (Column column : columns){
			updateAsString += column.getName() + " = " + getFormatter().valueAsString(column) + ", ";
		}
		updateAsString = updateAsString.substring(0, updateAsString.length() - 2);
		if (wclause != null) {
			updateAsString += wclause.toString();
		}
		return updateAsString;
	}


}
