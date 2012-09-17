/**
 * Copyright 2011 Nelson Efrain Abraham Cruz
 *
 * This file is part of JDBGM.
 * 
 * JDBGM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDBGM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JDBGM.  If not, see <http://www.gnu.org/licenses/>.
 */
package tests.singletests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.WhereClause;

/**
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 */
public class TestWhereClause {
	
	WhereClause where;
	String expectedWhere = "";
	
	@Before
	public void setUp(){
		where = new WhereClause();
	}
	
	@Test
	public void testSimplewhere(){
		where.andEquals("perro_id", 1);
		expectedWhere = " WHERE perro = 1";
		Assert.assertEquals(expectedWhere, where.toString());
		
	}
	

}
