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

public interface Level1SelectQuery {

	public void setLimit(int count);

	public void setLimit(int offset, int count);




}
