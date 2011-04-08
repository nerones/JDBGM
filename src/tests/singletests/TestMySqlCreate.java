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

import static org.junit.Assert.assertEquals;

import java.sql.Types;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Column;
import com.crossdb.sql.CreateTableQuery;
import com.spaceprogram.sql.mysql.MySQLCreateTableQuery;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestMySqlCreate {
	CreateTableQuery ct;
	Column col;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = new MySQLCreateTableQuery();
	}
	
	@Test
	public void testBasic(){
		ct.setName("Animales");
		Column test = new Column("Genero", Types.CHAR, false, false);
		test.setNullable(0);
		ct.addColumn(test);
		ct.addColumn(new Column("Id", Types.INTEGER, true, true));
		ct.addColumn(new Column("carac", Types.INTEGER, false, true));
		assertEquals("CREATE TABLE Animales ( Genero CHAR(50) NOT NULL, Id INTEGER PRIMARY KEY AUTO_INCREMENT, carac INTEGER )", ct.toString());
		
	}
	
	@Test
	public void testCompositePK(){
		ct.setName("Animales2");
		ct.addColumn(new Column("Genero", Types.CHAR, true, false));
		ct.addColumn(new Column("Id", Types.INTEGER, true, false));
		assertEquals("CREATE TABLE Animales2 ( Genero CHAR(50), Id INTEGER, PRIMARY KEY (Genero, Id) )", ct.toString());
	}
	
	@Test
	public void testMultipleCompositeFK(){
		ct.setName("Animales4");
		Column test = new Column("Genero", Types.CHAR, false, false);
		test.setForeignKey("Entes");
		test.setNullable(0);
		ct.addColumn(test);
		Column test2 = new Column("Genero2", Types.CHAR, false, false);
		test2.setForeignKey("Entes");
		ct.addColumn(test2);
		Column test3 = new Column("Cientifico", Types.CHAR, false, false);
		test3.setForeignKey("Estudio","Cientifico2");
		ct.addColumn(test3);
		ct.addColumn(new Column("Id", Types.INTEGER, true, true));
		ct.addColumn(new Column("carac", Types.INTEGER, false, true));
		System.out.println(ct.toString());
		assertEquals("CREATE TABLE Animales4 ( Genero CHAR(50) NOT NULL, Genero2 CHAR(50), Cientifico CHAR(50)," +
				" Id INTEGER PRIMARY KEY AUTO_INCREMENT, carac INTEGER," +
				" FOREIGN KEY (Genero, Genero2) REFERENCES Entes(Genero, Genero2)," +
				" FOREIGN KEY (Cientifico) REFERENCES Estudio(Cientifico2) )", ct.toString());
		
	}
	
	@Test
	public void testBasic4(){
		ct.setName("Animales2");
		Column col = new Column("Genero", Types.CHAR, false, false);
		col.setUnique(true);
		col.setDefaultValue("'RATA'");
		ct.addColumn(col );
		ct.addColumn(new Column("Id", Types.INTEGER, true, false));
		assertEquals("CREATE TABLE Animales2 ( Genero CHAR(50) UNIQUE DEFAULT 'RATA', Id INTEGER PRIMARY KEY )", ct.toString());
	}
	
	@Test
	public void testTypes(){
		ct.setName("Animales5");
		Column col = new Column("col1", Types.BIGINT, false, false);
		ct.addColumn(col );
		ct.addColumn(new Column("col2", Types.BINARY, false, false));
		ct.addColumn(new Column("col3", Types.BIT, false, false));
		ct.addColumn(new Column("col4", Types.BLOB, false, false));
		ct.addColumn(new Column("col5", Types.BOOLEAN, false, false));
		ct.addColumn(new Column("col6", Types.CHAR, false, false));
		ct.addColumn(new Column("col7", Types.CLOB, false, false));
		ct.addColumn(new Column("col8", Types.DATALINK, false, false));
		ct.addColumn(new Column("col9", Types.DATE, false, false));
		ct.addColumn(new Column("col10", Types.DECIMAL, false, false));
		ct.addColumn(new Column("col11", Types.DISTINCT, false, false));
		ct.addColumn(new Column("col12", Types.DOUBLE, false, false));
		ct.addColumn(new Column("col13", Types.FLOAT, false, false));
		ct.addColumn(new Column("col14", Types.INTEGER, false, false));
		ct.addColumn(new Column("col15", Types.JAVA_OBJECT, false, false));
		ct.addColumn(new Column("col16", Types.LONGNVARCHAR, false, false));
		ct.addColumn(new Column("col17", Types.LONGVARBINARY, false, false));
		ct.addColumn(new Column("col18", Types.LONGVARCHAR, false, false));
		ct.addColumn(new Column("col19", Types.NCHAR, false, false));
		ct.addColumn(new Column("col20", Types.NCLOB, false, false));
		ct.addColumn(new Column("col21", Types.NULL, false, false));
		ct.addColumn(new Column("col22", Types.NUMERIC, false, false));
		ct.addColumn(new Column("col23", Types.NVARCHAR, false, false));
		ct.addColumn(new Column("col24", Types.OTHER, false, false));
		ct.addColumn(new Column("col25", Types.REAL, false, false));
		ct.addColumn(new Column("col26", Types.REF, false, false));
		ct.addColumn(new Column("col27", Types.ROWID, false, false));
		ct.addColumn(new Column("col28", Types.SMALLINT, false, false));
		ct.addColumn(new Column("col29", Types.SQLXML, false, false));
		ct.addColumn(new Column("col30", Types.STRUCT, false, false));
		ct.addColumn(new Column("col31", Types.TIME, false, false));
		ct.addColumn(new Column("col32", Types.TIMESTAMP, false, false));
		ct.addColumn(new Column("col33", Types.TINYINT, false, false));
		ct.addColumn(new Column("col34", Types.VARBINARY, false, false));
		ct.addColumn(new Column("col35", Types.VARCHAR, false, false));
		
		assertEquals("CREATE TABLE Animales5 ( " +
				"col1 BIGINT, " +
				"col2 BLOB, " +
				"col3 TINYINT(1), " +
				"col4 BLOB, " +
				"col5 TINYINT(1), " +
				"col6 CHAR(50), " +
				"col7 CLOB, " +
				"col8 DATALINK, " +
				"col9 DATE," +
				"col10 DECIMAL, " +
				"col11 DISTINCT, " +
				"col12 DOUBLE, " +
				"col13 FLOAT, " +
				"col14 INTEGER, " +
				"col15 JAVA_OBJECT, " +
				"col16 TEXT, " +
				"col17 MEDIUMBLOB, " +
				"col18 TEXT, " +
				"col19 TEXT, " +
				"col20 NCLOB, " +
				"col21 NULL, " +
				"col22 NUMERIC, " +
				"col23 TEXT, " +
				"col24 OTHER, " +
				"col25 REAL, " +
				"col26 REF, " +
				"col27 ROWID, " +
				"col28 SMALLINT, " +
				"col29 SQLXML, " +
				"col30 STRUCT, " +
				"col31 TIME, " +
				"col32 TIMESTAMP, " +
				"col33 TINYINT, " +
				"col34 BLOB, " +
				"col35 VARCHAR(50) )", ct.toString());
	}

}
