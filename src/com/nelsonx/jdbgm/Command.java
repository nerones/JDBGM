/**
 * 
 */
package com.nelsonx.jdbgm;

import com.nelsonx.jdbgm.core.GenericManager;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public interface Command {
	public Object execute(GenericManager Manager);

}
