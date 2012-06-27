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
 * Esta clase sirve para mapear los tipos de datos genéricos a aquellos específicos
 * de cada base de datos.
 * @author Nelson Efrain A. Cruz
 *
 */
public abstract class DataTypes {
	
	abstract public String getAsString(int type, int size);
	
	
    public String getAsString(Column col) {
        return getAsString(col.getType(), col.getDefaultSize());
    }

    /**
     For columns that don't have a size
     */
    public String getAsString(int type) {
        return getAsString(type, 0);
    }
}
