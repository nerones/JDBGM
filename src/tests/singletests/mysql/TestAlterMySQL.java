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

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.Column;
import com.spaceprogram.sql.mysql.MySQLAlterTableQuery;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestAlterMySQL {
	AlterTableQuery at;
	
	@Before
	public void setUp() throws Exception {
		at = new MySQLAlterTableQuery();
	}
	
	@Test
	public void testBasi(){
		at.setTable("tabla");
		at.addColumn(new Column("columna", java.sql.Types.DECIMAL, false));
		assertEquals("ALTER TABLE tabla ADD columna DECIMAL", at.toString());
	}
	
	@Test
	public void testBasic(){
		at.setTable("tabla");
		at.addColumn(new Column("columna", java.sql.Types.DECIMAL, false));
		assertEquals("ALTER TABLE tabla ADD columna DECIMAL", at.toString());
		//sdsdsd
	}

}
