package example;

import java.sql.ResultSet;

import com.nelsonx.jdbgm.core.GenericManager;
import com.nelsonx.jdbgm.core.MySqlManager;

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
		
	}
	
	public ResultSet getStudentList() {
		String sql = "select * from alumno";
		return manager.query(sql);
	}
	

}
