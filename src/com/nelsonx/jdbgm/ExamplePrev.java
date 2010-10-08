package com.nelsonx.jdbgm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.mysql.jdbc.Statement;

import java.io.*;

public class ExamplePrev {
	static Random ranGene = new Random();;

	static Vector<String> openFile(String vr) throws IOException {
		FileReader fr = new FileReader(vr);
		BufferedReader entrada = new BufferedReader(fr);
		String s;
		Vector<String> lista = new Vector<String>();
		while ((s = entrada.readLine()) != null) {
			lista.add(s);
		}
		entrada.close();
		return lista;
	}

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(
					"jdbc:mysql://localhost/AsistenciaAlumnos", "tester",
					"tester");
			Statement st = (Statement) conexion.createStatement();
			st.executeUpdate("CREATE TABLE if not exists alumno (idAl INT AUTO_INCREMENT, PRIMARY KEY(idAl),"
					+ "dni int,fechaNacimiento date, correoe varchar(40), direccion varchar(40),"
					+ " nombre VARCHAR(40), apellido VARCHAR(40), telefono VARCHAR(20))");
			
			st.executeUpdate("create table if not exists materia (idMateria INT AUTO_INCREMENT, PRIMARY KEY(idMateria),"
					+ "nombre varchar(20))");
			
			st.executeUpdate("create table if not exists aniolectivo (idAA INT AUTO_INCREMENT, PRIMARY KEY(idAA),"
					+ "idAlumno int, foreign key(idAlumno) references alumno(idAlumno), idgrado int, anio date)");
			
			st.executeUpdate("create table if not exists materiasxanio (idMP INT AUTO_INCREMENT, PRIMARY KEY(idMP),"
					+ "idAA int, foreign key(idAA) references aniolectivo(idAA), idMateria int, "
					+ "foreign key(idMateria) references materia(idMateria))");
			
			st.executeUpdate("create table if not exists asistencia (fecha date, asistencia int,"
					+ "idMP int, foreign key(idMP) references materiasxanio(idMP))");
			
			st.executeUpdate("create table if not exists grados (idgrado INT AUTO_INCREMENT, PRIMARY KEY(idgrado),"
					+ "nombre varchar(20))");
			
			Vector<String> calles = openFile("DOC/CallesFixed.txt");
			Vector<String> nombres = openFile("DOC/NombreF.txt");
			Vector<String> apellidos = openFile("DOC/ApellidoF.txt");
			Vector<String> mails = openFile("DOC/mails.txt");
			int dni = 0;
			String date = "";
			String tel = "";

			for (int i = 0; i < nombres.size(); i++) {
				dni = 10000000 + ranGene.nextInt(10000000);
				tel = String.valueOf(4000000 + ranGene.nextInt(999999));
				String anio = String.valueOf(2000 + ranGene.nextInt(1000));
				String mes = String.valueOf(ranGene.nextInt(12));
				String dia = String.valueOf(ranGene.nextInt(28));
				date = anio + "/" + mes + "/" + dia;
				st.executeUpdate("INSERT INTO alumno (dni,fechaNacimiento,correoe,direccion,nombre,apellido,telefono)"
						+ "VALUES ("
						+ dni
						+ ",'"
						+ date
						+ "','"
						+ mails.elementAt(i)
						+ "','"
						+ calles.elementAt(i)
						+ "','"
						+ nombres.elementAt(i)
						+ "','"
						+ apellidos.elementAt(i) + "','" + tel + "' )");

			}
			Vector<String> materias = openFile("DOC/Materias.txt");

			for (int i = 0; i < materias.size(); i++) {
				st.executeUpdate("insert into materia (nombre) values ('"
						+ materias.elementAt(i) + "')");
			}
			String[] nom = {"4a","4b","5a"};
			for (int i = 0; i < 3; i++) {
				st.executeUpdate("insert into grados (nombre) values('"+nom[i]+"')");
			}

			ResultSet rse = st.executeQuery("select idAl from alumno");
			Vector<Integer> idsalumnos = new Vector<Integer>();
			
			while (rse.next()) {
				idsalumnos.add((Integer)rse.getObject("idAl"));
				
			}
			rse.close();
			rse = st.executeQuery("select idgrado from grados");
			Vector<Integer> idsgrados = new Vector<Integer>();

			while (rse.next()) {
				idsgrados.add((Integer)rse.getObject("idgrado"));
				
			}
			rse.close();
			for (int i = 0; i < idsalumnos.size(); i++) {
				String anio = "2010";//String.valueOf(2000 + ranGene.nextInt(1000));
				String mes = "1";//String.valueOf(ranGene.nextInt(12));
				String dia = "1";//String.valueOf(ranGene.nextInt(28));
				date = anio + "/" + mes + "/" + dia;
				int a = ranGene.nextInt(idsalumnos.size());
				int b = ranGene.nextInt(idsgrados.size());
				st.executeUpdate("insert into aniolectivo(idAlumno,idgrado,anio) values ("+idsalumnos.elementAt(i)+","+idsgrados.elementAt(b)+",'"+date+"')");
			}
			rse = st.executeQuery("select idAA from aniolectivo");
			Vector<Integer> idAA = new Vector<Integer>();
			
			while (rse.next()) {
				idAA.add((Integer)rse.getObject("idAA"));
				
			}
			rse.close();
			rse = st.executeQuery("select idMateria from materia");
			Vector<Integer> idMAteria = new Vector<Integer>();
			
			while (rse.next()) {
				idMAteria.add((Integer)rse.getObject("idMateria"));
				
			}
			rse.close();
			for (int i = 0; i < idAA.size(); i++) {
				int materia = ranGene.nextInt(idMAteria.size());
				st.executeUpdate("insert into materiasxanio (idAA,idMateria) values ("+idAA.elementAt(i)+","+idMAteria.elementAt(materia)+")");
			}
			
			rse = st.executeQuery("select idMP from materiasxanio");
			Vector<Integer> idMP = new Vector<Integer>();
			
			while (rse.next()) {
				idMP.add((Integer)rse.getObject("idMP"));
			}
			rse.close();
			for (int i = 0; i < 28; i++) {
				for (int j = 0; j < idAA.size(); j++) {
					String anio = "2010";//String.valueOf(2000 + ranGene.nextInt(1000));
					String mes = "1";//String.valueOf(ranGene.nextInt(12));
					
					String dia = ""+(i+1);//String.valueOf(ranGene.nextInt(28));
					date = anio + "/" + mes + "/" + dia;
					st.executeUpdate("insert into asistencia (fecha,asistencia,idMP) values ('"+date+"',"+1+","+idMP.elementAt(j)+")");
				}
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.print("error");
		}

	}
}
