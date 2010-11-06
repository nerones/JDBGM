/**
 * For Level1 features:
 * <p>
 * Includes:
 * <p>
 * <ol>
 * 	<li>Unions
 * 	<li>Limits
 * </ol>
 * @author Travis Reeder - travis@spaceprogram.com
 * Date: Jun 27, 2002
 * Time: 6:55:16 PM
 *
 */
package com.crossdb.sql.ext;

import com.crossdb.sql.SelectQuery;

public interface Level2SelectQuery extends Level1SelectQuery {


    public void union(SelectQuery sq);

}
