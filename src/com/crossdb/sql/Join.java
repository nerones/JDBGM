package com.crossdb.sql;

/**

This class will represent a table join that can
be passed into select queries in the addTable function.
<p>
 will hold the join type, the table to join, and any conditions (ON conditions)
<p>
/**
 * <p>Title: crossdb</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Space Program Inc.</p>
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 */

import java.util.*;
//import java.sql.*;

public class Join {
	
	public static final int INNER_JOIN = 1;
	public static final int LEFT_OUTER_JOIN = 10;
	
	String table_name;
	int type;
	WhereClause wclause = new WhereClause();
	
	public Join(int join_type, String table_name){
		this.type = join_type;
		this.table_name = table_name;
	}
	
	public void addWhereCondition(WhereCondition cond){
		wclause.addCondition(cond);
	}
	public void addWhereCondition(String x, int comparison, String y){
		WhereCondition c = new WhereCondition(x, comparison, y);
		wclause.addCondition(c);
	}
	public void addWhereCondition(String x, int comparison, int y){
		WhereCondition c = new WhereCondition(x, comparison, "" + y);
		wclause.addCondition(c);
	}
	
	public void addCondition(String x, int comparison, String y){
		WhereCondition c = new WhereCondition(x, comparison, y);
		wclause.addCondition(c);
	}
	public void addCondition(String x, int comparison, int y){
		WhereCondition c = new WhereCondition(x, comparison, "" + y);
		wclause.addCondition(c);
	}
	/*	void addWhereWhereCondition(String x, int comparison, int y);
		void addWhereWhereCondition(String and_or, String x, int comparison, String y);
		void addWhereWhereCondition(String and_or, String x, int comparison, int y);
	 (*/
		
	public String getTableName(){
		return table_name;
	}
	public int getType(){
		return type;
	}
	public WhereClause getConditions(){
		return wclause;
	}

	/**
	 * Auto join function. (self join is when you join a table to itself)
	 *
	 * Uses tables in nested WhereClauses to create tables if none are explicitly defined in SelectQuery
	 */
	public static void autoJoin(WhereClause wc, SelectQuery sq){
		java.util.List tables = new java.util.ArrayList();
		autoJoinRipper(tables, wc);
		for (int i = 0; i < tables.size(); i++) {
			String o = (String) tables.get(i);
			//System.out.println("adding table: " + o);
			sq.addTable(o);

		}
	}
	private static void autoJoinRipper(List tables, WhereClause wc){
		// rifle through conditions and separators
		// if another clause found, call getWhereClause on that one
		List conditions = wc.getConditions();
		for(int i =0; i < conditions.size(); i++){
			Object ob = conditions.get(i);
			/*if(i != 0){
				ret += separators.get(i) + " ";
			}
			*/

			if(ob instanceof WhereCondition){
				WhereCondition cond = (WhereCondition)ob;
				String t = cond.getPreTable();
				if(t != null){
					//System.out.println("word: " + i + " - " + t);
					if(!tables.contains(t)){
						tables.add(t);
					}
				}

				t = cond.getPostTable();
				if(t != null){
					//System.out.println("word: " + i + " - " + t);
					if(!tables.contains(t)){
						tables.add(t);
					}
				}

			}
			else{ // clause cause that's all it can be
				WhereClause clause = (WhereClause)ob;

				autoJoinRipper(tables, clause);

			}

		}

	}

}
