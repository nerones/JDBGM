/**
 * 
 */
package com.nelsonx.jdbgm.core;



/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MySqlManager extends JDBCManager{
	
//	protected ExceptionHandler handler;
//	protected Connection connection;
//	protected Statement st;
//	protected String location, user, password, locationURL;
	protected final String jdbc = "jdbc:mysql://";
	
	public MySqlManager(String location, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.location = location;
		this.user = user;
		this.password = password;
		locationURL = jdbc+location;
		// TODO Auto-generated constructor stub
	}

}
