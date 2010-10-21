/**
 * 
 */
package example;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import com.nelsonx.jdbgm.core.GenericManager;
import com.nelsonx.jdbgm.core.PostgreSQLManager;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MakePostrgeDB {
	GenericManager manager;
	String user,location,password;
	Random ranGene;
	
	public MakePostrgeDB(String location, String user, String password) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.password = password;
		this.location = location;
		manager = new PostgreSQLManager(location, user, password);
		ranGene = new Random();
	}
	
	public void makeDB(){
		try {
			//Creo la estrucutura de la base de datos
			String sql = "CREATE TABLE alumnos (idAlumno serial PRIMARY KEY,"
						+ "dni int,fechaNacimiento date, email varchar(40), direccion varchar(40),"
						+ " nombre VARCHAR(40), apellido VARCHAR(40), telefono VARCHAR(20))";
			manager.update(sql);
			sql = "create table  materias (idMateria serial PRIMARY KEY,"
				+ "nombre varchar(20))";
			manager.update(sql);
			sql = "create table aniolectivo (idAA serial, PRIMARY KEY(idAA),"
				+ "idAlumno int, foreign key(idAlumno) references alumnos(idAlumno), idgrado int, anio int)";
			manager.update(sql);
			sql = "create table materiasxanio (idMP serial, PRIMARY KEY(idMP),"
				+ "idAA int, foreign key(idAA) references aniolectivo(idAA), idMateria int, "
				+ "foreign key(idMateria) references materias(idMateria))";
			manager.update(sql);
			sql = "create table asistencias (fecha date, asistencia int,"
				+ "idMP int, foreign key(idMP) references materiasxanio(idMP))";
			manager.update(sql);
			sql = "create table grados (idgrado serial, PRIMARY KEY(idgrado),"
				+ "nombre varchar(20))";
			manager.update(sql);
			
			manager.beginConnection();
			//Empiezo a leer los datos desde un archivo y prepararlos para las tablas de la base de datos
			//empezando por alumnos
			Vector<String> calles = Utils.openFile("DOC/CallesFixed.txt");
			Vector<String> nombres = Utils.openFile("DOC/NombreF.txt");
			Vector<String> apellidos = Utils.openFile("DOC/ApellidoF.txt");
			Vector<String> mails = Utils.openFile("DOC/mails.txt");
			int dni = 0;
			String date = "";
			String tel = "";

			for (int i = 0; i < nombres.size(); i++) {

				dni = 10000000 + ranGene.nextInt(10000000);
				tel = String.valueOf(4000000 + ranGene.nextInt(999999));
				String anio = String.valueOf(1960 + ranGene.nextInt(100));
				String mes = String.valueOf(ranGene.nextInt(12)+1);
				String dia = String.valueOf(ranGene.nextInt(28)+1);
				date = anio + "-" + mes + "-" + dia;
				sql = "INSERT INTO alumnos (dni,fechaNacimiento,email,direccion,nombre,apellido,telefono)"
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
						+ apellidos.elementAt(i) + "','" + tel + "' )";
				manager.update(sql);

			}
			
			//datos para materias
			Vector<String> materias = Utils.openFile("DOC/Materias.txt");

			for (int i = 0; i < materias.size(); i++) {
				sql = "insert into materias (nombre) values ('"
						+ materias.elementAt(i) + "')";
				manager.update(sql);
			}
			
			//datos para los grados
			String[] nom = { "4a", "4b", "5a", "5c" };
			for (int i = 0; i < 3; i++) {
				sql ="insert into grados (nombre) values('"
						+ nom[i] + "')";
				manager.update(sql);
			}
			
			//datos para aniolectivo
			ResultSet rse = manager.query("select idAlumno from alumnos");
			Vector<Integer> idsalumnos = new Vector<Integer>();

			while (rse.next()) {
				idsalumnos.add((Integer) rse.getObject("idAlumno"));

			}
			rse.close();
			rse = manager.query("select idgrado from grados");
			Vector<Integer> idsgrados = new Vector<Integer>();

			while (rse.next()) {
				idsgrados.add((Integer) rse.getObject("idgrado"));

			}
			rse.close();
			for (int i = 0; i < idsalumnos.size(); i++) {
				int[] anios = {2010,2009,2008};
				int a = ranGene.nextInt(anios.length);
				int b = ranGene.nextInt(idsgrados.size());
				sql ="insert into aniolectivo(idAlumno,idgrado,anio) values ("
						+ idsalumnos.elementAt(i)
						+ ","
						+ idsgrados.elementAt(b) + "," + anios[a] + ")";
				manager.update(sql);
			}
			
			//datos para materiasxanio
			rse = manager.query("select idAA from aniolectivo");
			Vector<Integer> idAA = new Vector<Integer>();
			while (rse.next()) {
				idAA.add((Integer) rse.getObject("idAA"));
			}
			rse.close();
			rse = manager.query("select idMateria from materias");
			Vector<Integer> idMAteria = new Vector<Integer>();

			while (rse.next()) {
				idMAteria.add((Integer) rse.getObject("idMateria"));
			}
			rse.close();
			for (int i = 0; i < idAA.size(); i++) {
				int materia = ranGene.nextInt(idMAteria.size());
				sql = "insert into materiasxanio (idAA,idMateria) values ("
						+ idAA.elementAt(i)
						+ ","
						+ idMAteria.elementAt(materia) + ")";
				manager.update(sql);
			}

			//datos para asistencias
			rse = manager.query("select idMP from materiasxanio");
			Vector<Integer> idMP = new Vector<Integer>();

			while (rse.next()) {
				idMP.add((Integer) rse.getObject("idMP"));
			}
			rse.close();
			for (int i = 0; i < 28; i++) {
				for (int j = 0; j < idAA.size(); j++) {
					String anio = "2010";// String.valueOf(2000 +
											// ranGene.nextInt(1000));
					String mes = "1";// String.valueOf(ranGene.nextInt(12));

					String dia = "" + (i + 1);// String.valueOf(ranGene.nextInt(28));
					date = anio + "/" + mes + "/" + dia;
					sql = "insert into asistencias (fecha,asistencia,idMP) values ('"
							+ date + "'," + 1 + "," + idMP.elementAt(j) + ")";
					manager.update(sql);
				}
			}
			manager.endConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		MakePostrgeDB mkdb = new MakePostrgeDB("localhost/AsistenciaAlumnos", "nelson", "gnusmas");
		mkdb.makeDB();
	}

}
