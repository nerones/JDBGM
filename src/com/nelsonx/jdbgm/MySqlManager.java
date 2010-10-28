/**
 * 
 */
package com.nelsonx.jdbgm;



/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MySqlManager extends JDBCManager{
	
	protected final String jdbc = "jdbc:mysql://";
	
	public MySqlManager(String location, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.location = location;
		this.user = user;
		this.password = password;
		locationURL = jdbc+location;
	}

}
