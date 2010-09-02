package com.nelsonx.jdbgm;

import com.nelsonx.jdbgm.core.MySqlConnection;

public class ExampleUsage {
	public static void main(String[] args) throws ClassNotFoundException {
		MySqlConnection con = new MySqlConnection();
		try {
			con.getConection("localhost/world", "tester", "tester");
			con.makeUpdate("drop table contacto");
			String sqlins = "CREATE TABLE contacto (id INT AUTO_INCREMENT, PRIMARY KEY(id)," +
			 " nombre VARCHAR(20), apellidos VARCHAR(20), telefono VARCHAR(20))";
			con.makeUpdate(sqlins);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
