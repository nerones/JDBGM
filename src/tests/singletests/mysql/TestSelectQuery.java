/**
 * 
 */
package tests.singletests.mysql;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Functions;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.WhereClause;
import com.spaceprogram.sql.mysql.MySQLSelectQuery;

/**
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 *
 */
public class TestSelectQuery {
	
	SelectQuery sq, sq2;
	String expected;
	
	@Before
	public void setUp() {
		sq = new MySQLSelectQuery();
		sq2 = new MySQLSelectQuery();
	}
	
	@Test
	public void testToStringSimple() {
		sq.addTable("animales");
		sq.addJoin().innerJoin("registro", "raza = idRaza");
		expected = "SELECT * FROM animales INNER JOIN registro ON raza = idRaza";
		assertEquals(expected, sq.toString());

	}
	@Test
	public void testToStringMedium() {
		sq.addColumn("raza");
		sq.addColumn("nombre");
		sq.addTable("animales");
		sq.addJoin().innerJoin("registro", "animales.raza = registro.idRaza");
		//WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		//sq.addWhereCondition(wc);
		expected = "SELECT raza, nombre FROM animales INNER JOIN registro ON animales.raza = registro.idRaza";
		//System.out.println(sq.toString());
		assertEquals(expected, sq.toString());

	}
	
	@Test
	public void testToStringMedium2() {
		sq.addColumn("raza1");
		sq.addColumn("nombre");
		sq.addTable("animales");
		sq.addJoin().leftOuterJoin("registro", "animales.idGen = registro.idCien");
//		sq.addTable(Join.LEFT_OUTER_JOIN, "Registro", 
//				new WhereCondition("Animales", "idGen", WhereCondition.EQUAL_TO, "Registro", "idCien"));
		sq.addJoin().leftJoin("registro2", "animales.idGen = registro2.idCien");
//		sq.addTable(Join.LEFT_OUTER_JOIN, "Registro2", 
//				new WhereCondition("Animales", "idGen", WhereCondition.EQUAL_TO, "Registro", "idCien"));
//		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
		sq.addWhere().andEquals("animales.raza", "perro");
		//System.out.println(sq.toString());
		expected = "SELECT raza1, nombre " +
				"FROM animales LEFT OUTER JOIN registro ON animales.idGen = registro.idCien " +
				"LEFT JOIN registro2 ON animales.idGen = registro2.idCien " +
				"WHERE (animales.raza = 'perro')";
		assertEquals(expected, sq.toString());

	}
	
	@Test
	public void testToStringMedium3() {
		sq.setDistinct(true);
		sq.addColumn("raza");
		sq.addColumn("nombre");
		sq.addTable("animales");
		sq.addJoin().innerJoin("registro", "animales.raza = registro.idRaza");
//		sq.addTable("Registro");
//		WhereCondition wc = new WhereCondition("Animales", "raza", WhereCondition.EQUAL_TO, "Registro", "idRaza");
//		sq.addWhereCondition(wc);
		sq2.addColumn("nombre");
		sq2.addTable("Mascotas");
		sq.union(sq2);
		expected = "SELECT DISTINCT raza, nombre FROM animales INNER JOIN registro ON animales.raza = registro.idRaza" +
				" UNION " +
				"SELECT nombre FROM Mascotas";
		//System.out.println(sq.toString());
		assertEquals(expected , sq.toString());

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
		wc.andComparison("edad", "<", 30);
		wc.andComparison("edad2", "<", 30);
		//WhereClause wc2 = new WhereClause();
		//wc.addCondition("AND", new WhereCondition("edad", WhereCondition.LESS_THAN, 30));
		//wc.addCondition("AND", new WhereCondition("edad2", WhereCondition.LESS_THAN, 30));
		//wc2.addCondition(new WhereCondition("edad", WhereCondition.LESS_THAN, 30));
		//wc2.addCondition("AND", new WhereCondition("edad2", WhereCondition.LESS_THAN, 30));
		//wc.addClause("AND", wc2);
		sq.addWhere().andComparison("edad", "<", 20);
		sq.addWhere().andNestClause(wc);
		//sq.toString();
		sq.addOrderBy("nombre, nombre2, nombre3");
		//sq.addOrderBy("nombre2");
		//sq.addOrderBy("nombre3");
		sq.addGroupBy("edad, edad1, edad2");
		//sq.addGroupBy("edad1");
		//sq.addGroupBy("edad2");
		sq.setLimit(20);
		expected = "SELECT raza, Animales.vida " +
				"FROM Animales " +
				"WHERE (edad < 20 AND (edad < 30 AND edad2 < 30)) " +
				"GROUP BY edad, edad1, edad2 " +
				"ORDER BY nombre, nombre2, nombre3 " +
				"LIMIT 20";
		assertEquals(expected, sq.toString());
	}
	
	@Test
	public void testFunctions1(){
		sq.addColumn("column1");
		sq.maxColumn("column2");
		sq.addFunctionColumn("gastos", Functions.SUM, "Animales", "column");
		sq.addTable("Animales");
		expected = "SELECT column1, MAX( column2 ), SUM( Animales.column ) AS gastos FROM Animales";
		//TODO falta agregar e implementar los alias AS
		assertEquals(expected, sq.toString());
	}
	
	// TODO falta probar las where is null o not null

}
