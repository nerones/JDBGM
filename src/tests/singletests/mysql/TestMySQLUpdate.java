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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Column;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.UpdateQuery;
import com.nelsonx.jdbgm.ManagerFactory;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestMySQLUpdate {

	UpdateQuery update;
	SQLFactory factory = SQLFactory.debugGetFactory(ManagerFactory.MYSQL_DB);
	@Before
	public void setup(){
		update = factory.getUpdateQuery();
	}
	
	@Test
	public void testBasic(){
		update.setTable("tabla");
		update.addColumn("uno", "algo1");
		update.addColumn("dos", "algo2");
		update.addWhere().andEquals("uno", 1);
		update.addWhere().andNotNull("dos");
		
		
		String expected = "UPDATE tabla SET uno = 'algo1', dos = 'algo2' " +
				"WHERE (uno = 1 AND dos IS NOT NULL )";
		assertEquals(expected, update.toString());
	}
	
	@Test
	public void testEscapedTypes(){
		update.setTable("tabla");
		update.addColumn(new Column("uno", true));
		Date date = new Date(100000000);
		update.addColumn("dos", date);
		update.addColumnNoAlter("tres", "value");
		//TODO test more data types
		String expected = "UPDATE tabla SET uno = 1, dos = '1970-01-02 00:46:40', tres = value";
				//+"WHERE (uno = 1 AND dos IS NOT NULL )";
		assertEquals(expected, update.toString());
	}
	
}
