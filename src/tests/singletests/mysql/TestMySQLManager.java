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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.nelsonx.jdbgm.GenericManager;
import com.nelsonx.jdbgm.JDException;
import com.nelsonx.jdbgm.ManagerFactory;
/**
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class TestMySQLManager {

	GenericManager manager;
	SQLFactory factory;
	
	@Before
	public void prepare() throws JDException{
		manager = ManagerFactory.getManager(ManagerFactory.MYSQL_DB, "tester", "localhost/test", "tester");
		factory = ManagerFactory.getSQLFactory();
		
	}
	@Test
	public void testConnection() throws JDException{
		
		//CreateTableQuery create = factory.getCreateTableQuery();
		
//		create.setName("tabla1");
//		create.addColumn(new Column("campo1",Types.CHAR));
//		create.addColumn(new Column("campo2",Types.VARCHAR));
//		manager.update(create);
		for (int i = 0; i < 10; i++) {
			InsertQuery insert = factory.getInsertQuery();
			insert.setTable("tabla1");
			insert.addColumn("campo1", "valor1");
			insert.addColumn("campo2", "valor2");
			manager.update(insert);
		}
		manager.endConnection();
		
	}
	
	@Test
	public void testTransaction() throws JDException{
		manager.beginTransaction();
		for (int i = 0; i < 10; i++) {
			InsertQuery insert = factory.getInsertQuery();
			insert.setTable("tabla1");
			insert.addColumn("campo1", "trvalor1");
			insert.addColumn("campo2", "trvalor2");
			manager.update(insert);
		}
		manager.endTransaction();
		manager.endConnection();
	}
	
	@Test (expected=JDException.class)
	public void testFailTransaction() throws JDException{
		manager.beginTransaction();
		for (int i = 0; i < 10; i++) {
			InsertQuery insert = factory.getInsertQuery();
			insert.setTable("tabla1");
			insert.addColumn("campo1", "trvalor1");
			insert.addColumn("campo2", "trvalor2");
			manager.update(insert);
			manager.commit();
			manager.endConnection();
		}
		manager.endTransaction();
		manager.endConnection();
		
	}
	
	@Test (expected=JDException.class)
	public void testFailTransactionBad() throws JDException{
		manager.beginTransaction();
		InsertQuery insert;
		for (int i = 0; i < 10; i++) {
			insert = factory.getInsertQuery();
			insert.setTable("tabla1");
			insert.addColumn("campo1", "trbadvalor1");
			insert.addColumn("campo2", "trbadvalor2");
			manager.update(insert);
		}
		insert = factory.getInsertQuery();
		insert.setTable("tabla1");
		insert.addColumn("campo1", "trvalor1");
		insert.addColumn("campo2", "trvalor54");
		insert.addColumn("campo2", "trvalor54");
		manager.update(insert);
		
		manager.endTransaction();
		manager.endConnection();
		
	}
	
	@Test
	public void testQuery() throws JDException{
		SelectQuery select = factory.getSelectQuery();
		select.addTable("tabla1");
		@SuppressWarnings("unused")
		ResultSet rs = manager.query(select);
		
	}
	
	public void testConnection2() throws JDException, SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conexion = DriverManager.getConnection(
				"jdbc:mysql://localhost/test", "tester",
				"tester");
		PreparedStatement insert = conexion.prepareStatement("insert into tabla1 set campo1 = ?, campo2 = ?");
		for (int i = 0; i < 100; i++) {
			insert.setString(1, "valor1");
			insert.setString(2, "valor2");
			insert.execute();
		}
		
	}
}
