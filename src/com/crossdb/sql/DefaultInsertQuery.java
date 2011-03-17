/**
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 8:55:57 PM
 * 
 */
package com.crossdb.sql;


public abstract class DefaultInsertQuery extends InsertQuery{

	protected String table;

	protected boolean return_id = false;

	public DefaultInsertQuery() {

		super();

	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addAutoIncrementColumn(String column) {
		ColumnValue c = new ColumnValue(column, null);
		c.setAutoIncrement(true);
		columns.add(c);
		// values.add(null);
	}

	public void addAutoIncrementColumn(String column, String sequence) {
		ColumnValue c = new ColumnValue(column, null);
		c.setAutoIncrement(true);
		c.setSequence(sequence);
		columns.add(c);
		// values.add(null);
	}

	public void returnID(boolean b) {
		return_id = b;
	}

	public abstract String toString();
	
	/*
	public abstract int execute(Statement stmt) throws SQLException;

	public int execute(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		int ret = execute(stmt);
		stmt.close();
		return ret;
	}

	public PreparedStatement getPreparedStatement(Connection conn)
			throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(toString());
		return pstmt;
	}
	*/
}
