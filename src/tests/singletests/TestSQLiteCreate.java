/**
 * 
 */
package tests.singletests;


import java.sql.Types;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Column;
import com.crossdb.sql.CreateTableQuery;
import com.nelsonx.sqlite.SQLiteCreateTableQuery;

import static org.junit.Assert.assertEquals;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestSQLiteCreate {

	CreateTableQuery ct;
	Column col;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ct = new SQLiteCreateTableQuery();
	}
	
	@Test
	public void testBasic(){
		ct.setName("Animales");
		Column test = new Column("Genero", Types.CHAR, false, false);
		test.setNullable(0);
		ct.addColumn(test);
		ct.addColumn(new Column("Id", Types.INTEGER, true, true));
		ct.addColumn(new Column("carac", Types.INTEGER, false, true));
		assertEquals("CREATE TABLE Animales ( Genero CHAR(50) NOT NULL, Id INTEGER PRIMARY KEY AUTOINCREMENT, carac INTEGER )", ct.toString());
		
	}
	
	@Test
	public void testBasic2(){
		ct.setName("Animales2");
		ct.addColumn(new Column("Genero", Types.CHAR, true, false));
		ct.addColumn(new Column("Id", Types.INTEGER, true, false));
		assertEquals("CREATE TABLE Animales2 ( Genero CHAR(50), Id INTEGER, PRIMARY KEY (Genero, Id) )", ct.toString());
	}
	
	@Test
	public void testBasic3(){
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
				" Id INTEGER PRIMARY KEY AUTOINCREMENT, carac INTEGER," +
				" FOREIGN KEY (Genero, Genero2) REFERENCES Entes(Genero, Genero2)," +
				" FOREIGN KEY (Cientifico) REFERENCES Estudio(Cientifico2) )", ct.toString());
		
	}
	
	

}
