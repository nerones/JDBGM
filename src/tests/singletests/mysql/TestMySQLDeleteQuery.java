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
package tests.singletests.mysql;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.DeleteQuery;
import com.crossdb.sql.SQLFactory;
import com.nelsonx.jdbgm.ManagerFactory;

/**
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 *
 */
public class TestMySQLDeleteQuery {
	
	public DeleteQuery delete;
	public String expected; 
	SQLFactory factory = SQLFactory.debugGetFactory(ManagerFactory.MYSQL_DB);
	
	@Before
	public void setUp(){
		delete = factory.getDeleteQuery();
	}
	
	@Test
	public void testBasic() {
		delete.setTable("table");
		delete.addWhere().andEquals("campo", "valor");
		expected = "DELETE FROM table WHERE (campo = 'valor')";
		assertEquals(expected, delete.toString());
	}

}
