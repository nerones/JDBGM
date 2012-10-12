package com.nelsonx.jdbgm;

import java.sql.SQLException;

/**
 * Envuelve las posibles excepciones que puedan ocurrir al manejar los métodos
 * de JDBC
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 * @since 0.5
 */
public class JDException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8845283186962440284L;
	private String extraInfo;
	private SQLException cause;
	private String context;
	
	public JDException(String info, SQLException cause) {
		extraInfo = info;
		this.cause = cause;
		System.err.println(extraInfo);
	}
	
	public JDException(String context, String info, SQLException cause) {
		extraInfo = info;
		this.cause = cause;
		this.context = context;
		System.err.println(context + extraInfo);
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public Exception getCause() {
		return cause;
	}

	public void setCause(SQLException cause) {
		this.cause = cause;
	}
	
	
	

}
