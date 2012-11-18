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

/**
 * Esta clase sirve para convertir los tipos de datos genéricos a aquellos específicos
 * para cada DBMS. <strong> Aclaración</strong>: como la declaración de tipos de datos
 * es usada casi exclusivamente por la sentencia {@code CREATE}, el soporte para
 * los tipos de datos en esta versión es básico puesto que escapa a el objetivo
 * de dicha versión. Es decir que solo son reconocidos los tipos de datos mas usados.
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 */
public abstract class DataTypes {
	
	/**
     * Dada una constante devuelve el tipo de dato asociado a la misma con la
     * opción de indicarle un tamaño para el tipo de dato. Para los tipos de datos
     * que no poseen un tamaño el tamaño declarado debe ser 0.
     * 
     * @param type Una constante que representa un tipo de datos
     * @param size El tamaño, si es que se requiere, para el tipo de dato.
     * @return El tipo de dato expresado como una cadena de texto.
     */
	abstract public String getAsString(int type, int size);
	
	/**
	 * Dado un objeto {@link Column} devuelve como cadena de texto el tipo de dato
	 * declarado para el mismo.
	 * 
	 * @param col La columna de la cual se quiere obtener el tipo de dato.
	 * @return El tipo de dato expresado como cadena de texto.
	 */
    public String getAsString(Column col) {
        return getAsString(col.getType(), col.getDefaultSize());
    }

    /**
     * Dada una constante devuelve el tipo de dato asociado a la misma.
     * 
     * @param type Una constante que representa un tipo de datos
     * @return El tipo de dato expresado como una cadena de texto.
     */
    public String getAsString(int type) {
        return getAsString(type, 0);
    }
}
