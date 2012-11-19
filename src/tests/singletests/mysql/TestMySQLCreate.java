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

import java.sql.Types;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Column;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.TableConstraint;
import com.nelsonx.jdbgm.ManagerFactory;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestMySQLCreate {
	CreateTableQuery ct;
	Column col;
	String expectedSQL;
	SQLFactory factory = SQLFactory.debugGetFactory(ManagerFactory.MYSQL_DB);
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = factory.getCreateTableQuery();
	}
	
	@Test
	public void testBasic(){
		ct.setName("Animales");
		Column test = new Column("Genero", Types.CHAR);
		test.setNullable(0);
		ct.addColumn(test);
		ct.addAutoincrementPrimaryKeyColumn(new Column("id", Types.INTEGER));
		ct.addColumn(new Column("carac", Types.INTEGER));
		expectedSQL = "CREATE TABLE Animales ( Genero CHAR(50) NOT NULL, id INT AUTO_INCREMENT PRIMARY KEY, carac INT )";
		assertEquals( expectedSQL, ct.toString());
		
		ct = factory.getCreateTableQuery();
		ct.setName("animales");
		ct.addPrimaryKeyColumn(new Column("id",Types.INTEGER));
		ct.addForeignKeyColumn(new Column("reino_id", Types.VARCHAR, "reinos", "id"));
		expectedSQL = "CREATE TABLE animales ( id INT, reino_id VARCHAR(50), " +
				"PRIMARY KEY (id), " +
				"FOREIGN KEY (reino_id) REFERENCES reinos(id) )";
		assertEquals( expectedSQL, ct.toString());
		
	}
	
	@Test
	public void testAsSelect(){
		ct.setName("animal");
		SelectQuery select = factory.getSelectQuery();
		select.addTable("animales");
		ct.setSelectStatementSource(select);
		assertEquals("CREATE TABLE animal AS SELECT * FROM animales", ct.toString());
	}
	
	@Test
	public void testCompositePK(){
		ct.setName("animales");
		ct.setTemporary(true);
		//Column id = new Column("id",Types.INTEGER);
		Column reino_id = new Column("reino_id", Types.VARCHAR, "reinos", "id");
		Column reinob_id = new Column("reinob_id", Types.VARCHAR, "reinosb", "idb");
		Vector<Column> columns = new Vector<Column>();
		columns.add(reino_id);
		columns.add(reinob_id);
		ct.addCompositePrimaryKeyColumns(columns);
		ct.addForeignKeyColumn(reino_id);
		ct.addForeignKeyColumn(reinob_id);
		expectedSQL = "CREATE TEMPORARY TABLE animales ( reino_id VARCHAR(50), reinob_id VARCHAR(50), " +
				"PRIMARY KEY (reino_id, reinob_id), " +
				"FOREIGN KEY (reino_id) REFERENCES reinos(id), " +
				"FOREIGN KEY (reinob_id) REFERENCES reinosb(idb) )";
		//System.out.println(ct.toString());
		assertEquals( expectedSQL, ct.toString());
		
	}
	
	@Test
	public void testMultipleCompositeFK(){
		ct.setName("Animales4");
		Column test = new Column("Genero", Types.CHAR);
		test.setForeignKey("Entes");
		test.setNullable(0);
		Column test2 = new Column("Genero2", Types.CHAR);
		test2.setForeignKey("Entes");
		Vector<Column> columns = new Vector<Column>();
		columns.add(test);
		columns.add(test2);
		ct.addForeignKeyColumn(columns);
		Column test3 = new Column("Cientifico", Types.CHAR);
		test3.setForeignKey("Estudio","Cientifico2");
		ct.addColumn(test3);
		ct.addForeignKeyColumn(test3);
		ct.addAutoincrementPrimaryKeyColumn(new Column("Id", Types.INTEGER));
		ct.addColumn(new Column("carac", Types.INTEGER));
		//System.out.println(ct.toString());
		expectedSQL = "CREATE TABLE Animales4 ( Genero CHAR(50) NOT NULL, Genero2 CHAR(50), Cientifico CHAR(50)," +
				" Id INT AUTO_INCREMENT PRIMARY KEY, carac INT," +
				" FOREIGN KEY (Genero, Genero2) REFERENCES Entes(Genero, Genero2)," +
				" FOREIGN KEY (Cientifico) REFERENCES Estudio(Cientifico2) )"; 
		assertEquals( expectedSQL, ct.toString());
		
	}
	
	@Test
	public void testUNIQUE(){
		ct.setName("Animales2");
		Column col = new Column("Genero", Types.CHAR);
		col.setDefaultColumnValue("'RATA'");
		ct.addUniqueColumn(col );
		ct.addColumn(new Column("Id", Types.INTEGER));
		Vector<Column> columns = new Vector<Column>();
		columns.add(new Column("uno", Types.INTEGER));
		columns.add(new Column("dos", Types.INTEGER));
		ct.addCompositeUniqueColumns(columns);
		expectedSQL = "CREATE TABLE Animales2 ( Genero CHAR(50) DEFAULT 'RATA', Id INT, " +
				"uno INT, dos INT, " +
				"UNIQUE (Genero), " +
				"UNIQUE (uno, dos) )";
		//System.out.println(ct.toString());
		assertEquals(expectedSQL, ct.toString());
	}
	
	@Test
	public void testAddConstraint(){
		ct.setName("animales");
		Vector<Column> columns = new Vector<Column>();
		columns.add(new Column("uno", Types.INTEGER, "tunos", "tuno_id"));
		columns.add(new Column("dos", Types.INTEGER, "tdos", "tdos_id"));
		TableConstraint foreign = new TableConstraint(TableConstraint.TYPE_FOREIGN_KEY, columns);
		foreign.setMatchTypeFK(TableConstraint.MATCH_FULL);
		ct.addTableConstraint(foreign);
		expectedSQL = "CREATE TABLE animales ( uno INT, dos INT, " +
				"FOREIGN KEY (uno, dos) REFERENCES tunos(tuno_id, tdos_id) MATCH FULL )";
		//System.out.println(ct.toString());
		assertEquals(expectedSQL, ct.toString());
	}
	
	@Test
	public void testTypes(){
		ct.setName("Animales5");
		Column col = new Column("col1", Types.BIGINT );
		ct.addColumn(col );
		ct.addColumn(new Column("col2", Types.BINARY ));
		ct.addColumn(new Column("col3", Types.BIT ));
		ct.addColumn(new Column("col4", Types.BLOB ));
		ct.addColumn(new Column("col5", Types.BOOLEAN ));
		ct.addColumn(new Column("col6", Types.CHAR ));
		ct.addColumn(new Column("col7", Types.CLOB ));
		ct.addColumn(new Column("col8", Types.DATALINK ));
		ct.addColumn(new Column("col9", Types.DATE ));
		ct.addColumn(new Column("col10", Types.DECIMAL ));
		ct.addColumn(new Column("col11", Types.DISTINCT ));
		ct.addColumn(new Column("col12", Types.DOUBLE ));
		ct.addColumn(new Column("col13", Types.FLOAT ));
		ct.addColumn(new Column("col14", Types.INTEGER ));
		ct.addColumn(new Column("col15", Types.JAVA_OBJECT ));
		ct.addColumn(new Column("col16", Types.LONGNVARCHAR ));
		ct.addColumn(new Column("col17", Types.LONGVARBINARY ));
		ct.addColumn(new Column("col18", Types.LONGVARCHAR ));
		ct.addColumn(new Column("col19", Types.NCHAR ));
		ct.addColumn(new Column("col20", Types.NCLOB ));
		ct.addColumn(new Column("col21", Types.NULL ));
		ct.addColumn(new Column("col22", Types.NUMERIC ));
		ct.addColumn(new Column("col23", Types.NVARCHAR ));
		ct.addColumn(new Column("col24", Types.OTHER ));
		ct.addColumn(new Column("col25", Types.REAL ));
		ct.addColumn(new Column("col26", Types.REF ));
		ct.addColumn(new Column("col27", Types.ROWID ));
		ct.addColumn(new Column("col28", Types.SMALLINT ));
		ct.addColumn(new Column("col29", Types.SQLXML ));
		ct.addColumn(new Column("col30", Types.STRUCT ));
		ct.addColumn(new Column("col31", Types.TIME ));
		ct.addColumn(new Column("col32", Types.TIMESTAMP ));
		ct.addColumn(new Column("col33", Types.TINYINT ));
		ct.addColumn(new Column("col34", Types.VARBINARY ));
		ct.addColumn(new Column("col35", Types.VARCHAR ));
		
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
