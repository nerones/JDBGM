package com.thinkvirtual.sql.sqlserver;

/** This is an initial beta of a class that will represent a query string */

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import com.crossdb.sql.*;


public class SQLServerSelectQuery extends DefaultSelectQuery implements com.crossdb.sql.SelectQuery {
	

	/*public void setLimit(int count){
		if(limit == null){
			limit = new int[1];
		}
		limit[0] = count;
	}
	
	public void setLimit(int offset, int count){
		if(limit == null){
			limit = new int[2];
		}
		limit[0] = offset;
		limit[1] = count;
	}*/
	public String toString(){
		String ret = "SELECT ";
        if(isDistinct()){
            ret += "DISTINCT ";
        }
		int i;
		if(columns == null || columns.size() == 0){
			ret += "* ";
		}
		else{
			// rifle through columns and spit out string'
			for(i = 0; i < columns.size(); i++){
				String column = (String)(columns.get(i));
				ret += column + ",";
			}
			if(i > 0){
				ret = ret.substring(0,ret.length() -1);
			}
		}
		ret += " FROM ";
		String join_conditions = "";
		if(tables == null || tables.size() == 0){
			return null;
		}
		else{
			// rifle through tables and return string
			for(i = 0; i < tables.size(); i++){
				Object tj = tables.get(i);
				if(tj instanceof String){
					String tablestr = (String)(tj);
					if(i == 0){
						ret += tablestr;
					}
					else{
						ret += "," + tablestr;
					}
				}
				else if(tj instanceof Join){
					// extract info from join
					Join join = (Join)(tj);
					if(join.getType() == Join.LEFT_OUTER_JOIN){
						ret += " LEFT OUTER JOIN " + join.getTableName();
						 WhereClause jconds2 = join.getConditions();
						List jconds = jconds2.getConditions();
						
						if(jconds.size() > 0){
							ret += " ON ";
						}
						for(int m = 0; m < jconds.size(); m++){
							WhereCondition c = (WhereCondition)(jconds.get(m));
							if(m > 0){
								ret += " AND ";
							}
							ret += c.getPre() + getOperatorString(c.getOperator()) + c.getPost();
						}
					}
					else{
						ret += "," + join.getTableName(); // inner join default
						 WhereClause jconds2 = join.getConditions();
						List jconds = jconds2.getConditions();
						
						for(int m = 0; m < jconds.size(); m++){
							WhereCondition c = (WhereCondition)(jconds.get(m));
							if(m > 0){
								join_conditions += ",";
							}
							join_conditions += c.getPre() + getOperatorString(c.getOperator()) + c.getPost();
						}
					}
					
					
				}
				
			}
			/*	if(i > 0){
			 ret = ret.substring(0,ret.length() -1);
			 }*/
		}
		
		if(wclause.hasConditions()){
			ret += " WHERE ";
			// rifle through tables and return string
			ret += wclause.toString();
		}
		else if(join_conditions.length() > 0){
			ret += " WHERE "+join_conditions;// in case it's only join conditions
		}
		if(join_conditions.length() > 0){
			ret += " AND " +join_conditions;
		}
		if(group_by == null || group_by.size() == 0){
			//return null;
		}
		else{
			ret += " GROUP BY ";
			// rifle through tables and return string
			for(i = 0; i < group_by.size(); i++){
				String group = (String)(group_by.get(i));
				ret += group + ",";
			}
			if(i > 0){
				ret = ret.substring(0,ret.length() -1);
			}
		}
		if(order_by == null || order_by.size() == 0){
			//return null;
		}
		else{
			ret += " ORDER BY ";
			// rifle through tables and return string
			for(i = 0; i < order_by.size(); i++){
				String order = (String)(order_by.get(i));
				ret += order + ",";
			}
			if(i > 0){
				ret = ret.substring(0,ret.length() -1);
			}
		}
		/*if(limit != null){
			if(limit.length == 1){
				ret += " LIMIT " + limit[0];
			}
			else{ // length = 2
				ret += " LIMIT " + limit[0] + "," + limit[1];
			}
		}*/
		return ret;
	}
	
	/*
	public CrossdbResultSet execute(Statement stmt) throws SQLException{
		//q = new 	Query(conn);
		//rs = stmt.executeQuery(querystring);
		return new SQLServerResultSet(stmt.executeQuery(toString()));
	}
	public CrossdbResultSet execute(Connection conn) throws SQLException{
		//q = new 	Query(conn);
		Statement stmt = conn.createStatement();
		return execute(stmt);
	}
	*/

}
