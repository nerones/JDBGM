package com.crossdb.sql;

import java.util.List;

/**
 * Representa una cláusula WHERE como por ejemplo WHERE x = y, la cláusula where
 * esta formada por diferentes condiciones del tipo {@link WhereCondition} e
 * incluir cláusulas {@link WhereClause} anidadas como por ejemplo WHERE (x = y
 * AND (x=z OR x=b))
 * <p>
 * Entonces cada conjunto de paréntesis puede ser visto como una cláusula where,
 * y básicamente podemos ver a WhereClause como una lista de condiciones y/o
 * cláusulas where.
 * <p>
 * Copyright: Copyright (c) 2002 - Company: Space Program Inc.
 * </p>
 * 
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 */
public class WhereClause implements IWhereClause {

	java.util.List conditions = new java.util.ArrayList();
	java.util.List separators = new java.util.ArrayList();

	/**
	 * For formatting dates in toString
	 * 
	 * @see #toString()
	 */
	SQLDateTimeFormat sqldf;

	public WhereClause() {

	}

	public void addCondition(WhereCondition cond) {
		separators.add("AND");
		conditions.add(cond);
	}

	public void addCondition(String and_or, WhereCondition cond) {
		separators.add(and_or);
		conditions.add(cond);
	}

	public void addClause(IWhereClause clause) {
		separators.add("AND");
		conditions.add(clause);
	}

	public void addClause(String and_or, IWhereClause clause) {
		separators.add(and_or);
		conditions.add(clause);
	}

	public String toString() {
		// rifle through conditions and separators
		// if another clause found, call getWhereClause on that one

		String ret = "";
		for (int i = 0; i < conditions.size(); i++) {
			Object ob = conditions.get(i);
			if (i != 0) {
				ret += separators.get(i) + " ";
			}
			if (ob instanceof WhereCondition) {
				WhereCondition cond = (WhereCondition) ob;
				if (cond.getPreTable() != null) {
					ret += cond.getPreTable() + ".";
				}
				Object pre = cond.getPre();
				// check to see if object is a certain type for formatting
				if (pre instanceof java.util.Date) {
					if (sqldf == null)
						sqldf = new SQLDateTimeFormat();

					ret += "'" + sqldf.format((java.util.Date) pre) + "' ";
				} else
					// TODO pre se convierte a string pero puede ser cualquier objeto.
					ret += pre + " ";

				ret += "" + getOperatorString(cond.getComparison()) + " ";
				if (cond.getPostTable() != null) {
					ret += cond.getPostTable() + ".";
				}
				pre = cond.getPost();
				// check to see if object is a certain type for formatting
				if (pre instanceof java.util.Date) {
					if (sqldf == null)
						sqldf = new SQLDateTimeFormat();

					ret += "'" + sqldf.format((java.util.Date) pre) + "' ";
				} else
					// TODO pre se convierte a string pero puede ser cualquier objeto.
					ret += pre + "";
					// TODO " " -> "" rompio algo?

			} else { // clause cause that's all it can be
				WhereClause clause = (WhereClause) ob;
				ret += " (" + clause.toString() + ") ";
			}
		}
		return ret;
	}

	public boolean hasConditions() {
		if (conditions.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * gets operator string
	 */
	public static String getOperatorString(int operator) {
		String ret = null;
		switch (operator) {
		case WhereCondition.EQUAL_TO:
			ret = "=";
			break;
		case WhereCondition.NOT_EQUAL_TO:
			ret = "<>";
			break;
		case WhereCondition.LESS_THAN:
			ret = "<";
			break;
		case WhereCondition.LESS_THAN_OR_EQUAL_TO:
			ret = "<=";
			break;
		case WhereCondition.GREATER_THAN:
			ret = ">";
			break;
		case WhereCondition.GREATER_THAN_OR_EQUAL_TO:
			ret = ">=";
			break;
		case WhereCondition.LIKE:
			ret = "LIKE";
			break;
		case WhereCondition.BETWEEN:
			ret = "BETWEEN";
			break;

		case WhereCondition.IN:
			ret = "IN";
			break;
		case WhereCondition.NOT_NULL:
			ret = "IS NOT NULL";
			break;
		case WhereCondition.IS_NULL:
			ret = "IS NULL";
			break;
		}
		return ret;
	}

	public List getConditions() {
		return conditions;
	}

	public List getSeparators() {
		return separators;
	}

	public void addWhereIn(String col, String commaSeparatedList) {
		// todo: not really a todo, but should the commalist be escaped?
		addWhereIn("AND", col, commaSeparatedList);
	}

	public void addWhereIn(String and_or, String col, String commaSeparatedList) {
		addCondition(new WhereCondition(col, WhereCondition.IN, "("
				+ commaSeparatedList + ")"));
	}

	public void addWhereLikeLeft(String col, String toCompare) {
		addWhereLikeLeft("AND", col, toCompare);
	}

	public void addWhereLikeLeft(String and_or, String col, String toCompare) {
		addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'"
				+ SQLFormat.escape(toCompare) + "%'"));
	}

	public void addWhereLikeRight(String col, String toCompare) {
		addWhereLikeRight("AND", col, toCompare);
	}

	public void addWhereLikeRight(String and_or, String col, String toCompare) {
		addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'%"
				+ SQLFormat.escape(toCompare) + "'"));
	}

	public void addWhereLikeAny(String col, String toCompare) {
		addWhereLikeAny("AND", col, toCompare);
	}

	public void addWhereLikeAny(String and_or, String col, String toCompare) {
		addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'%"
				+ SQLFormat.escape(toCompare) + "%'"));
	}

}
