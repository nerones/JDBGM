package com.nelsonx.jdbgm;

public class ConnectionIssueException extends JDException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834605409666079331L;

	public ConnectionIssueException(String info, Exception cause) {
		super(info, cause);
		// TODO Auto-generated constructor stub
	}
	
	public ConnectionIssueException(Exception cause){
		super("No se pudo connectar a la base de datos",cause);
	}

}
