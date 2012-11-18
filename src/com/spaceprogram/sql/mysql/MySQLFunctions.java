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
package com.spaceprogram.sql.mysql;

import com.crossdb.sql.Functions;

/**
 * Implementación especifica de {@link Functions} para el DBMS MySQL.Esta clase
 * redefine algunas constantes que representan los nombres de la funciones equivalentes
 * para acomodarlas al dialecto usado por este motor.
 * 
 * @author Nelson Efrain A. Cruz -neac03@gmail.com
 *
 */
public class MySQLFunctions extends Functions {

	public static final String DATETIME = "TIMESTAMP";
}