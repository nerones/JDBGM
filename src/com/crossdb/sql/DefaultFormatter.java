package com.crossdb.sql;

import java.util.Date;

/**
 * Implementación base de {@link Formatter} de la que deben heredar cualquier implementación
 * especifica para los motores de base de datos que se estén soportando. Los
 * métodos que se deben reescribir (si es que se debe hacer) son aquellos que
 * se llaman format(), el método que se debe llamar para convertir adecuadamente
 * los valores de las columnas a {@link String} es {@link #valueAsString(Column)} que usa
 * los métodos anteriores para realizar tal conversión.
 * 
 * @author Travis reeder
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @version 0.2
 */
public abstract class DefaultFormatter implements Formatter {
  
	SQLDateTimeFormat sqldf = new SQLDateTimeFormat();
	
	
    public String format(String s) {
        return "'" + escape(s) + "'";
    }

	@Override
	public String format(Boolean b) {
		if (b.booleanValue()) {
			// true, so 1
			return "1";
		}
		else
			return "0";
	}
	
	public String format(Date d){
		
			return "'" + sqldf.format(d) + "'";
		}

	public String escape(String s) {
		StringBuffer sb = new StringBuffer(s);

		char c;
		for (int i = 0; i < sb.length(); i++) {
			c = sb.charAt(i);
			if (c == '\'') {
				sb.insert(i, '\'');
				i += 1;
			} else if (c == '\\') {
				sb.insert(i, '\\');
				i += 1;
			}
		}
		return sb.toString();
	}
	
	public String valueAsString(Column column){
		Object val = column.getColumnValue();
		String in_val;
		if (val == null) {
			in_val = null;
		}
		else if (val instanceof String) {
			if (column.isNoAlter()) {
				//No se altera el contenido del valor de la columna.
				in_val = (String) val;
			}
			else {
				in_val = format( (String) val);
			}
		}
		else if (val instanceof java.util.Date) {
			in_val = format( (java.util.Date)val);
		}
		else if (val instanceof Boolean) {
			in_val = format((Boolean)val);
		}
		else {
			in_val = val.toString();
		}
		return in_val;
	}
	
}
