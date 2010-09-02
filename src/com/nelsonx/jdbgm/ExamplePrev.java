package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;


public class ExamplePrev {
	public static void main(String[] args) {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/world", "tester", "tester");
			 Statement st = (Statement) conexion.createStatement();
			 st.executeUpdate(
					 "CREATE TABLE contacto (id INT AUTO_INCREMENT, PRIMARY KEY(id)," +
					 " nombre VARCHAR(20), apellidos VARCHAR(20), telefono VARCHAR(20))");
			 
			 String nombres[]={"Juan","Pedro","Antonio"};
			 String apellidos[]={"Gomez","Lopez","Alvarez"};
			 String telefonos[]={"123","456","789"};
			 for (int i=0;i<nombres.length;i++)
			    st.executeUpdate("INSERT INTO contacto (nombre, apellidos, telefono) " +
			    		"VALUES ('"+nombres[i]+"','"+apellidos[i]+"','"+telefonos[i]+"' )");
			 
			 ResultSet rs = st.executeQuery("SELECT * FROM contacto"); 
			 while (rs.next())
			 {
			    System.out.println("nombre="+rs.getObject("nombre")+
			       ", apellidos="+rs.getObject("apellidos")+
			       ", telefono="+rs.getObject("telefono"));
			 }
			 rs.close();
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.print("error");
		}
		
		
	}
}
