package com.crossdb.sql;

/**


 
/**
 * <p>Title: crossdb - </p>
 * <p>Description: There are some oddities with this class that are here to support all databases.  The
main one is that you can specify a sequence table for dbs like Oracle or SAPDB that
need a sequence table to insert into identity columns.
<p>
If none are specified, the implementation of this class should create the sequence
table called <table_name>_seq.
<p>
Now if the user specifies a sequence column, he should also use that same sequence name in any insert query
no matter what db he is using.  dbs like mysql or sql server ignore the sequence names.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

//import java.util.List;
//import java.sql.*;

public interface CreateTableQuery {
	
	
	void setName(String name);
	void addColumn(Column c);
	
	/**
	This is used for dbs like Oracle and SAP db, but needs to be implemented for all dbs for cross db
	compatability.
	
	Not necessary to use it though, because implementation should have default sequence names.
	 */
	//void setSequence(String sequence_table_name);
	
	/**
	Uses stmt to execute the query.  This is so you can keep reusing the same
	statement.  Be sure to use new statements if you want more than one resultset
	open at the same time.
	 */
	void execute(java.sql.Statement stmt) throws java.sql.SQLException ;
	
	void execute(java.sql.Connection conn) throws java.sql.SQLException;
	
}
