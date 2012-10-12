package com.nelsonx.jdbgm;

import java.sql.SQLException;

public class ConnectionIssueException extends JDException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834605409666079331L;

	public ConnectionIssueException(String info, SQLException cause) {
		super(info, cause);
		// TODO Auto-generated constructor stub
	}
	
	public ConnectionIssueException(SQLException cause){
		super("No se pudo connectar a la base de datos",cause);
	}

}
