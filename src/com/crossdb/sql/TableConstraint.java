/**
 * Copyright 2011 Nelson Efrain Abraham Cruz
 *
 * This file is part of JDBGM.
 * 
 * JDBGM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDBGM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JDBGM.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.crossdb.sql;

import java.util.Vector;

/**
 * Esta clase define una restricción de tabla para la definición de una tabla,
 * estas restricción pueden ser: clave primaria ({@link #TYPE_PRIMARY_KEY}), 
 * clave foranea ({@link #TYPE_FOREIGN_KEY}) o una restricción
 * UNIQUE ({@link #TYPE_UNIQUE} ). Una tabla puede tener a lo sumo una clave 
 * primaria, varias claves foraneas y varias unique. Para las claves foraneas, compuestas o no
 * se puede especificar cualquiera de las restricciones {@link #MATCH_FULL},
 * {@link #MATCH_PARTIAL} o {@link #MATCH_SIMPLE} y las acciones realizadas cuando
 * se borra {@link #TRIGGERED_ON_DELETE} o se actualiza {@link #TRIGGERED_ON_UPDATE}
 * con las constantes {@link #REFERENTIAL_ACTION_CASCADE}, {@link #REFERENTIAL_ACTION_NO_ACTION}
 * , {@link #REFERENTIAL_ACTION_RESTRICT} y {@link #REFERENTIAL_ACTION_SET_NULL}.
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 *
 */
public class TableConstraint {
	
	public static final int TYPE_PRIMARY_KEY = 1;
	public static final int TYPE_FOREIGN_KEY = 2;
	public static final int TYPE_UNIQUE = 3;
	
	public static final int MATCH_FULL = 4;
	public static final int MATCH_PARTIAL = 5;
	public static final int MATCH_SIMPLE = 6;
	
	public static final int TRIGGERED_ON_UPDATE = 11;
	public static final int TRIGGERED_ON_DELETE = 12;
	
	public static final int REFERENTIAL_ACTION_CASCADE = 7;
	public static final int REFERENTIAL_ACTION_SET_NULL = 8;
	public static final int REFERENTIAL_ACTION_RESTRICT = 9;
	public static final int REFERENTIAL_ACTION_NO_ACTION = 10;
	
	
	/**
	 * Las columnas que entran en la restricción de tabla.
	 */
	private Vector<Column> columns;
	
	/*
	 * Si se trata de una {@link #FOREIGN_KEY} entonces esta es la lista de
	 * columnas a las que se hace referencia
	 */
	//private Vector<Column> referencedColumns = new Vector<Column>();
	
	//private String referencedTable = null;
	
	/*
	 * El tipo de restricción de la que se trata pudiendo ser de cualquiera
	 * de las variables estáticas declaradas en esta clase.
	 */
	private int constraintType = 0;
	private String constraintName = null;
	private int matchType= 0;
	private int firstTriggeredAction = 0;
	private int firstReferentialAction = 0;
	private int secondTriggeredAction = 0;
	private int secondReferentialAction = 0;
	
	/**
	 * Crea una restricción de tabla de cualquiera de los tres tipos definidos por
	 * {@link #TYPE_FOREIGN_KEY}, {@link #TYPE_PRIMARY_KEY} o {@link #TYPE_UNIQUE}
	 * 
	 * @param constraintType el tipo de restricción de la que se trata
	 */
	public TableConstraint(int constraintType){
		this.constraintType = constraintType;
		this.columns = new Vector<Column>();
	}
	
	/**
	 * Crea una restricción de tabla de cualquiera de los tres tipos definidos por
	 * {@link #TYPE_FOREIGN_KEY}, {@link #TYPE_PRIMARY_KEY} o {@link #TYPE_UNIQUE}
	 * y una columna que formara parte de esa restricción.
	 * @param constraintType el tipo de restricción de la que se trata.
	 * @param column que formara la restricción
	 */
	public TableConstraint(int constraintType, Column column){
		this.constraintType = constraintType;
		columns = new Vector<Column>();
		columns.add(column);
	}

	/**
	 * Crea una restricción de tabla del tipo FOREIGN KEY para una sola columna
	 * y además se especifica la restricción MATCH con alguna de las opciones MATCH_FULL
	 * MATCH_PARTIAL o MATCH_SIMPLE.
	 * 
	 * @param fkColumn la columna que sera clave foranea
	 * @param matchType La opción para MATCH tomada de las constantes definidas en esta clase
	 */
	public TableConstraint( Column fkColumn, int matchType){
		this.constraintType = TYPE_FOREIGN_KEY;
		columns = new Vector<Column>();
		columns.add(fkColumn);
		this.matchType = matchType; 
	}
	
	/**
	 * Crea una restricción de tabla de cualquiera de los tres tipos definidos por
	 * {@link #TYPE_FOREIGN_KEY}, {@link #TYPE_PRIMARY_KEY} o {@link #TYPE_UNIQUE}
	 * y una lista de columnas que formara parte de esa restricción.
	 * 
	 * @param constraintType el tipo de restricción de la que se trata.
	 * @param column La lista de columnas que formara la restricción compuesta.
	 */
	public TableConstraint(int constraintType, Vector<Column> column){
		this.constraintType = constraintType;
		columns = column;
	}

