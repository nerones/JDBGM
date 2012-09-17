package example;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.crossdb.sql.Column;
import com.crossdb.sql.Column;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.InsertQuery;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.SpecificSQLFactory;
import com.nelsonx.jdbgm.DefaultExceptionHandler;
import com.nelsonx.jdbgm.GenericManager;
import com.nelsonx.jdbgm.JDException;
import com.nelsonx.jdbgm.ManagerFactory;
import com.nelsonx.jdbgm.MySqlManager;

public class MakeMySqlDB {
	GenericManager manager;
	String user,location,password;
	Random ranGene;
	SpecificSQLFactory sqlFactory;
	
	public MakeMySqlDB(String location, String user, String password) {
		// TODO Auto-generated constructor stub
		this.user = user;
		this.password = password;
		this.location = location;
		//manager = new MySqlManager(location, user, password);
		try {
			manager = ManagerFactory.getManager(ManagerFactory.MYSQL_DB, user, location, password);
		} catch (JDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.setExceptionHandler(new DefaultExceptionHandler());
		ranGene = new Random();
		sqlFactory = ManagerFactory.getSQLFactory();
	}
	
	public void makeDB() throws JDException{
		try {
			//Creo la estrucutura de la base de datos
			String sql = "CREATE TABLE if not exists alumnos (idAlumno INT AUTO_INCREMENT, PRIMARY KEY(idAlumno),"
						+ "dni int,fechaNacimiento date, email varchar(40), direccion varchar(40),"
						+ " nombre VARCHAR(40), apellido VARCHAR(40), telefono VARCHAR(20))";
			manager.update(sql);
			sql = "create table if not exists materias (idMateria INT AUTO_INCREMENT, PRIMARY KEY(idMateria),"
				+ "nombre varchar(20))";
			manager.update(sql);
			sql = "create table if not exists aniolectivo (idAA INT AUTO_INCREMENT, PRIMARY KEY(idAA),"
				+ "idAlumno int, foreign key(idAlumno) references alumnos(idAlumno), idgrado int, anio int)";
			manager.update(sql);
			sql = "create table if not exists materiasxanio (idMP INT AUTO_INCREMENT, PRIMARY KEY(idMP),"
				+ "idAA int, foreign key(idAA) references aniolectivo(idAA), idMateria int, "
				+ "foreign key(idMateria) references materias(idMateria))";
			manager.update(sql);
			sql = "create table if not exists asistencias (fecha date, asistencia int,"
				+ "idMP int, foreign key(idMP) references materiasxanio(idMP))";
			manager.update(sql);
			sql = "create table if not exists grados (idgrado INT AUTO_INCREMENT, PRIMARY KEY(idgrado),"
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
				String anio = String.valueOf(2000 + ranGene.nextInt(1000));
				String mes = String.valueOf(ranGene.nextInt(12));
				String dia = String.valueOf(ranGene.nextInt(28));
				date = anio + "/" + mes + "/" + dia;
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
	
	public void makeDB2() throws JDException{
		CreateTableQuery create = sqlFactory.getCreateTableQuery();
		create.setName("alumnos");
		Column column = new Column("idAlumno", Types.INTEGER);
		create.addColumn(new Column("idAlumno", Types.INTEGER, true));
		create.addColumn(new Column("dni", Types.INTEGER));
		create.addColumn(new Column("fechaNacimiento", Types.DATE));
		create.addColumn(new Column("email", Types.VARCHAR));
		create.addColumn(new Column("direccion", Types.VARCHAR));
		create.addColumn(new Column("nombre", Types.VARCHAR));
		create.addColumn(new Column("apellido", Types.VARCHAR));
		create.addColumn(new Column("telefono", Types.VARCHAR));
		System.out.println(create);
		manager.update(create);
		
		create = sqlFactory.getCreateTableQuery();
		create.setName("materias");
		create.addColumn(new Column("idMateria", Types.INTEGER, true));
		create.addColumn(new Column("nombre", Types.VARCHAR));
		System.out.println(create);
		manager.update(create);
		
		create = sqlFactory.getCreateTableQuery();
		create.setName("aniolectivo");
		create.addColumn(new Column("IdAA", Types.INTEGER, true));
		column = new Column("idAlumno", Types.INTEGER);
		column.setForeignKey("alumnos");
		create.addColumn(column);
		create.addColumn(new Column("idgrado", Types.INTEGER));
		create.addColumn(new Column("anio", Types.INTEGER));
		System.out.println(create);
		manager.update(create);
		
		create = sqlFactory.getCreateTableQuery();
		create.setName("materiasxanio");
		create.addColumn(new Column("IdMP", Types.INTEGER, true));
		column = new Column("idAA", Types.INTEGER);
		column.setForeignKey("aniolectivo");
		create.addColumn(column);
		column = new Column("idMateria", Types.INTEGER);
		column.setForeignKey("materias");
		create.addColumn(column);
		System.out.println(create);
		manager.update(create);
		
		create = sqlFactory.getCreateTableQuery();
		create.setName("asistencias");
		create.addColumn(new Column("fecha", Types.DATE));
		create.addColumn(new Column("asistencia",Types.INTEGER));
		column = new Column("idMP", Types.INTEGER);
		column.setForeignKey("materiasxanio");
		create.addColumn(column);
		System.out.println(create);
		manager.update(create);
		
		create = sqlFactory.getCreateTableQuery();
		create.setName("grados");
		create.addColumn(new Column("idgrado", Types.INTEGER, true));
		create.addColumn(new Column("nombre", Types.VARCHAR));
		manager.update(create);
		System.out.println(create);
	}
	
	public void fillDB() throws IOException, SQLException, JDException{
		manager.beginTransaction();
		//Empiezo a leer los datos desde un archivo y prepararlos para las tablas de la base de datos
		//empezando por alumnos
		Vector<String> calles = Utils.openFile("DOC/CallesFixed.txt");
		Vector<String> nombres = Utils.openFile("DOC/NombreF.txt");
		Vector<String> apellidos = Utils.openFile("DOC/ApellidoF.txt");
		Vector<String> mails = Utils.openFile("DOC/mails.txt");
		int dni = 0;
		String date = "";
		String tel = "";
		
		InsertQuery insert = sqlFactory.getInsertQuery();
		insert.setTable("alumnos");
		ArrayList columnlist = new ArrayList();
		Column columndni = new Column("dni", null);
		columnlist.add(columndni);
		Column columnNacimiento = new Column("fechaNacimiento", null);
		columnlist.add(columnNacimiento);
		Column columnEmail = new Column("email", null);
		columnlist.add(columnEmail);
		Column columnDireccion = new Column("direccion", null);
		columnlist.add(columnDireccion);
		Column columnNombre = new Column("nombre", null);
		columnlist.add(columnNombre);
		Column columnApellido = new Column("apellido", null);
		columnlist.add(columnApellido);
		Column columnTelefono = new Column("telefono", null);
		columnlist.add(columnTelefono);
		insert.appendColumns(columnlist);
		
		
		
//		insert.Columns();

		for (int i = 0; i < nombres.size(); i++) {
			dni = 10000000 + ranGene.nextInt(10000000);
			tel = String.valueOf(4000000 + ranGene.nextInt(999999));
			String anio = String.valueOf(2000 + ranGene.nextInt(1000));
			String mes = String.valueOf(ranGene.nextInt(12));
			String dia = String.valueOf(ranGene.nextInt(28));
			date = anio + "/" + mes + "/" + dia;
			columndni.setColumnValue(dni);
			columnNacimiento.setColumnValue(date);
			columnEmail.setColumnValue(mails.elementAt(i));
			columnDireccion.setColumnValue(calles.elementAt(i));
			columnNombre.setColumnValue(nombres.elementAt(i));
			columnApellido.setColumnValue(apellidos.elementAt(i));
			columnTelefono.setColumnValue(tel);
			//System.out.println(insert.toString());
			manager.update(insert);
		}
//		
		//datos para materias
		Vector<String> materias = Utils.openFile("DOC/Materias.txt");
		insert = sqlFactory.getInsertQuery();
		insert.setTable("materias");
		Column col = new Column("nombre", null);
		ArrayList list = new ArrayList();
		list.add(col);
		insert.appendColumns(list);
		
		for (int i = 0; i < materias.size(); i++) {
			col.setColumnValue(materias.elementAt(i));
			manager.update(insert);
		}
		
		insert.setTable("grados");
		
		//datos para los grados
		String[] nom = { "4a", "4b", "5a", "5c" };
		for (int i = 0; i < 3; i++) {
			col.setColumnValue(nom[i]);
			manager.update(insert);
		}
		
//		//datos para aniolectivo
		SelectQuery select = sqlFactory.getSelectQuery();
		select.addColumn("idAlumno");
		select.addTable("alumnos");
		
		ResultSet rse = manager.query(select);
		Vector<Integer> idsalumnos = new Vector<Integer>();

		while (rse.next()) {
			idsalumnos.add((Integer) rse.getObject("idAlumno"));

		}
		rse.close();
		select = sqlFactory.getSelectQuery();
		select.addColumn("idgrado");
		select.addTable("grados");
		rse = manager.query(select);
		Vector<Integer> idsgrados = new Vector<Integer>();

		while (rse.next()) {
			idsgrados.add((Integer) rse.getObject("idgrado"));

		}
		rse.close();
		
		insert = sqlFactory.getInsertQuery();
		insert.setTable("aniolectivo");
		Column idalumn = new Column("idAlumno", null);
		insert.addColumn(idalumn);
		Column idgrado = new Column("idgrado", null);
		insert.addColumn(idgrado);
		Column anio = new Column("anio", null);
		insert.addColumn(anio);
		for (int i = 0; i < idsalumnos.size(); i++) {
			int[] anios = {2010,2009,2008};
			int a = ranGene.nextInt(anios.length);
			int b = ranGene.nextInt(idsgrados.size());
			idalumn.setColumnValue(idsalumnos.elementAt(i));
			idgrado.setColumnValue(idsgrados.elementAt(b));
			anio.setColumnValue(anios[a]);
			manager.update(insert);
		}
		
		//datos para materiasxanio
		select = sqlFactory.getSelectQuery();
		select.addColumn("idAA");
		select.addTable("aniolectivo");
		rse = manager.query(select);
		Vector<Integer> idAA = new Vector<Integer>();
		while (rse.next()) {
			idAA.add((Integer) rse.getObject("idAA"));
		}
		rse.close();
		select = sqlFactory.getSelectQuery();
		select.addColumn("idMateria");
		select.addTable("materias");
		rse = manager.query(select);
		Vector<Integer> idMAteria = new Vector<Integer>();

		while (rse.next()) {
			idMAteria.add((Integer) rse.getObject("idMateria"));
		}
		rse.close();
		
		insert = sqlFactory.getInsertQuery();
		insert.setTable("materiasxanio");
		Column cidAA = new Column("idAA", null);
		insert.addColumn(cidAA);
		Column idMateria = new Column("idMateria", null);
		insert.addColumn(idMateria);
		for (int i = 0; i < idAA.size(); i++) {
			int materia = ranGene.nextInt(idMAteria.size());
			cidAA.setColumnValue(idAA.elementAt(i));
			idMateria.setColumnValue(idMAteria.elementAt(materia));
			manager.update(insert);
		}

		//datos para asistencias
		select = sqlFactory.getSelectQuery();
		select.addColumn("idMP");
		select.addTable("materiasxanio");
		rse = manager.query(select);
		Vector<Integer> idMP = new Vector<Integer>();

		while (rse.next()) {
			idMP.add((Integer) rse.getObject("idMP"));
		}
		rse.close();
		
		insert = sqlFactory.getInsertQuery();
		insert.setTable("asistencias");
		Column fecha = new Column("fecha", null);
		insert.addColumn(fecha);
		Column asistencia = new Column("asistencia", null);
		insert.addColumn(asistencia);
		Column cidMP = new Column("idMP", null);
		insert.addColumn(cidMP);
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < idAA.size(); j++) {
				String anio1 = "2010";// String.valueOf(2000 +
										// ranGene.nextInt(1000));
				String mes = "1";// String.valueOf(ranGene.nextInt(12));

				String dia = "" + (i + 1);// String.valueOf(ranGene.nextInt(28));
				date = anio1 + "/" + mes + "/" + dia;
				fecha.setColumnValue(date);
				asistencia.setColumnValue(1);
				cidMP.setColumnValue(idMP.elementAt(j));
				manager.update(insert);
			}
		}
		manager.endConnection();
	}
	
	public static void main(String[] args) throws IOException, SQLException, JDException {
		MakeMySqlDB mkdb = new MakeMySqlDB("localhost/AsistenciaAlumnos", "tester", "tester");
		//mkdb.makeDB2();
		mkdb.fillDB();
	}

}
