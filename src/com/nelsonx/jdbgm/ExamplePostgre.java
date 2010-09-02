package com.nelsonx.jdbgm;

import java.sql.*;


public class ExamplePostgre {
	public static void main(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection conexion = DriverManager.getConnection(
				"jdbc:postgresql://localhost/world", "nelson", "test");
		Statement st = (Statement) conexion.createStatement();
		st.executeUpdate(
				 "CREATE TABLE contacto (id INT, PRIMARY KEY (id)," +
				 " nombre VARCHAR(20), apellidos VARCHAR(20), telefono VARCHAR(20))");
		 
		 String nombres[]={"Juan","Pedro","Antonio"};
		 String apellidos[]={"Gomez","Lopez","Alvarez"};
		 String telefonos[]={"123","456","789"};
		 for (int i=0;i<nombres.length;i++)
		    st.executeUpdate("INSERT INTO contacto (id,nombre, apellidos, telefono) " +
		    		"VALUES ("+i+",'"+nombres[i]+"','"+apellidos[i]+"','"+telefonos[i]+"' )");
		 
		
	}

}
