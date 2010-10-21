package example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.nelsonx.jdbgm.core.GenericManager;
import com.nelsonx.jdbgm.core.MySqlManager;
import com.nelsonx.jdbgm.core.PostgreSQLManager;
import com.nelsonx.jdbgm.core.SQLiteManager;

/**
 * @author Nelson Efrain A. Cruz
 * 
 */
public class DataObtainer {
	String location, user, password;
	GenericManager manager;

	public DataObtainer(String user, String location, String password) {
		this.location = location;
		this.user = user;
		this.password = password;
		manager = new MySqlManager(location, user, password);
		//manager = new PostgreSQLManager(location, user, password);
		//manager = new SQLiteManager(location, user, password);

	}

	public ResultSet getAllStudentList() {
		String sql = "select * from alumnos";
		return manager.query(sql);
	}

	public ResultSet getListByYear(String date) {
		/*
		 * Usar Date?
		 */
		String sql = "SELECT a.idAlumno, a.nombre,a.apellido, al.anio " +
				"FROM alumnos a inner join aniolectivo al on a.idAlumno = al.idAlumno " +
				"where al.anio='" + date + "'";
		return manager.query(sql);
	}
	
	 public ResultSet getListByGrade(int idGrado){
		String sql = "SELECT a.idAlumno, a.nombre,a.apellido, al.anio " +
				"FROM alumno a inner join aniolectivo al on a.idAlumno = al.idAlumno " +
				"where al.idgrado="+idGrado;
		return manager.query(sql);
		 
	 }
	 
	 public ResultSet getStudent(int idAlumno){
		 String sql = "select * from alumnos " +
		 		"where alumno.idAlumno ="+idAlumno;
		 return manager.query(sql);
	 }
	 
	 public String[] listYears(){
		String sql = "select DISTINCT anio from aniolectivo";
		ResultSet rs = manager.query(sql);
		Vector<String> data = new Vector<String>(1);
		try {
			while (rs.next()) {
				data.add((rs.getObject("anio")).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] dat = new String[data.size()];
		data.toArray(dat);
		return dat;
	 }
	 
	 public Grade[] listGradesByYear(String year){
		String sql = "select DISTINCT g.nombre, g.idgrado " +
		 		"from aniolectivo a inner join grados g on a.idgrado = g.idgrado " +
		 		"where a.anio='"+year+"'";
		ResultSet rs = manager.query(sql);
		Vector<Grade> data = new Vector<Grade>(1);
		try {
			while (rs.next()) {
				String year1 = (String) rs.getObject("nombre");
				int idGrade = (Integer) rs.getObject("idgrado");
				data.add(new Grade(year1, idGrade) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Grade[] dat = new Grade[data.size()];
		data.toArray(dat);
		return dat;
	 }
	 
	 public ResultSet listStudenbyYearGrade(String year, int idgrade){
		String sql = "select a.nombre, a.apellido, a.dni, a.email " +
				"from aniolectivo al inner join alumnos a on a.idAlumno = al.idAlumno " +
		 		"where al.anio='"+year+"' and al.idgrado="+idgrade;
		return manager.query(sql); 
		
	 }

}
