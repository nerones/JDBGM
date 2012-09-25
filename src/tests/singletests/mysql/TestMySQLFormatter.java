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

import com.crossdb.sql.Column;
import com.spaceprogram.sql.mysql.MySQLFormatter;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestMySQLFormatter {

	MySQLFormatter formatter;
	
	@Before
	public void setup(){
		formatter = new MySQLFormatter();
	}
	
	@Test
	public void testBasic(){
		Column column = new Column("uno", "algo'asi\"p'sdsd");
		
		String expected = "UPDATE tabla SET uno = 'algo1', dos = 'algo2' " +
				"WHERE (uno = 1 AND dos IS NOT NULL )";
		assertEquals(expected, formatter.valueAsString(column));
	}

}
