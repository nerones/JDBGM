package com.crossdb.sql;

import java.util.Date;

/**
 * El formateador define el comportamiento necesario para convertir (formatear) adecuadamente Objetos en cadenas
 * de texto que sean validas dentro de una sentencia SQL, como agregado también es capaz
 * de tomar como parámetro una {@link Column} y convertir adecuadamente el valor
 * de esta en una cadena de texto.
 * 
 * This will be on an implementation basis, the Formatter is retrieved/created
 * from the factory for the specific implementation.
 *
 * @author Nelson Efrain A. Cruz - neac03@gmail.com
 * @author Travis Reeder - travis@spaceprogram.com
 * 
 */
public interface Formatter {
	
	/**
	 * Los objetos del tipo Date deben ser adecuadamente convertidos
	 * @param d
	 * @return
	 */
	public String format(Date d);

     /**
     * Si se trata de una cadena de texto envuelve su contenido
     * con comillas simples.
     * @param s La cadena de texto a tratar.
     * @return La cadena de texto encerrada en comillas simples.
     */
    public String format(String s);
    
    /**
     * Si se trata de un objeto Booleano se lo convierte según corresponda a una
     * cadena de texto. usualmente se lo maneja haciendo la correspondencia 1=TRUE y
     * 0=FALSE.
     * @param b El objeto a ser tratado
     * @return el objeto booleano formateado en una cadena de texto.
     */
    public String format(Boolean b);
    
    /**
	 * Escapes a string for using with db,
	 *
	 * replaces single quotes with double singles.
	 */
	public String escape(String s);
	
	/**
	 * Evalúa el tipo de dato que almacena la columna y decide si es necesario
	 * formatear el tipo de datos después de convertirlo a {@link String} usando
	 * los métodos definidos en esta interfaz.
	 * @param column La columna de la que se quiere extraer el valor.
	 * @return El valor de la columna adecuadamente formatedo.
	 */
	public String valueAsString(Column column);
}
