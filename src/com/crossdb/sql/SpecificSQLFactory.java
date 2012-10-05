package com.crossdb.sql;
/*
 * Esta clase se llamaba SQLFactory
 */
/**
 * Note: this class is now only used to implement specific factories, to obtain
 * the *Query you must use SQLFactory. 
 * This is the starting point to using the crossdb classes.
 * <p>
 * You create a factory by:
 * <p>
 * <code>
Class factory_class = Class.forName("com.spaceprogram.sql.mysql.DB2Factory"); // put the implementation string into forName()
com.crossdb.sql.SQLFactory factory = (com.crossdb.sql.SQLFactory)(factory_class.newInstance());
</code>
 * <p>
 * Then you use one of the get methods to get the type of query you want to use.
 * <p>
 * Title: crossdb
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */
@Deprecated
public interface SpecificSQLFactory {

	InsertQuery getInsertQuery();
	SelectQuery getSelectQuery();
	UpdateQuery getUpdateQuery();
	DeleteQuery getDeleteQuery();
	AlterTableQuery getAlterTableQuery();
	CreateTableQuery getCreateTableQuery();
	WhereClause getWhereClause();


}
