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
import com.nelsonx.sqlite.SQLiteFormatter;

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
		where = new WhereClause(new SQLiteFormatter());
	}
	
	@Test
	public void testSimplewhere(){
		where.andEquals("perro_id", 1);
		where.andEquals("oveja", "blanca");
		where.orEquals("gato", "negro");
		expectedWhere = " WHERE (perro_id = 1 AND oveja = 'blanca' OR gato = 'negro')";
		System.out.println(where.toString());
		Assert.assertEquals(expectedWhere, where.toString());
		where = new WhereClause(new SQLiteFormatter());
		where.andBetween("perro_id", 1, 3);
		expectedWhere = " WHERE (perro_id BETWEEN 1 AND 3)";
		System.out.println(where.toString());
		
	}
	
	@Test
	public void testComplexWhere() {
		where.andLike("nombre", "%ardo%");
		WhereClause where2 = new WhereClause(new SQLiteFormatter());
		where2.andEquals("oveja", "negra");
		String[] array = {"edu","jorge","luis"};
		where2.orIn("nombre", array);
		where.andNestClause(where2);
		
		System.out.println(where.toString());
		expectedWhere = " WHERE (nombre LIKE '%ardo%' AND (oveja = 'negra' OR nombre IN ('edu', 'jorge', 'luis')))";
		Assert.assertEquals(expectedWhere, where.toString());
	}
	
	@Test
	public void testComplexWhere2() {
		where.andLike("nombre", "%ardo%");
		WhereClause where2 = new WhereClause(new SQLiteFormatter());
		where2.andEquals("oveja", "negra");
		Integer[] array = {1,2,3};
		where2.orIn("nombre", array);
		where.andNestClause(where2);
		
		System.out.println(where.toString());
		expectedWhere = " WHERE (nombre LIKE '%ardo%' AND (oveja = 'negra' OR nombre IN (1, 2, 3)))";
		Assert.assertEquals(expectedWhere, where.toString());
	}
	
	@Test
	public void testFullWhere(){
		where.andComparison("perro_id", "<", 3);
		where.orComparison("perro_id", "<>", 3);
		String[] array = {"edu","jorge","luis"};
		where.andNotIn("nombre", array);
		where.andNotLike("nombre", "%ardo%");
		expectedWhere = " WHERE (perro_id < 3 OR perro_id <> 3 AND nombre NOT IN ('edu', 'jorge', 'luis') AND nombre NOT LIKE '%ardo%')";
		Assert.assertEquals(expectedWhere, where.toString());
		
	}
	
	//TODO test more strange objects, to see how the to string works.
	
	

}
