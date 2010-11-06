/**
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 6:57:02 PM
 * 
 */
package com.crossdb.sql.ext;

import com.crossdb.sql.DefaultSelectQuery;
import com.crossdb.sql.SelectQuery;

public abstract class DefaultLevel2SelectQuery extends DefaultLevel1SelectQuery implements Level2SelectQuery {
	SelectQuery forUnion;

	public void union(SelectQuery sq){
		forUnion = sq;
	}
}
