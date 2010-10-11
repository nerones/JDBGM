/**
 * 
 */
package com.nelsonx.jdbgm;

import com.nelsonx.jdbgm.core.MySqlManager;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public interface Command {
	public Object execute(MySqlManager Manager);

}
