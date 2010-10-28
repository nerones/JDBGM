package com.nelsonx.jdbgm;

import java.sql.ResultSet;


public class ExampleUsage {
	public static void main(String[] args) throws ClassNotFoundException {
		MySqlManager con = new MySqlManager("localhost/world", "tester", "tester");
		try {
			con.beginConnection();
			String sqlins = "CREATE TABLE if not exists contacto  (id INT AUTO_INCREMENT, PRIMARY KEY(id)," +
			 " nombre VARCHAR(20), apellidos VARCHAR(20), telefono VARCHAR(20))";
			con.update(sqlins);
			for (int i = 0; i < 100; i++) {
				String sql1 = "insert into contacto (nombre,apellidos,telefono) values ('Rodolfo', 'Baez', '155045343')";
				con.update(sql1);
			}
			ResultSet rs = con.query("select * from contacto");
			System.out.println();
			while (rs.next()) {
				String nombre = (String)(rs.getObject("nombre"));
				String apellido = (String)(rs.getObject("apellidos"));
				System.out.println(nombre + apellido + rs.getRow());
			}
			//con.update("drop table contacto");
			con.endConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
