/**
 * 
 */
package tests.singletests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Functions;
import com.crossdb.sql.Join;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.WhereClause;
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
		sq.addTable(Join.LEFT_OUTER_JOIN, "Registro2", 
				new WhereCondition("Animales", "idGen", WhereCondition.EQUAL_TO, "Registro", "idCien"));
		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		sq.addWhereCondition(wc);
		//System.out.println(sq.toString());
		assertEquals("SELECT raza1, nombre " +
				"FROM Animales LEFT OUTER JOIN Registro ON Animales.idGen = Registro.idCien " +
				"LEFT OUTER JOIN Registro2 ON Animales.idGen = Registro.idCien " +
				"WHERE Animales.raza = Registro.idRaza", 
				sq.toString());

	}
	
	@Test
	public void testToStringMedium3() {
		sq.setDistinct(true);
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
		assertEquals("SELECT DISTINCT raza, nombre FROM Animales, Registro WHERE Animales.raza = Registro.idRaza UNION " +
				"SELECT nombre FROM Mascotas", sq.toString());

	}
	
	@Test (expected=RuntimeException.class)
	/**
	 * Acá falla por que no se especifica ninguna tabla en select
	 */
	public void testSimple(){
		sq.addColumn("raza");
		sq.addColumn("Animales", "vida");
		sq.toString();
		//assertEquals("SELECT raza, Animales.vida FROM", sq.toString());
	}
	
	@Test //(expected=RuntimeException.class)
	public void testSimple2(){
		sq.addColumn("raza");
		sq.addColumn("Animales", "vida");
		sq.addTable("Animales");
		WhereClause wc = new WhereClause();
		wc.addCondition(new WhereCondition("edad", WhereCondition.LESS_THAN, 20));
		WhereClause wc2 = new WhereClause();
		//wc.addCondition("AND", new WhereCondition("edad", WhereCondition.LESS_THAN, 30));
		//wc.addCondition("AND", new WhereCondition("edad2", WhereCondition.LESS_THAN, 30));
		wc2.addCondition(new WhereCondition("edad", WhereCondition.LESS_THAN, 30));
		wc2.addCondition("AND", new WhereCondition("edad2", WhereCondition.LESS_THAN, 30));
		wc.addClause("AND", wc2);
		sq.addWhereClause(wc);
		//sq.toString();
		sq.addOrderBy("nombre");
		sq.addOrderBy("nombre2");
		sq.addOrderBy("nombre3");
		sq.addGroupBy("edad");
		sq.addGroupBy("edad1");
		sq.addGroupBy("edad2");
		sq.setLimit(20);
		assertEquals("SELECT raza, Animales.vida FROM Animales WHERE ( edad < 20 AND ( edad < 30 AND edad2 < 30 ) )" +
				" GROUP BY edad, edad1, edad2 ORDER BY nombre, nombre2, nombre3 LIMIT 20", sq.toString());
	}
	
	@Test
	public void testFunctions1(){
		sq.addColumn("column1");
		sq.maxColumn("column2");
		sq.addFunctionColumn("gastos", Functions.SUM, "Animales", "column");
		sq.addTable("Animales");
		
		//TODO falta agregar e implementar los alias AS
		assertEquals("SELECT column1, MAX( column2 ), SUM( Animales.column ) AS gastos FROM Animales", sq.toString());
	}
	
	// TODO falta probar las where is null o not null

}