	/**
	 * Crea una restricción de tabla del tipo FOREIGN KEY para una lista de columnas
	 * y además se especifica la restricción MATCH con alguna de las opciones {@link #MATCH_FULL},
	 * {@link #MATCH_PARTIAL} o {@link #MATCH_SIMPLE}.
	 * 
	 * @param fkColumn las columnas que serán clave foranea
	 * @param matchType La opción para MATCH tomada de las constantes definidas en esta clase
	 */
	public TableConstraint( Vector<Column> fkColumn, int matchType){
		this.constraintType = TYPE_FOREIGN_KEY;
		columns = fkColumn;
		this.matchType = matchType; 
	}
	
	/**
	 * La primer acción que se realizara al borrar o actualizar la tabla con
	 * respecto a su clave foranea
	 * @param triggeredAction
	 */
	public void setFirstReferentialTriggeredAction(int triggeredAction, int referentialAction){
		this.firstTriggeredAction = triggeredAction;
		this.firstReferentialAction = referentialAction;
	}
	
	public void setSecondReferentialTriggeredAction(int triggeredAction, int referentialAction){
		this.secondTriggeredAction = triggeredAction;
		this.secondReferentialAction = referentialAction;
	}
	
	public void setConstraintName(String constraintName){
		this.constraintName = constraintName;
	}
	
	private String matchTypeAsString(int matchType) {
		switch (matchType) {
		case MATCH_FULL:
			return " MATCH FULL";
			
		case MATCH_PARTIAL:
			return " MATCH PARTIAL";
		case MATCH_SIMPLE:
			return " MATCH SIMPLE";
		default:
			throw new RuntimeException("No se especifico un valor valido para" +
					"matchType, referirse a la documentación para mas detalle");
		}
	}
	
	private String referentialActionAsString(int referentialAction) {
		switch (referentialAction) {
		case REFERENTIAL_ACTION_CASCADE:
			return " CASCADE";
			
		case REFERENTIAL_ACTION_NO_ACTION:
			return " NO ACTION";
		case REFERENTIAL_ACTION_RESTRICT:
			return " RESTRICT";
		case REFERENTIAL_ACTION_SET_NULL:
			return " SET NULL";
		default:
			throw new RuntimeException("No se especifico un valor valido para" +
					"referentialAction, referirse a la documentación para mas detalle");
		}
	}
	
	private String triggeredActionAsString(int triggeredAction){
		switch (triggeredAction) {
		case TRIGGERED_ON_UPDATE:
			return " ON UPDATE";
		case TRIGGERED_ON_DELETE:
			return " ON DELETE";
		default:
			throw new RuntimeException("No se especifico un valor valido para" +
					"triggeredAction, referirse a la documentación para mas detalle");
		}
	}
	public String toString(){
		String query_string = "";
		if (constraintName != null) query_string += "CONSTRAINT "+ constraintName;
		if ( (columns == null) || (columns.size() == 0) ) 
			throw new RuntimeException("No se agregaron columnas a la restricción no se puede construir.");
		/*
		 * Extraigo la primer columna de la lista de columnas para poder armar 
		 * en un ciclo la lista de columnas del modo (element,element,element)
		 */
		Column firstColumn = columns.remove(0);
		switch (constraintType){
		case TYPE_UNIQUE:
			//Column firstColumn = columns.remove(0);
			query_string += " UNIQUE (" + firstColumn.getName();
			for (Column column: columns){
				query_string += " ," + column.getName();
			}
			return query_string + " )";
		case TYPE_PRIMARY_KEY:
			//Column firstColumn = columns.remove(0);
			query_string += " PRIMARY KEY (" + firstColumn.getName();
			for (Column column: columns){
				query_string += " " + column.getName();
			}
			return query_string + " )";
		case TYPE_FOREIGN_KEY:
			//Column firstColumn = columns.remove(0);
			query_string += " FOREIGN KEY (" + firstColumn.getName();
			String referencedColumnsAsString = "("+ firstColumn.getName();
			for (Column column: columns){
				query_string += " ," + column.getName();
				referencedColumnsAsString += " ," + column.getForeignPrimaryKey();
			}
			query_string += " ) REFERENCES " + firstColumn.getForeignTable() 
					+ referencedColumnsAsString + ")";
			if (matchType != 0) query_string += matchTypeAsString(matchType) +" ";
			if ( firstTriggeredAction != 0 ) query_string += triggeredActionAsString(firstTriggeredAction) + 
					referentialActionAsString(firstReferentialAction);
			if (secondTriggeredAction != 0 ) query_string += triggeredActionAsString(secondTriggeredAction) +
					referentialActionAsString(secondReferentialAction);
			return query_string;
		default:
			throw new RuntimeException("No se especifico un valor valido para" +
					"constraintType, referirse a la documentación para mas detalle");
		}
	}
	
}
