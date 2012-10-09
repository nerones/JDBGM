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
package tests.singletests.postgre;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SelectQuery;
import com.nelsonx.postgre.PostgreSQLFormatter;
import com.nelsonx.postgre.PostgreSQLInsertQuery;
import com.nelsonx.postgre.PostgreSQLSelectQuery;

/**
 * @author Nelson Efrain A. Cruz
 *Por ahora es exactamente igual a TestSQLiteInsert
 */
public class TestPostgreSQLInsert {
InsertQuery insert;
	
	@Before
	public void setup(){
		insert = new PostgreSQLInsertQuery();
	}
	
	@Test
	public void TestFromDefault(){
		insert.setTable("tabla");
		insert.addColumn("uno", "algo1");
		insert.addColumn("dos", "algo2");
		insert.setFromDefault(true);
		
		String expected = "INSERT INTO tabla DEFAULT VALUES";
		assertEquals(expected, insert.toString());
		
	}
	
	@Test
	public void TestFromSelect(){
		insert.setTable("tabla");
		insert.addColumn("uno", "algo1");
		insert.addColumn("dos", "algo2");
		SelectQuery select = new PostgreSQLSelectQuery(new PostgreSQLFormatter());
		select.addTable("Perro");
		insert.setSelectStmt(select);		
		String expected = "INSERT INTO tabla (uno, dos) SELECT * FROM Perro";
		assertEquals(expected, insert.toString());
		
	}
	
	@Test
	public void TestFromValues(){
		insert.setTable("tabla");
		insert.addColumn("uno", "algo1");
		insert.addColumn("dos", "algo2");		
		String expected = "INSERT INTO tabla (uno, dos) VALUES ('algo1', 'algo2')";
		assertEquals(expected, insert.toString());
		
	}
	
	@Test
	public void testTypes(){
		insert.setTable("Perro");
		Date date = new Date(100000000);
		insert.addColumn("nac", date);
		String expected = "INSERT INTO Perro (nac) VALUES ('1970-01-02 00:46:40')";
		assertEquals(expected, insert.toString());
	}
	
	@Test
	public void testPrecedence(){
		insert.setTable("Perro");
		insert.addColumn("Id", 522);
		insert.setFromDefault(true);
		String expected = "INSERT INTO Perro DEFAULT VALUES";
		assertEquals(expected, insert.toString());
		
		SelectQuery select = new PostgreSQLSelectQuery(new PostgreSQLFormatter());
		select.addTable("Dogs");
		insert.setSelectStmt(select);
		expected = "INSERT INTO Perro (Id) SELECT * FROM Dogs";
		assertEquals(expected, insert.toString());
		
		insert.setFromValues(true);
		expected = "INSERT INTO Perro (Id) VALUES (522)";
		assertEquals(expected, insert.toString());
	}

}
