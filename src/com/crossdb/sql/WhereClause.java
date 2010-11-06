package com.crossdb.sql;

import java.util.List;

/**

 Represents a where clause
 <p>
 The WhereClauses can be set up to form any type of where clause
 and includes sub where clauses that will create for example:
 WHERE (x = y AND (x=z OR x=b))
 <p>
 So each set of brackets is a WhereClause.
 <p>
 Basically a list of conditions and/or sub wheres

/**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */
public class WhereClause implements IWhereClause{
	
	java.util.List conditions = new java.util.ArrayList();
	java.util.List separators = new java.util.ArrayList();

	/**
	 * for formatting dates in toString
	 * @see #toString()
	 */
	SQLDateTimeFormat sqldf;

	public WhereClause(){
		
	}
	/**
	 defaults to AND
	 */
	public void addCondition(WhereCondition cond){
		separators.add("AND");
		conditions.add(cond);
	}
	public void addCondition(String and_or, WhereCondition cond){
		separators.add(and_or);
		conditions.add(cond);
	}
	/**
	 defaults to AND
	 */
	public void addClause(IWhereClause clause){
		separators.add("AND");
		conditions.add(clause);
	}
	
	public void addClause(String and_or, IWhereClause clause){
		separators.add(and_or);
		conditions.add(clause);
	}
    public void addClause(WhereClause clause){
		separators.add("AND");
		conditions.add(clause);
	}

	public void addClause(String and_or, WhereClause clause){
		separators.add(and_or);
		conditions.add(clause);
	}
	/**
	 Not sure if this will work in all dbs, but for now, it should be fine.
	 <p>
	 This function could turn into debugging or sample if not.
	 */
	
	public String toString(){
		// rifle through conditions and separators
		// if another clause found, call getWhereClause on that one

		String ret = "";
		for(int i =0; i < conditions.size(); i++){
			Object ob = conditions.get(i);
			if(i != 0){
				ret += separators.get(i) + " ";
			}
			if(ob instanceof WhereCondition){
				WhereCondition cond = (WhereCondition)ob;
				if(cond.x_table != null){
					ret += cond.x_table + ".";
				}
				Object pre = cond.getPre();
				// check to see if object is a certain type for formatting
                if(pre instanceof java.util.Date){
					if(sqldf == null) sqldf = new SQLDateTimeFormat();

					ret += "'" + sqldf.format((java.util.Date)pre) + "' ";
				}
				else ret += pre + " ";

				ret += " " + getOperatorString(cond.getComparison()) + " ";
				if(cond.y_table != null){
					ret += cond.y_table + ".";
				}
				pre = cond.getPost();
				// check to see if object is a certain type for formatting
                if(pre instanceof java.util.Date){
					if(sqldf == null) sqldf = new SQLDateTimeFormat();

					ret += "'" + sqldf.format((java.util.Date)pre) + "' ";
				}
				else ret += pre + " ";

			}
			else{ // clause cause that's all it can be
				WhereClause clause = (WhereClause)ob;
				
				ret += " (" + clause.toString() + ") ";
				
			}
			
		}
		return ret;
	}
	public boolean hasConditions(){
		if(conditions.size() > 0){
			return true;
		}
		return false;
	}
	/**
	* gets operator string
	 */
	public static String getOperatorString(int operator){
		String ret = null;
		switch(operator){
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

	public List getConditions(){
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
           addCondition(new WhereCondition(col, WhereCondition.IN, "(" + commaSeparatedList + ")"));
       }

       /**
        * Will compare toCompare to the value in col where the left side is the same
        * @param col
        * @param toCompare
        */
       public void addWhereLikeLeft(String col, String toCompare) {
           addWhereLikeLeft("AND", col, toCompare);
       }

       public void addWhereLikeLeft(String and_or, String col, String toCompare) {
           addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'" + SQLFormat.escape(toCompare) + "%'"));
       }

       /**
        * Will compare toCompare to the value in col where the right side is the same
        * @param col
        * @param toCompare
        */
       public void addWhereLikeRight(String col, String toCompare) {
           addWhereLikeRight("AND", col, toCompare);
       }

       public void addWhereLikeRight(String and_or, String col, String toCompare) {
            addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'%" + SQLFormat.escape(toCompare) + "'"));
       }

       /**
        * this will compare the string to anywhere in the string
        * @param col
        * @param toCompare
        */
       public void addWhereLikeAny(String col, String toCompare) {
           addWhereLikeAny("AND", col, toCompare);
       }

       public void addWhereLikeAny(String and_or, String col, String toCompare) {
            addCondition(and_or, new WhereCondition(col, WhereCondition.LIKE, "'%" + SQLFormat.escape(toCompare) + "%'"));
       }



}

