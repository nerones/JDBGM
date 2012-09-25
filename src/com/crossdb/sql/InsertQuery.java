package com.crossdb.sql;

/**
 * Esta clase representa una sentencia del tipo {@code INSERT} en ella se puede
 * elegir que los datos a insertar provengan de:
 * <ul>
 * 	<li>Valores por defecto para la columna.</li>
 * 	<li>Valores ingresados explícitamente.</li>
 * 	<li>Valores provenientes de una consulta {@link SelectQuery}.</li>
 * </ul>
 * 
 * Pero solo se puede elegir una de estas tres opciones a la vez, de llegarse a llamar mas
 * de una vez a cualquiera de los métodos {@link #setFromDefault(boolean)}, {@link #setFromValues(boolean)} y
 * {@link #setSelectStmt(SelectQuery)} solo tendrá efecto el ultimo de los que fuera
 * llamado.
 * 
 * <p>
 * Note: You should ALWAYS specify an auto_increment column using
 * addAutoIncrementColumn() if you are using one of course.
 * <p>
 * Rest is pretty self explanatory.
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.1
 */
public abstract class InsertQuery extends DefaultUpdateableQuery {

	//public abstract void setTable(String table);

	public InsertQuery(Formatter formatter) {
		super(formatter);
		// TODO Auto-generated constructor stub
	}

	// void addColumn(String column, String value, boolean auto_inc);
	public abstract void addAutoIncrementColumn(String column); // uses default
																// sequence

	//public abstract void addAutoIncrementColumn(String column, String sequence); 
	
	/**
	 * Este método pone como origen de los datos para la sentencia insert a una sentencia SELECT del tipo
	 * {@link SelectQuery}
	 * @param select Una sentencia SELECT valida
	 */
	public abstract void setSelectStmt(SelectQuery select);

	/**
	 * Este método hace que la fila a agregar tome los valores por defecto que se
	 * declararon al crearse la tabla, si no se declararon valores por defecto por lo general
	 * los motores ponen como default a {@code NULL}
	 * @param isFromDefault Indica si la fila insertada tomara los valores por defecto.
	 */
	public abstract void setFromDefault(boolean isFromDefault);
	
	/**
	 * Indica que a la sentencia se le pasaran los valores correspondientes a cada valor de columna que
	 * se este por insertar.
	 * @param isFromValues 
	 */
	public abstract void setFromValues(boolean isFromValues);

	
	
	/**
	 * Returns a java.sql.PreparedStatement object based on the query.
	 * 
	 * This should guarantee that the ordering of the "?" passed in is the same
	 * ordering as in the returned statement.
	 */
	// PreparedStatement getPreparedStatement(Connection conn) throws
	// SQLException;

}
