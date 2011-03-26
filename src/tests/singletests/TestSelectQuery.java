/**
 * 
 */
package tests.singletests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Join;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.WhereCondition;
import com.spaceprogram.sql.mysql.MySQLSelectQuery;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class TestSelectQuery {
	
	SelectQuery sq, sq2;
	
	@Before
	/**
	 * 
	 */
	public void setUp() {
		sq = new MySQLSelectQuery();
		sq2 = new MySQLSelectQuery();
	}
	@Test
	/**
	 * 
	 */
	public void testToStringSimple() {
		sq.addTable("Animales");
		sq.addTable(Join.INNER_JOIN,"Registro", new WhereCondition("raza", WhereCondition.EQUAL_TO, "idRaza"));
		//System.out.println(sq.toString());
		assertEquals("SELECT * FROM Animales, Registro WHERE raza = idRaza", sq.toString());

	}
	@Test
	public void testToStringMedium() {
		sq.addColumn("raza");
		sq.addColumn("nombre");
		sq.addTable("Animales");
		sq.addTable("Registro");
		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		sq.addWhereCondition(wc);
		//System.out.println(sq.toString());
		assertEquals("SELECT raza, nombre FROM Animales, Registro WHERE Animales.raza = Registro.idRaza", sq.toString());

	}
	
	@Test
	public void testToStringMedium2() {
		sq.addColumn("raza1");
		sq.addColumn("nombre");
		sq.addTable("Animales");
		sq.addTable(Join.LEFT_OUTER_JOIN, "Registro", 
				new WhereCondition("Animales", "idGen", WhereCondition.EQUAL_TO, "Registro", "idCien"));
		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		sq.addWhereCondition(wc);
		//System.out.println(sq.toString());
		assertEquals("SELECT raza1, nombre " +
				"FROM Animales LEFT OUTER JOIN Registro ON Animales.idGen = Registro.idCien " +
				"WHERE Animales.raza = Registro.idRaza", 
				sq.toString());

	}
	
	@Test
	public void testToStringMedium3() {
		
		sq.addColumn("raza");
		sq.addColumn("nombre");
		sq.addTable("Animales");
		sq.addTable("Registro");
		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		sq.addWhereCondition(wc);
		sq2.addColumn("nombre");
		sq2.addTable("Mascotas");
		sq.union(sq2);
		//System.out.println(sq.toString());
		assertEquals("SELECT raza, nombre FROM Animales, Registro WHERE Animales.raza = Registro.idRaza UNION " +
				"SELECT nombre FROM Mascotas", sq.toString());

	}

}
