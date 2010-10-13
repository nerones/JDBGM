/**
 * 
 */
package example;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class EasyList {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		//MainWindow main = new MainWindow("Easy List");
		DataObtainer data = new DataObtainer("tester", "localhost/AsistenciaAlumnos", "tester");
		ResultSet rs = data.getStudentList();
		while (rs.next()){
			String nombre = (String) rs.getObject("nombre");
			String email = (String) rs.getObject("correoe");
			System.out.println(nombre +" | "+email);
		}

	}

}
