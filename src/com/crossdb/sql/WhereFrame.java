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

import java.util.Date;

/**
 * Es una plantilla para agregar todas las posibles condiciones where pues hay varias clases que lo usan.
 * @author Nelson Efrain A. Cruz
 *
 */
public interface WhereFrame {
	
	public abstract void addWhereCondition(String x, int comparison, String y);

	public abstract void addWhereCondition(String x, int comparison, int y);

	public abstract void addWhereCondition(String x, int comparison, Date y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, String y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, int y);

	public abstract void addWhereCondition(String and_or, String x, int comparison, Date y);

	public abstract void addWhereString(String x, int comparison, String y);

	public abstract void addWhereString(String and_or, String x, int comparison, String y);

	public abstract void addWhereCondition(WhereCondition cond);

	public abstract void addWhereClause(WhereClause wc);

	public abstract void addWhereNotNull(String col);

	public abstract void addWhereNotNull(String and_or, String col);

	public abstract void addWhereIsNull(String col);

	public abstract void addWhereIsNull(String and_or, String col);
}
