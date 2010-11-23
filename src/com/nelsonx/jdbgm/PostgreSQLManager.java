/**
 * 
 */
package com.nelsonx.jdbgm;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class PostgreSQLManager extends JDBCManager {
	
	protected final String jdbc = "jdbc:postgresql://";
	
	public PostgreSQLManager(String location, String user, String password) throws JDException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.location = location;
		this.user = user;
		this.password = password;
		locationURL = jdbc+location;
		// TODO Auto-generated constructor stub
		beginConnection();
	}

}
